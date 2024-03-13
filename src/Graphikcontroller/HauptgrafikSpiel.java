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

// Das Hauptfenster, auf dem das Spiel abläuft.
public class HauptgrafikSpiel extends JFrame{
    // Abstand zwischen den Linien, somit Größe der Kästchen in Pixeln
    public final static int spaceBetweenLinesPixels = 27;
    public final static int titelbalkenSizePixels = 27 + 27;

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
    private BufferedImage backgroundImageLevel1 = null;
    private BufferedImage backgroundImageLevel2 = null;
    private BufferedImage backgroundImageLevel3 = null;
    private BufferedImage backgroundImageLevel4 = null;
    private BufferedImage backgroundImageLevel5 = null;

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
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );

        // Zugriff auf die Bilddateien
        File fMauer = new File("images/Mauer.png");
        File fDefaultTurm = new File("images/DefaultTurm.png");
        File fSchnellschussTurm = new File("images/SchnellschussTurm.png");
        File fScharfschuetzenTurm = new File("images/ScharfschuetzenTurm.png");
        File fBasis = new File("images/Basis.png");
        File fDefaultMonster = new File("images/DefaultMonster.png");
        File fGolem = new File("images/Golem.png");
        File fSprinter = new File("images/Sprinter.png");
        File fBombenschiff = new File("images/Bombenschiff.png");
        File fLakai = new File("images/Lakai.png");
        File fBoss = new File("images/Boss.png");
        File fBackgroundLevel1 = new File("images/BackgroundLevel1.png");
        File fBackgroundLevel2 = new File("images/BackgroundLevel2.jpg");
        File fBackgroundLevel3 = new File("images/BackgroundLevel3.jpg");
        File fBackgroundLevel4 = new File("images/BackgroundLevel4.jpg");
        File fBackgroundLevel5 = new File("images/BackgroundLevel5.jpg");

        // Erstellung eines Images, in welches danach die Bilddateien geladen werden
        try {
            mauerImage = ImageIO.read(fMauer);
            defaultTurmImage = ImageIO.read(fDefaultTurm);
            schnellschussTurmImage = ImageIO.read(fSchnellschussTurm);
            scharfschuetzenTurmImage = ImageIO.read(fScharfschuetzenTurm);
            basisImage = ImageIO.read(fBasis);
            defaultMonsterImage = ImageIO.read(fDefaultMonster);
            golemImage = ImageIO.read(fGolem);
            sprinterImage = ImageIO.read(fSprinter);
//            bombenschiffImage = ImageIO.read(fBombenschiff);
            lakaiImage = ImageIO.read(fLakai);
            bossImage = ImageIO.read(fBoss);
            backgroundImageLevel1 = ImageIO.read(fBackgroundLevel1);
            backgroundImageLevel2 = ImageIO.read(fBackgroundLevel2);
            backgroundImageLevel3 = ImageIO.read(fBackgroundLevel3);
            backgroundImageLevel4 = ImageIO.read(fBackgroundLevel4);
            backgroundImageLevel5 = ImageIO.read(fBackgroundLevel5);
        } catch (IOException ignored) {}

        // Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setSize(windowWidthPixels, windowHeightPixels);
        setLayout(null);
        setVisible(true);
    }

    // paint()-Methode
    public void paint(Graphics g) {
        if (karte.getLevel().getClass().equals(Level1.class)) {
            System.out.println(karte.getLevel().getClass());
            g.drawImage(
                    backgroundImageLevel1,
                    0,
                    0,
                    windowWidthPixels,
                    windowHeightPixels,
                    null
            );
        } else if (karte.getLevel().getClass().equals(Level2.class)) {
            g.drawImage(
                    backgroundImageLevel2,
                    0,
                    0,
                    windowWidthPixels,
                    windowHeightPixels,
                    null
            );
        } else if (karte.getLevel().getClass().equals(Level3.class)) {
            g.drawImage(
                    backgroundImageLevel3,
                    0,
                    0,
                    windowWidthPixels,
                    windowHeightPixels,
                    null
            );
        } else if (karte.getLevel().getClass().equals(Level4.class)) {
            g.drawImage(
                    backgroundImageLevel4,
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
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Geld: " + geld, 10, titelbalkenSizePixels - 5);

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
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("Design speichern", 300, titelbalkenSizePixels - 5);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("Design laden", 490, titelbalkenSizePixels - 5);


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
                                } else if(e.getX() >= 490 && e.getX() <= 660){
                                    new Load();
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
                                try {
                                    new PopupRemoveBuilding(x, y, e.getX(), e.getY());
                                } catch (IOException ignored) {}
                            }else {
                                new PopupSetzen(x, y, e.getX(), e.getY());
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
}
