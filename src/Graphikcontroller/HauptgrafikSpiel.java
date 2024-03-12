package src.Graphikcontroller;

// Import anderer Klassen innerhalb des Projekts
import src.Coords;
import src.Karte;
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
    private BufferedImage mauerImage = null;
    private BufferedImage turmImage = null;
    private BufferedImage defaultMonsterImage = null;
    private BufferedImage lakaiImage = null;
    private BufferedImage basisImage = null;
    private BufferedImage bossImage = null;
    private BufferedImage backgroundImage = null;
    private final ImageIcon mauerIcon;
    private final ImageIcon turmIcon;

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
        File fTurm = new File("images/Turm.png");
        File fBasis = new File("images/Basis.png");
        File fDefaultMonster = new File("images/DefaultMonster.png");
        File fLakai = new File("images/Lakai.png");
        File fBoss = new File("images/Boss.png");
        File fBackground = new File("images/Background.png");

        // Erstellung eines Images, in welches danach die Bilddateien geladen werden
        try {
            mauerImage = ImageIO.read(fMauer);
            turmImage = ImageIO.read(fTurm);
            basisImage = ImageIO.read(fBasis);
            defaultMonsterImage = ImageIO.read(fDefaultMonster);
            lakaiImage = ImageIO.read(fLakai);
            bossImage = ImageIO.read(fBoss);
            backgroundImage = ImageIO.read(fBackground);
        } catch (IOException ignored) {}

        turmIcon = new ImageIcon(turmImage.getScaledInstance(spaceBetweenLinesPixels - 2, spaceBetweenLinesPixels - 2, Image.SCALE_SMOOTH));
        mauerIcon = new ImageIcon(mauerImage.getScaledInstance(spaceBetweenLinesPixels - 2, spaceBetweenLinesPixels - 2, Image.SCALE_SMOOTH));

        // Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setSize(windowWidthPixels, windowHeightPixels);
        setLayout(null);
        setVisible(true);
    }

    // paint()-Methode
    public void paint(Graphics g) {
        // Aufruf der super.paint()-Methode
//        super.paint(g);
        g.drawImage(
                backgroundImage,
                0,
                0,
                windowWidthPixels,
                windowHeightPixels,
                null
        );

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
            if(building.getType().equals("DefaultMauer")){
                // Zeichnen der Mauer
                g.setColor(Color.black);
                g.drawImage(
                        mauerImage,
                        buildingX,
                        buildingY,
                        spaceBetweenLinesPixels -2,
                        spaceBetweenLinesPixels -2,
                        null
                );

                if(!(building.getHealth() == building.getMaxHealth())) {
                    setLifeBar((Graphics2D) g, buildingX, buildingY, building.getHealth(), building.getMaxHealth());
                }
            }else if(building.getType().equals("DefaultTurm")){
                // Zeichnen des Turms
                g.setColor(Color.black);
                g.drawImage(
                        turmImage,
                        buildingX,
                        buildingY,
                        spaceBetweenLinesPixels -2,
                        spaceBetweenLinesPixels -2,
                        null
                );
                if(!(building.getHealth() == building.getMaxHealth())) {
                    setLifeBar((Graphics2D) g, buildingX, buildingY, building.getHealth(), building.getMaxHealth());
                }            }
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
                                new PopupSetzen(mauerIcon, turmIcon, x, y, e.getX(), e.getY());
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
