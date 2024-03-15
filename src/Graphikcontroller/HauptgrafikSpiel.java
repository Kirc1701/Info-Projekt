package src.Graphikcontroller;

// Import anderer Klassen innerhalb des Projekts
import src.Coords;
import src.Karte;
import src.Level.*;
import src.Main;
import src.Objekte.Baubar.Baubar;
import src.Objekte.Monster.Monster;

// Import anderer Klassen von außerhalb des Projekts
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static src.Main.*;
import static src.Objekte.Baubar.Baubar.getBaubar;

// Das Hauptfenster, auf dem das Spiel abläuft.
public class HauptgrafikSpiel extends JFrame{
    // Abstand zwischen den Linien, somit Größe der Kästchen in Pixeln
    public final static int spaceBetweenLinesPixels = 32;
    public final static int titelbalkenSizePixels = 27 + 54;

    // Höhe und Breite des geöffneten Fensters in Pixeln
    private final int windowWidthPixels;
    private final int windowHeightPixels;

    // Verknüpfung mit der logischen Implementierung der Karte
    private final Karte karte;

    // boolean der eine häufigere Öffnung des popups verhindert
    public final static boolean[] pressed = {false, false};
    public static BufferedImage mauerImage = null;
    public static BufferedImage defaultTurmImage = null;
    public static BufferedImage schnellschussTurmImage = null;
    public static BufferedImage scharfschuetzenTurmImage = null;
    private BufferedImage defaultMonsterImage = null;
    private BufferedImage golemImage = null;
    private BufferedImage sprinterImage = null;
    private BufferedImage bombenschiffImage = null;
    private BufferedImage lakaiImage = null;
    private BufferedImage basisImage = null;
    private BufferedImage bossImage = null;
//    private BufferedImage backgroundImageLevel1 = null;
//    private BufferedImage backgroundImageLevel2 = null;
    private BufferedImage backgroundImageLevel3 = null;
//    private BufferedImage backgroundImageLevel4 = null;
    private BufferedImage backgroundImageLevel5 = null;
    private int chosenBuilding = 0;

    public void closeWindow() {
        setVisible(false);
        dispose();
    }
    // Konstruktor für die Klasse HauptgrafikSpiel
    public HauptgrafikSpiel(Karte karte){
        // Initialisierung der Karte
        this.karte = karte;

        // Initialisierung der Höhe und Breite als Vielfaches der Node-Anzahl
        // Anpassung der Höhe unter Einbeziehung des Titelbalkens
        windowWidthPixels = karte.getWidth() * spaceBetweenLinesPixels;
        windowHeightPixels = karte.getHeight() * spaceBetweenLinesPixels + titelbalkenSizePixels;

        // WindowListener um das Schließen des Fensters zu registrieren
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        closeWindow();
                        System.exit(0);
                    }
                }
        );

        // Erstellung eines Images, in welches danach die Bilddateien geladen werden
        try {
            mauerImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/Mauer.png")));
            defaultTurmImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/DefaultTurm.png")));
            schnellschussTurmImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/SchnellschussTurm.png")));
            scharfschuetzenTurmImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/ScharfschuetzenTurm.png")));
            basisImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/Basis.png")));
            defaultMonsterImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/DefaultMonster.png")));
            golemImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/Golem.png")));
            sprinterImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/Sprinter.png")));
            bombenschiffImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/Bombenschiff.png")));
            lakaiImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/Lakai.png")));
            bossImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/Boss.png")));
//            backgroundImageLevel1 = ImageIO.read(fBackgroundLevel1);
//            backgroundImageLevel2 = ImageIO.read(fBackgroundLevel2);
            backgroundImageLevel3 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/BackgroundLevel3.jpg")));
//            backgroundImageLevel4 = ImageIO.read(fBackgroundLevel4);
            backgroundImageLevel5 = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/BackgroundLevel5.jpg")));
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        // Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setSize(windowWidthPixels, windowHeightPixels);
        setLayout(null);
        setVisible(true);
    }

    // paint()-Methode
    public void paint(Graphics g) {
        {
            if (karte.getLevel().getClass().equals(Level1.class) || karte.getLevel().getClass().equals(Level2.class) || karte.getLevel().getClass().equals(Level3.class) || karte.getLevel().getClass().equals(Level4.class)) {
                g.drawImage(
                        backgroundImageLevel3,
                        0,
                        0,
                        windowWidthPixels,
                        windowHeightPixels,
                        null
                );
            } else if (karte.getLevel().getClass().equals(Level5.class)) {
                g.drawImage(
                        backgroundImageLevel5,
                        0,
                        0,
                        windowWidthPixels,
                        windowHeightPixels,
                        null
                );
            }
        }
        // Darstellung der Basis, wenn vorhanden
        if(karte.getBasis() != null) {
            //Position der linken oberen Ecke der Basis
            int basisXCoordinate = karte.getBasis().getPosition().x() * spaceBetweenLinesPixels + 1;
            int basisYCoordinate = karte.getBasis().getPosition().y() * spaceBetweenLinesPixels + 1 + titelbalkenSizePixels;

            // Darstellung des Bildes von der Basis
            g.setColor(Color.black);
            g.drawImage(
                    basisImage,
                    basisXCoordinate,
                    basisYCoordinate,
                    spaceBetweenLinesPixels - 2,
                    spaceBetweenLinesPixels - 2,
                    null
            );

            // Darstellung der verbleibenden Leben
            if(!(karte.getBasis().getHealth() == karte.getBasis().getMaxHealth())) {
                double lifeInPercent = ((double) karte.getBasis().getHealth()) / ((double) karte.getBasis().getMaxHealth());
                int widthOfLifeline = (int) (lifeInPercent * (spaceBetweenLinesPixels - 2));
                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setStroke(new BasicStroke(3));
                graphics2D.setColor(Color.red);
                graphics2D.drawLine(basisXCoordinate + 1, basisYCoordinate, basisXCoordinate + widthOfLifeline, basisYCoordinate);
            }
        }

        // Darstellen aller Gebäude, in der buildings-Liste in der Game-Logik
        Map<Coords, Baubar> buildings = new HashMap<>(karte.getBuildings());
        for(Coords buildingCoords : buildings.keySet()){
            Baubar building = buildings.get(buildingCoords);

            // Koordinaten des Gebäudes
            int buildingX = buildingCoords.x()* spaceBetweenLinesPixels +1;
            int buildingY = buildingCoords.y()* spaceBetweenLinesPixels +1 + titelbalkenSizePixels;

            // Unterscheidung zwischen unterschiedlichen Arten von Gebäuden
            switch (building.getType()) {
                case "DefaultMauer" -> {
                    // Zeichnen der Mauer
                    g.setColor(Color.black);
                    g.drawImage(
                            mauerImage,
                            buildingX,
                            buildingY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );
                }
                case "DefaultTurm" -> {
                    // Zeichnen des Turms
                    g.setColor(Color.black);
                    g.drawImage(
                            defaultTurmImage,
                            buildingX,
                            buildingY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );

                }
                case "Schnellschussgeschuetz" -> {
                    g.setColor(Color.black);
                    g.drawImage(
                            schnellschussTurmImage,
                            buildingX,
                            buildingY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );
                }
                case "Scharfschuetzenturm" -> {
                    g.setColor(Color.black);
                    g.drawImage(
                            scharfschuetzenTurmImage,
                            buildingX,
                            buildingY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );
                }
            }
            if (!(building.getHealth() == building.getMaxHealth())) {
                setLifeBar((Graphics2D) g, buildingX, buildingY, building.getHealth(), building.getMaxHealth());
            }
        }

        // Zeichnen der Monster
        if(!karte.getMonsterList().isEmpty()) {
            List<Monster> monsterList = karte.getMonsterList().subList(0, karte.getMonsterList().size());
            for(Monster monster : monsterList){
                int monsterX = monster.getPosition().x()* spaceBetweenLinesPixels +1;
                int monsterY = monster.getPosition().y()* spaceBetweenLinesPixels +1 + titelbalkenSizePixels;
                g.setColor(Color.black);
                switch (monster.getType()) {
                    case "Default" -> g.drawImage(
                            defaultMonsterImage,
                            monsterX,
                            monsterY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );
                    case "Golem" -> g.drawImage(
                            golemImage,
                            monsterX,
                            monsterY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );
                    case "Sprinter" -> g.drawImage(
                            sprinterImage,
                            monsterX,
                            monsterY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );
                    case "Bombenschiff" -> g.drawImage(
                            bombenschiffImage,
                            monsterX,
                            monsterY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );
                    case "Lakai" -> g.drawImage(
                            lakaiImage,
                            monsterX,
                            monsterY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );
                    case "Boss1" -> g.drawImage(
                            bossImage,
                            monsterX,
                            monsterY,
                            spaceBetweenLinesPixels - 2,
                            spaceBetweenLinesPixels - 2,
                            null
                    );
                }
                setLifeBar((Graphics2D) g, monsterX, monsterY, monster.getHealth(), monster.getMaxHealth());
            }
        }

        //Zeichnen der Kanten
        for(int i = 0; i < karte.getHeight(); i++){
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.setColor(Color.black);
            graphics2D.drawLine(0, i* spaceBetweenLinesPixels + titelbalkenSizePixels, windowWidthPixels, i* spaceBetweenLinesPixels + titelbalkenSizePixels);
        }
        for(int i = 1; i < karte.getWidth(); i++){
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.setColor(Color.black);
            graphics2D.drawLine(i* spaceBetweenLinesPixels,titelbalkenSizePixels, i* spaceBetweenLinesPixels, windowHeightPixels);
        }

        //Darstellen des Geldes
        double geld = Main.money;
        g.setColor(Color.BLACK);
        int width = geld / 100 >= 1 ? 128 : 112;
        g.fillRect(0, titelbalkenSizePixels/3, width, 27);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Geld: " + geld, 10, titelbalkenSizePixels - 32);

        // Zeichnen des Schusses
        Map<String, Integer>[] shots = Main.shotMonster.clone();
        for(Map<String, Integer> shot : shots) {
            if (!shot.isEmpty()) {
                int monsterX = shot.get("MonsterX") * spaceBetweenLinesPixels + spaceBetweenLinesPixels / 2;
                int monsterY = shot.get("MonsterY") * spaceBetweenLinesPixels + spaceBetweenLinesPixels / 2 + titelbalkenSizePixels;
                int turmX = shot.get("TurmX") * spaceBetweenLinesPixels + spaceBetweenLinesPixels / 2;
                int turmY = shot.get("TurmY") * spaceBetweenLinesPixels + spaceBetweenLinesPixels / 2 + titelbalkenSizePixels;

                Graphics2D graphics2D = (Graphics2D) g;
                graphics2D.setStroke(new BasicStroke(3));
                graphics2D.setColor(Color.red);
                graphics2D.drawLine(turmX, turmY, monsterX, monsterY);
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(298, titelbalkenSizePixels/3, 172, 27);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("Design speichern", 300, titelbalkenSizePixels - 32);

        g.setColor(Color.BLACK);
        g.fillRect(488, titelbalkenSizePixels/3, 132, 27);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("Design laden", 490, titelbalkenSizePixels - 32);

        g.setColor(Color.BLACK);
        g.fillRect(windowWidthPixels - 132, titelbalkenSizePixels/3, 132, 27);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("Einstellungen", windowWidthPixels - 130, titelbalkenSizePixels - 32);

        g.setColor(Color.BLACK);
        g.fillRect(628, titelbalkenSizePixels/3, 220, 54);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 13));
        if(defaultTurmImage != null) {
            g.fillRect(632, titelbalkenSizePixels/3 + 2, 37, 37);
            g.drawImage(
                    defaultTurmImage,
                    630,
                    titelbalkenSizePixels/3,
                    41,
                    41,
                    null
            );
            if(chosenBuilding == 1){
                g.setColor(Color.BLUE.brighter());
            }
            g.drawString("Turm", 630, titelbalkenSizePixels - 2); // Add text label for Default Turm
            if(chosenBuilding == 1){
                g.setColor(Color.WHITE);
            }
        }
        if(schnellschussTurmImage != null) {
            g.fillRect(691, titelbalkenSizePixels/3 + 2, 37, 37);
            g.drawImage(
                    schnellschussTurmImage,
                    689,
                    titelbalkenSizePixels/3,
                    41,
                    41,
                    null
            );
            if(chosenBuilding == 2){
                g.setColor(Color.BLUE.brighter());
            }
            g.drawString("Minigun", 687, titelbalkenSizePixels - 2); // Add text label for Schnellschuss Turm
            if(chosenBuilding == 2){
                g.setColor(Color.WHITE);
            }
        }
        if(scharfschuetzenTurmImage != null) {
            g.fillRect(750, titelbalkenSizePixels/3 + 2, 37, 37);
            g.drawImage(
                    scharfschuetzenTurmImage,
                    748,
                    titelbalkenSizePixels/3,
                    41,
                    41,
                    null
            );
            if(chosenBuilding == 3){
                g.setColor(Color.BLUE.brighter());
            }
            g.drawString("Sniper", 748, titelbalkenSizePixels - 2); // Add text label for Scharfschuetzen Turm
            if(chosenBuilding == 3){
                g.setColor(Color.WHITE);
            }
        }
        if(mauerImage != null) {
            g.fillRect(809, titelbalkenSizePixels/3 + 2, 37, 37);
            g.drawImage(
                    mauerImage,
                    807,
                    titelbalkenSizePixels/3,
                    41,
                    41,
                    null
            );
            if(chosenBuilding == 0){
                g.setColor(Color.BLUE.brighter());
            }
            g.drawString("Mauer", 807, titelbalkenSizePixels - 2); // Add text label for Default Mauer
            if(chosenBuilding == 0){
                g.setColor(Color.WHITE);
            }
        }

        // Hinzufügen des MouseListeners
        this.addMouseListener(
                new MouseAdapter() {
                    // Wird aufgerufen, wenn die Maus gedrückt wird
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
                                    chosenBuilding = 1;
                                    repaint(628, titelbalkenSizePixels/3, 220, 54);
                                    pressed[1] = false;
                                } else if(e.getX() >= 689 && e.getX() <= 730){
                                    chosenBuilding = 2;
                                    repaint(628, titelbalkenSizePixels/3, 220, 54);
                                    pressed[1] = false;
                                } else if(e.getX() >= 748 && e.getX() <= 789){
                                    chosenBuilding = 3;
                                    repaint(628, titelbalkenSizePixels/3, 220, 54);
                                    pressed[1] = false;
                                } else if(e.getX() >= 807 && e.getX() <= 848){
                                    chosenBuilding = 0;
                                    repaint(628, titelbalkenSizePixels/3, 220, 54);
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
                            if(karte.getBuildings().containsKey(new Coords(x, y))){
                                if(karte.getBuildings().get(new Coords(x, y)).getSpawntime() + 2 <= time) {
                                    try {
                                        new PopupRemoveBuilding(x, y, e.getX(), e.getY());
                                    } catch (IOException ignored) {}
                                }else {
                                    pressed[0] = false;
                                }
                            }else {
                                switch(chosenBuilding){
                                    case 0 -> {
                                        Baubar newBaubar = getBaubar("DefaultMauer", new Coords(x, y));
                                        assert newBaubar != null;
                                        build(newBaubar, x, y);
                                    }
                                    case 1 -> {
                                        Baubar newBaubar = getBaubar("DefaultTurm", new Coords(x, y));
                                        assert newBaubar != null;
                                        build(newBaubar, x, y);
                                    }
                                    case 2 -> {
                                        Baubar newBaubar = getBaubar("SchnellschussTurm", new Coords(x, y));
                                        assert newBaubar != null;
                                        build(newBaubar, x, y);
                                    }
                                    case 3 -> {
                                        Baubar newBaubar = getBaubar("ScharfschuetzenTurm", new Coords(x, y));
                                        assert newBaubar != null;
                                        build(newBaubar, x, y);
                                    }
                                }
                                pressed[0] = false;
                            }
                        }
                    }
                }
        );
    }

    private void setLifeBar(Graphics2D graphics2D, int monsterX, int monsterY, double health, double maxHealth) {
        double lifeInPercent = health / maxHealth;
        int width = (int)(lifeInPercent * (spaceBetweenLinesPixels - 2));
        graphics2D.setStroke(new BasicStroke(3));
        graphics2D.setColor(Color.red);
        graphics2D.drawLine(monsterX + 1, monsterY, monsterX + width, monsterY);
    }

    private void build(Baubar newBaubar, int x, int y) {
        if (newBaubar.getKosten() <= money) {
            if (karte.addBuilding(newBaubar.getPosition(), newBaubar) == null) {
                System.out.println("Something went wrong");
            } else {
                laufendeKosten += newBaubar.getKosten();
                building_update_place = new Rectangle(x, y, spaceBetweenLinesPixels, spaceBetweenLinesPixels);
                building_update = true;
            }
        }
//        setVisible(false);
        pressed[0] = false;
//        dispose();
    }
}
