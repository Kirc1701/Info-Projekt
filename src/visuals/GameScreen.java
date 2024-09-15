package src.visuals;

// Import of other classes into the project

import src.*;
import src.drawables.objects.Object;
import src.drawables.objects.ObjectType;
import src.drawables.objects.buildings.Building;
import src.drawables.objects.buildings.tower.DefaultTower;
import src.drawables.objects.buildings.tower.Minigun;
import src.drawables.objects.buildings.tower.Sniper;
import src.drawables.objects.buildings.wall.DefaultWall;
import src.drawables.objects.monster.Monster;
import src.level.*;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import static src.Main.*;

// Main window which runs the game
public class GameScreen extends JPanel {
    // space between lines, aka length of squares
    public final static int spaceBetweenLinesPixels = 32;
    public final static int titleBarSizePixels = 54;

    // height and width of the opened window in pixels
    private final int windowWidthPixels;
    private final int windowHeightPixels;

    // Connection with the logical implementation of the LogicRepresentation
    private final LogicRepresentation logicRepresentation;

    // boolean that prevents opening the popup multiple times
    public static final boolean[] pressed = {false, false};
    private BufferedImage backgroundImageLevel3 = null;
    private BufferedImage backgroundImageLevel5 = null;
    private Building chosenBuilding = null;
    private long lastTick = System.currentTimeMillis();
    private double spawnCooldown;

    public static boolean paused = true;

    // constructor for class GameScreen
    public GameScreen(LogicRepresentation logicRepresentation) {
        this.logicRepresentation = logicRepresentation;

        windowWidthPixels = logicRepresentation.getWidth() * spaceBetweenLinesPixels;
        windowHeightPixels = logicRepresentation.getHeight() * spaceBetweenLinesPixels + titleBarSizePixels;

        try {
            backgroundImageLevel3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/BackgroundLevel3.jpg")));
            backgroundImageLevel5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/BackgroundLevel5.jpg")));
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        {
            this.addMouseListener(
                    new MouseAdapter() {
                        public void mousePressed(MouseEvent e) {
                            if (e.getY() <= titleBarSizePixels) {
                                if (!pressed[1]) {
                                    if (e.getX() >= 300 && e.getX() <= 470) {
                                        new SaveDesignPopUp();
                                    } else if (e.getX() >= 490 && e.getX() <= 620) {
                                        new LoadDesignPopUp();
                                    } else if (e.getX() >= windowWidthPixels - 130) {
                                        Main.source = true;
                                        loop.update(LoopType.settings);
                                    } else if (e.getX() >= 630 && e.getX() <= 671) {
                                        choseBuilding("tower.DefaultTower");
                                    } else if (e.getX() >= 689 && e.getX() <= 730) {
                                        choseBuilding("tower.Minigun");
                                    } else if (e.getX() >= 748 && e.getX() <= 789) {
                                        choseBuilding("tower.Sniper");
                                    } else if (e.getX() >= 807 && e.getX() <= 848) {
                                        choseBuilding("wall.DefaultWall");
                                    }
                                }
                                return;
                            }

                            int x = e.getX() / spaceBetweenLinesPixels;
                            int y = (e.getY() - titleBarSizePixels) / spaceBetweenLinesPixels;

                            if (logicRepresentation.getBuildings().containsKey(new CoordsInt(x, y))) {
                                if (logicRepresentation.getBuildings().get(new CoordsInt(x, y)).getSpawntime() + 2 <= System.currentTimeMillis()) {
                                    try {
                                        new RemoveBuildingPopUp(x, y, e.getX(), e.getY());
                                    } catch (IOException ignored) {
                                    }
                                } else {
                                    pressed[0] = false;
                                }
                            } else {
                                build(chosenBuilding.getClass(), new CoordsInt(x, y), chosenBuilding.getCost());
                                pressed[0] = false;
                            }
                        }
                    }
            );

        }
        {
            this.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseMoved(MouseEvent e) {
                    if (chosenBuilding == null) return;
                    int x = e.getX() / spaceBetweenLinesPixels;
                    int y = (e.getY() - titleBarSizePixels) / spaceBetweenLinesPixels;
                    if (e.getY() > titleBarSizePixels && !logicRepresentation.getBuildings().containsKey(new CoordsInt(x, y))) {
                        chosenBuilding.setPosition(new CoordsInt(x, y));
                    } else {
                        chosenBuilding.setPosition(new CoordsInt(-1, -1));
                    }
                }
            });
        }
        spawnCooldown = 0;

        setSize(windowWidthPixels, windowHeightPixels);
        setLayout(null);
        setVisible(true);
    }

    // paint()-Method
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawDrawables(g);
        double timeDelta = (System.currentTimeMillis() - lastTick) / 1000.0;
        if(!paused) {
            tick(timeDelta);
        }

        paintingTitleBarAndEdges(g);

        try {
            loadDesign();
        } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        lastTick = System.currentTimeMillis();
        repaint();
    }

    private void paintingTitleBarAndEdges(Graphics g) {
        paint_edges((Graphics2D) g);

        paintTextIntoTitleBar(0, (loop.getMoney()/100 >= 1 ? 128:112), 27, "Geld: "+loop.getMoney(),"Arial", 20, g);
        paintTextIntoTitleBar(298, 172, 27, "Design speichern", "Helvetica", 20, g);
        paintTextIntoTitleBar(488, 132, 27, "Design laden", "Helvetica", 20, g);
        paintTextIntoTitleBar(windowWidthPixels -132, 132, 27, "Settings", "Helvetica", 20, g);

        paintTextIntoTitleBar(628, 220, 54, "", "Helvetica", 13, g);

        paintBuildingsIntoTitleBar(DefaultTower.getStaticImage(), 630, ObjectType.DefaultTower, "Default", g);
        paintBuildingsIntoTitleBar(Minigun.getStaticImage(), 689, ObjectType.Minigun, "Mini-Gun", g);
        paintBuildingsIntoTitleBar(Sniper.getStaticImage(), 748, ObjectType.Sniper, "Sniper", g);
        paintBuildingsIntoTitleBar(DefaultWall.getStaticImage(), 807, ObjectType.DefaultWall, "Wall", g);
    }

    private void paintTextIntoTitleBar(int x, int width, int height, String attribute, String fontType, int fontSize, Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(x, 0, width, height);
        g.setColor(Color.WHITE);
        g.setFont(new Font(fontType, Font.BOLD, fontSize));
        g.drawString(attribute, x+2, titleBarSizePixels - 32);
    }

    private void paintBuildingsIntoTitleBar(Image image, int x, ObjectType buildingType, String visibleType, Graphics g){
        if(image != null){
            g.fillRect(x+2, 2, 37, 37);
            g.drawImage(
                    image,
                    x,
                    0,
                    41,
                    41,
                    null
            );
            if(chosenBuilding!= null && chosenBuilding.getType().equals(buildingType)){
                g.setColor(Color.BLUE.brighter());
            }
            g.drawString(visibleType, x, titleBarSizePixels -2);
            if(chosenBuilding != null && chosenBuilding.getType().equals(buildingType)){
                g.setColor(Color.WHITE);
            }
        }
    }

    private void paint_edges(Graphics2D graphics2D) {
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.setColor(Color.black);
        graphics2D.drawLine(0, titleBarSizePixels, windowWidthPixels, titleBarSizePixels);
    }

    private void tick(double timeDelta) {
        if (logicRepresentation.gameOver()) return;
        spawnCooldown -= timeDelta;
        for (Tickable tickable : loop.getTickables()) {
            tickable.tick(timeDelta, logicRepresentation);
        }
        if(spawnCooldown <= 0){
            if (!logicRepresentation.getLevel().getMonstersToSpawn().isEmpty()) {
                logicRepresentation.spawnMonster();
            }
            spawnCooldown = logicRepresentation.getLevel().getSpawnTime();
        }
        for (Monster monster : logicRepresentation.getMonsterList()) {
            if(monster.getHealth() <= 0){
                logicRepresentation.getMonsterList().remove(monster);
                monster.die();
            }
        }
        for (Object building : logicRepresentation.getBuildings().values().stream().toList()) {
            if (building.getHealth() <= 0) {
                building.die();
                if(building.getType().equals(ObjectType.DefaultBasis)){
                    setVisible(false);
                }
            }
        }
    }

    private void drawDrawables(Graphics g) {
        List<Drawable> drawables = loop.getDrawables().reversed();
        for(Drawable drawable : drawables){
            drawable.draw(g);
        }
    }

    private void drawBackground(Graphics g) {
        if (logicRepresentation.getLevel().getClass().equals(Level1.class) || logicRepresentation.getLevel().getClass().equals(Level2.class) || logicRepresentation.getLevel().getClass().equals(Level3.class) || logicRepresentation.getLevel().getClass().equals(Level4.class)) {
            g.drawImage(
                    backgroundImageLevel3,
                    0,
                    titleBarSizePixels,
                    windowWidthPixels,
                    windowHeightPixels,
                    null
            );
        } else if (logicRepresentation.getLevel().getClass().equals(Level5.class)) {
            g.drawImage(
                    backgroundImageLevel5,
                    0,
                    titleBarSizePixels,
                    windowWidthPixels,
                    windowHeightPixels,
                    null
            );
        }
    }

    private void build(Class<?> toBuild, CoordsInt position, double price) {
        if (price <= loop.getMoney()) {
            if (logicRepresentation.getBuilding(position) == null) {
                try {
                    Building building = (Building) toBuild.getDeclaredConstructor(CoordsInt.class).newInstance(position);
                    logicRepresentation.addBuilding(position, building);
                    loop.setMoney(loop.getMoney() - price);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
                for (Monster monster : logicRepresentation.getMonsterList()) {
                    monster.updateMonsterPath(logicRepresentation);
                }
            }
        }
        pressed[0] = false;
    }

    private void choseBuilding(String chosen){
        String chosenSub = chosen.split("\\.")[1];
        if(chosenBuilding == null){
            createChosenBuilding(chosen);
            return;
        }
        chosenBuilding.die();
        if(chosenBuilding.getType().toString().equals(chosenSub)){
            chosenBuilding = null;
        }else{
            createChosenBuilding(chosen);
        }
        pressed[1] = false;
    }

    private void createChosenBuilding(String chosen) {
        try {
            Class<?> buildingClass = Class.forName("src.drawables.objects.buildings." + chosen);
            chosenBuilding = (Building) buildingClass.getDeclaredConstructor(CoordsInt.class).newInstance(new CoordsInt(-1, -1));
            chosenBuilding.setBlueprint(true);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
