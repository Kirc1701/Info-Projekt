package src.Graphikcontroller;

// Import anderer Klassen innerhalb des Projekts
import src.Drawable;
import src.Karte;
import src.Level.*;
import src.Main;
import src.Objekte.Baubar.Building;
import src.Objekte.Baubar.Mauer.DefaultMauer;
import src.Objekte.Baubar.Turm.DefaultTurm;
import src.Objekte.Baubar.Turm.Scharfschuetzenturm;
import src.Objekte.Baubar.Turm.Schnellschussgeschuetz;
import src.Objekte.Monster.Monster;
import src.Objekte.Objekt;
import src.Tickable;
import src.util.CoordsDouble;
import src.util.CoordsInt;

// Import anderer Klassen von außerhalb des Projekts
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static src.Main.*;

// Das Hauptfenster, auf dem das Spiel abläuft.
public class HauptgrafikSpiel extends JPanel{
    // Abstand zwischen den Linien, somit Größe der Kästchen in Pixeln
    public final static int spaceBetweenLinesPixels = 32;
    public final static int titelbalkenSizePixels = 54;

    // Höhe und Breite des geöffneten Fensters in Pixeln
    private final int windowWidthPixels;
    private final int windowHeightPixels;

    // Verknüpfung mit der logischen Implementierung der Karte
    private final Karte karte;

    // boolean der eine häufigere Öffnung des popups verhindert
    public final static boolean[] pressed = {false, false};
    private BufferedImage backgroundImageLevel3 = null;
    private BufferedImage backgroundImageLevel5 = null;
    private Building chosenBuilding = null;
    private Building hovered_image;
    private long lastTick = System.currentTimeMillis();
    private double spawnCooldown;

    // Konstruktor für die Klasse HauptgrafikSpiel
    public HauptgrafikSpiel(Karte karte){
        // Initialisierung der Karte
        this.karte = karte;

        // Initialisierung der Höhe und Breite als Vielfaches der Node-Anzahl
        // Anpassung der Höhe unter Einbeziehung des Titelbalkens
        windowWidthPixels = karte.getWidth() * spaceBetweenLinesPixels;
        windowHeightPixels = karte.getHeight() * spaceBetweenLinesPixels + titelbalkenSizePixels;

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
                    // asdf sd
                    public void mousePressed(MouseEvent e) {
                        if(e.getY() <= titelbalkenSizePixels) {
                            if (!pressed[1]) {
                                pressed[1] = true;
                                if (e.getX() >= 300 && e.getX() <= 470) {
                                    new Save();
                                } else if(e.getX() >= 490 && e.getX() <= 620){
                                    new Load();
                                } else if(e.getX() >= windowWidthPixels - 130){
                                    Main.source = true;
                                    new Einstellungen();
                                } else if(e.getX() >= 630 && e.getX() <= 671){
                                    if(chosenBuilding != null) {
                                        chosenBuilding.die();
                                    }
                                    chosenBuilding = new DefaultTurm(new CoordsInt(-1, -1));
                                    chosenBuilding.setBlueprint(true);
                                    pressed[1] = false;
                                } else if(e.getX() >= 689 && e.getX() <= 730){
                                    if(chosenBuilding != null) {
                                        chosenBuilding.die();
                                    }
                                    chosenBuilding = new Schnellschussgeschuetz(new CoordsInt(-1, -1));
                                    chosenBuilding.setBlueprint(true);
                                    pressed[1] = false;
                                } else if(e.getX() >= 748 && e.getX() <= 789){
                                    if(chosenBuilding != null) {
                                        chosenBuilding.die();
                                    }
                                    chosenBuilding = new Scharfschuetzenturm(new CoordsInt(-1, -1));
                                    chosenBuilding.setBlueprint(true);
                                    pressed[1] = false;
                                } else if(e.getX() >= 807 && e.getX() <= 848){
                                    if(chosenBuilding != null) {
                                        chosenBuilding.die();
                                    }
                                    chosenBuilding = new DefaultMauer(new CoordsInt(-1, -1));
                                    chosenBuilding.setBlueprint(true);
                                    pressed[1] = false;
                                }
                            }
                            return;
                        }
                        // Wenn pressed auf false steht, kann das Programm ausgeführt werden
                        if(!pressed[0]) {
                            // pressed wird auf true gesetzt, um häufigere Öffnung des Fensters zu vermeiden
                            pressed[0] = true;
                            // x und y werden aus dem Event gezogen
                            int x = e.getX() / spaceBetweenLinesPixels;
                            int y = (e.getY() - titelbalkenSizePixels) / spaceBetweenLinesPixels;
                            // Wenn an der gewählten Position bereits ein Gebäude steht, so wird remove aufgerufen,
                            // sonst wird setzen aufgerufen
                            if(karte.getBuildings().containsKey(new CoordsInt(x, y))){
                                if(karte.getBuildings().get(new CoordsInt(x, y)).getSpawntime() + 2 <= System.currentTimeMillis()) {
                                    try {
                                        new PopupRemoveBuilding(x, y, e.getX(), e.getY());
                                    } catch (IOException ignored) {}
                                }else {
                                    pressed[0] = false;
                                }
                            }else {
                                try {
                                    build(chosenBuilding.getClass().getConstructor(CoordsInt.class).newInstance(new CoordsInt(x,y)));
                                } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                                         NoSuchMethodException ex) {
                                    throw new RuntimeException(ex);
                                }
                                pressed[0] = false;
                            }
                        }
                    }
                }
        );
        this.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent e) {
                if(chosenBuilding == null) return;
                int x = e.getX() / spaceBetweenLinesPixels;
                int y = (e.getY() - titelbalkenSizePixels) / spaceBetweenLinesPixels;
                if(e.getY()>titelbalkenSizePixels && !karte.getBuildings().containsKey(new CoordsInt(x,y))){
                    chosenBuilding.setPosition(new CoordsInt(x,y));
                }else{
                    chosenBuilding.setPosition(new CoordsInt(-1, -1));
                }
            }
        });
        spawnCooldown = karte.getLevel().getSpawnTime();
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
            try {
                tick(timeDelta);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
            g.drawString("Einstellungen", windowWidthPixels - 130, titelbalkenSizePixels - 32);

            g.setColor(Color.BLACK);
            g.fillRect(628, 0, 220, 54);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Helvetica", Font.BOLD, 13));
            Image defaultTurmImage = DefaultTurm.getStaticImage();
            Image schnellschussTurmImage = Schnellschussgeschuetz.getStaticImage();
            Image scharfschuetzenTurmImage = Scharfschuetzenturm.getStaticImage();
            Image mauerImage = DefaultMauer.getStaticImage();
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
                if (chosenBuilding != null && chosenBuilding.getType().equals("DefaultTurm")) {
                    g.setColor(Color.BLUE.brighter());
                }
                g.drawString("Turm", 630, titelbalkenSizePixels - 2); // Add text label for Default Turm
                if (chosenBuilding != null && chosenBuilding.getType().equals("DefaultTurm")) {
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
                if (chosenBuilding != null && chosenBuilding.getType().equals("Schnellschussgeschuetz")) {
                    g.setColor(Color.BLUE.brighter());
                }
                g.drawString("Minigun", 687, titelbalkenSizePixels - 2); // Add text label for Schnellschuss Turm
                if (chosenBuilding != null && chosenBuilding.getType().equals("Schnellschussgeschuetz")) {
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
                if (chosenBuilding != null && chosenBuilding.getType().equals("Scharfschuetzenturm")) {
                    g.setColor(Color.BLUE.brighter());
                }
                g.drawString("Sniper", 748, titelbalkenSizePixels - 2); // Add text label for Scharfschuetzen Turm
                if (chosenBuilding != null && chosenBuilding.getType().equals("Scharfschuetzenturm")) {
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
                if (chosenBuilding != null && chosenBuilding.getType().equals("DefaultMauer")) {
                    g.setColor(Color.BLUE.brighter());
                }
                g.drawString("Mauer", 807, titelbalkenSizePixels - 2); // Add text label for Default Mauer
                if (chosenBuilding != null && chosenBuilding.getType().equals("DefaultMauer")) {
                    g.setColor(Color.WHITE);
                }
            }
        }
        lastTick = System.currentTimeMillis();
        repaint();
    }



    private void tick(double timeDelta) throws IOException {
        if(karte.gameOver()) return;
        spawnCooldown -= timeDelta;
        for (Tickable tickable : Main.getTickables()) {
            tickable.tick(timeDelta, karte);
        }
        if(spawnCooldown <= 0){
            if(!karte.getLevel().getMonstersToSpawn().isEmpty()){
                karte.spawnMonster();
            }
            spawnCooldown = karte.getLevel().getSpawnTime();
        }
        for(Monster monster : karte.getMonsterList()){
            if(monster.getHealth() <= 0){
                karte.getMonsterList().remove(monster);
                monster.die();
            }
        }
        for (Objekt building : karte.getBuildings().values().stream().toList()) {
            if (building.getHealth() <= 0) {
                building.die();
                if(building.getType().equals("DefaultBasis")){
                    setVisible(false);
                }
            }
        }
        loadDesign();
    }

    private void drawDrawables(Graphics g) {
        for (Drawable drawable : Main.getDrawables()) {
            if(drawable.getDrawnPosition().equals(new CoordsDouble(-1, -1))) continue;
            CoordsDouble pixelPosition = drawable.getDrawnPosition().scale(spaceBetweenLinesPixels);
            Graphics2D g2 = (Graphics2D) g;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, drawable.getOpacity()));
            g2.drawImage(drawable.getImage(), (int) pixelPosition.x(), (int) pixelPosition.y() + titelbalkenSizePixels, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
        }
        for(Drawable drawable : Main.getDrawables()){
            drawable.draw(g);
        }
    }

    private void drawBackground(Graphics g) {
        if (karte.getLevel().getClass().equals(Level1.class) || karte.getLevel().getClass().equals(Level2.class) || karte.getLevel().getClass().equals(Level3.class) || karte.getLevel().getClass().equals(Level4.class)) {
            g.drawImage(
                    backgroundImageLevel3,
                    0,
                    titelbalkenSizePixels,
                    windowWidthPixels,
                    windowHeightPixels,
                    null
            );
        } else if (karte.getLevel().getClass().equals(Level5.class)) {
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

    private void build(Building newBuilding) {
        if (newBuilding.getKosten() <= money) {
            if (karte.addBuilding(newBuilding.getPosition(), newBuilding) == null) {
                System.out.println("Something went wrong");
            } else {
                for (Monster monster : karte.getMonsterList()) {
                    monster.updateMonsterPath(karte);
                }
                money -= newBuilding.getKosten();
            }
        }
        pressed[0] = false;
    }
}
