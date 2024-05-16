package src.visuals;

// Import anderer Klassen innerhalb des Projekts

import src.Drawable;
import src.LogicRepresentation;
import src.Main;
import src.Tickable;
import src.drawables.objects.Object;
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
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;

import static src.Main.*;

// Das Hauptfenster, auf dem das Spiel abläuft.
public class GameScreen extends JPanel {
    // Abstand zwischen den Linien, somit Größe der Kästchen in Pixeln
    public final static int spaceBetweenLinesPixels = 32;
    public final static int titelbalkenSizePixels = 54;

    // Höhe und Breite des geöffneten Fensters in Pixeln
    private final int windowWidthPixels;
    private final int windowHeightPixels;

    // Verknüpfung mit der logischen Implementierung der LogicRepresentation
    private final LogicRepresentation logicRepresentation;

    // boolean der eine häufigere Öffnung des popups verhindert
    public static final boolean[] pressed = {false, false};
    private BufferedImage backgroundImageLevel3 = null;
    private BufferedImage backgroundImageLevel5 = null;
    private Building chosenBuilding = null;
    private long lastTick = System.currentTimeMillis();
    private double spawnCooldown;

    // Konstruktor für die Klasse GameScreen
    public GameScreen(LogicRepresentation logicRepresentation) {
        // Initialisierung der LogicRepresentation
        this.logicRepresentation = logicRepresentation;

        // Initialisierung der Höhe und Breite als Vielfaches der Node-Anzahl
        // Anpassung der Höhe unter Einbeziehung des Titelbalkens
        windowWidthPixels = logicRepresentation.getWidth() * spaceBetweenLinesPixels;
        windowHeightPixels = logicRepresentation.getHeight() * spaceBetweenLinesPixels + titelbalkenSizePixels;

        // WindowListener um das Schließen des Fensters zu registrieren


        // Erstellung eines Images, in welches danach die Bilddateien geladen werden
        try {
            backgroundImageLevel3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/BackgroundLevel3.jpg")));
            backgroundImageLevel5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/BackgroundLevel5.jpg")));
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        // Hinzufügen des MouseListeners
        this.addMouseListener(
                new MouseAdapter() {
                    // Wird aufgerufen, wenn die Maus gedrückt wird
                    public void mousePressed(MouseEvent e) {
                        if(e.getY() <= titelbalkenSizePixels) {
                            if (!pressed[1]) {
                                if (e.getX() >= 300 && e.getX() <= 470) {
                                    new SaveDesignPopUp();
                                } else if(e.getX() >= 490 && e.getX() <= 620){
                                    new LoadDesignPopUp();
                                } else if(e.getX() >= windowWidthPixels - 130){
                                    Main.source = true;
                                    new Settings();
                                } else if(e.getX() >= 630 && e.getX() <= 671){
                                    choseBuilding("tower.DefaultTower");
                                } else if(e.getX() >= 689 && e.getX() <= 730){
                                    choseBuilding("tower.Minigun");
                                } else if(e.getX() >= 748 && e.getX() <= 789){
                                    choseBuilding("tower.Sniper");
                                } else if(e.getX() >= 807 && e.getX() <= 848){
                                    choseBuilding("wall.DefaultWall");
                                }
                            }
                            return;
                        }
                        // Wenn pressed auf false steht, kann das Programm ausgeführt werden
//                        if(!pressed[0]) {
                            // pressed wird auf true gesetzt, um häufigere Öffnung des Fensters zu vermeiden
//                            pressed[0] = true;
                            // x und y werden aus dem Event gezogen
                            int x = e.getX() / spaceBetweenLinesPixels;
                            int y = (e.getY() - titelbalkenSizePixels) / spaceBetweenLinesPixels;
                            // Wenn an der gewählten Position bereits ein Gebäude steht, so wird remove aufgerufen,
                            // sonst wird setzen aufgerufen
                        if (logicRepresentation.getBuildings().containsKey(new CoordsInt(x, y))) {
                            if (logicRepresentation.getBuildings().get(new CoordsInt(x, y)).getSpawntime() + 2 <= System.currentTimeMillis()) {
                                    try {
                                        new RemoveBuildingPopUp(x, y, e.getX(), e.getY());
                                    } catch (IOException ignored) {}
                                }else {
                                    pressed[0] = false;
                                }
                            }else {
                                build(chosenBuilding.getClass(), new CoordsInt(x,y), chosenBuilding.getKosten());
                                pressed[0] = false;
                            }
//                        }
                    }
                }
        );
        this.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent e) {
                if(chosenBuilding == null) return;
                int x = e.getX() / spaceBetweenLinesPixels;
                int y = (e.getY() - titelbalkenSizePixels) / spaceBetweenLinesPixels;
                if (e.getY() > titelbalkenSizePixels && !logicRepresentation.getBuildings().containsKey(new CoordsInt(x, y))) {
                    chosenBuilding.setPosition(new CoordsInt(x,y));
                }else{
                    chosenBuilding.setPosition(new CoordsInt(-1, -1));
                }
            }
        });
        spawnCooldown = 0;
        // Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setSize(windowWidthPixels, windowHeightPixels);
        setLayout(null);
        setVisible(true);
    }

    // paint()-Methode
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawDrawables(g);
        double timeDelta = (System.currentTimeMillis() - lastTick) / 1000.0;
        if(screenSelection != 1) {
            tick(timeDelta);
        }

        //Zeichnen der Kanten
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setStroke(new BasicStroke(1));
        graphics2D.setColor(Color.black);
        graphics2D.drawLine(0, titelbalkenSizePixels, windowWidthPixels, titelbalkenSizePixels);

        double geld = Main.money;
        g.setColor(Color.BLACK);
        int width = geld / 100 >= 1 ? 128 : 112;
        g.fillRect(0, 0, width, 27);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Geld: " + geld, 10, titelbalkenSizePixels - 32);

        {
            g.setColor(Color.BLACK);
            g.fillRect(298, 0, 172, 27);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.BOLD, 20));
            g.drawString("Design speichern", 300, titelbalkenSizePixels - 32);

            g.setColor(Color.BLACK);
            g.fillRect(488, 0, 132, 27);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.BOLD, 20));
            g.drawString("Design laden", 490, titelbalkenSizePixels - 32);

            g.setColor(Color.BLACK);
            g.fillRect(windowWidthPixels - 132, 0, 132, 27);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.BOLD, 20));
            g.drawString("Settings", windowWidthPixels - 130, titelbalkenSizePixels - 32);

            g.setColor(Color.BLACK);
            g.fillRect(628, 0, 220, 54);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.BOLD, 13));
            Image defaultTurmImage = DefaultTower.getStaticImage();
            Image schnellschussTurmImage = Minigun.getStaticImage();
            Image scharfschuetzenTurmImage = Sniper.getStaticImage();
            Image mauerImage = DefaultWall.getStaticImage();
            if (defaultTurmImage != null) {
                g.fillRect(632, 2, 37, 37);
                g.drawImage(
                        defaultTurmImage,
                        630,
                        0,
                        41,
                        41,
                        null
                );
                if (chosenBuilding != null && chosenBuilding.getType().equals("DefaultTower")) {
                    g.setColor(Color.BLUE.brighter());
                }
                g.drawString("Tower", 630, titelbalkenSizePixels - 2); // Add text label for Default tower
                if (chosenBuilding != null && chosenBuilding.getType().equals("DefaultTower")) {
                    g.setColor(Color.WHITE);
                }
            }
            if (schnellschussTurmImage != null) {
                g.fillRect(691, 2, 37, 37);
                g.drawImage(
                        schnellschussTurmImage,
                        689,
                        0,
                        41,
                        41,
                        null
                );
                if (chosenBuilding != null && chosenBuilding.getType().equals("Minigun")) {
                    g.setColor(Color.BLUE.brighter());
                }
                g.drawString("Minigun", 687, titelbalkenSizePixels - 2); // Add text label for Schnellschuss tower
                if (chosenBuilding != null && chosenBuilding.getType().equals("Minigun")) {
                    g.setColor(Color.WHITE);
                }
            }
            if (scharfschuetzenTurmImage != null) {
                g.fillRect(750, 2, 37, 37);
                g.drawImage(
                        scharfschuetzenTurmImage,
                        748,
                        0,
                        41,
                        41,
                        null
                );
                if (chosenBuilding != null && chosenBuilding.getType().equals("Sniper")) {
                    g.setColor(Color.BLUE.brighter());
                }
                g.drawString("Sniper", 748, titelbalkenSizePixels - 2); // Add text label for Scharfschuetzen tower
                if (chosenBuilding != null && chosenBuilding.getType().equals("Sniper")) {
                    g.setColor(Color.WHITE);
                }
            }
            if (mauerImage != null) {
                g.fillRect(809, 2, 37, 37);
                g.drawImage(
                        mauerImage,
                        807,
                        0,
                        41,
                        41,
                        null
                );
                if (chosenBuilding != null && chosenBuilding.getType().equals("DefaultWall")) {
                    g.setColor(Color.BLUE.brighter());
                }
                g.drawString("Wall", 807, titelbalkenSizePixels - 2); // Add text label for Default wall
                if (chosenBuilding != null && chosenBuilding.getType().equals("DefaultWall")) {
                    g.setColor(Color.WHITE);
                }
            }
        }
        try {
            loadDesign();
        } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        lastTick = System.currentTimeMillis();
        repaint();
    }

    private void tick(double timeDelta) {
        if (logicRepresentation.gameOver()) return;
        spawnCooldown -= timeDelta;
        for (Tickable tickable : Main.getTickables()) {
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
                if(building.getType().equals("DefaultBasis")){
                    setVisible(false);
                }
            }
        }
    }

    private void drawDrawables(Graphics g) {
        List<Drawable> drawables = Main.getDrawables().reversed();
        for(Drawable drawable : drawables){
            drawable.draw(g);
        }
    }

    private void drawBackground(Graphics g) {
        if (logicRepresentation.getLevel().getClass().equals(Level1.class) || logicRepresentation.getLevel().getClass().equals(Level2.class) || logicRepresentation.getLevel().getClass().equals(Level3.class) || logicRepresentation.getLevel().getClass().equals(Level4.class)) {
            g.drawImage(
                    backgroundImageLevel3,
                    0,
                    titelbalkenSizePixels,
                    windowWidthPixels,
                    windowHeightPixels,
                    null
            );
        } else if (logicRepresentation.getLevel().getClass().equals(Level5.class)) {
            g.drawImage(
                    backgroundImageLevel5,
                    0,
                    titelbalkenSizePixels,
                    windowWidthPixels,
                    windowHeightPixels,
                    null
            );
        }
    }

    private void build(Class<?> toBuild, CoordsInt position, double price) {
        if (price <= money) {
            if (logicRepresentation.getBuilding(position) == null) {
                try {
                    Building building = (Building) toBuild.getDeclaredConstructor(CoordsInt.class).newInstance(position);
                    logicRepresentation.addBuilding(position, building);
                    money -= price;
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
        if(chosenBuilding.getType().equals(chosenSub)){
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
