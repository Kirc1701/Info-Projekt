package src.Graphikcontroller;

// Import anderer Klassen innerhalb des Projekts
import src.Coords;
import src.Karte;
import src.Main;
import src.Objekte.Monster.Monster;
import src.Objekte.Objekt;

// Import anderer Klassen von außerhalb des Projekts
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    public final static boolean[] pressed = {false};
    private BufferedImage mauerImage = null;
    private BufferedImage turmImage = null;
    private BufferedImage defaultMonsterImage = null;
    private BufferedImage lakaiImage = null;
    private BufferedImage basisImage = null;
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
        File fMauer = new File("images/Mauer.jpg");
        File fTurm = new File("images/Turm.jpg");
        File fBasis = new File("images/Basis.jpg");
        File fDefaultMonster = new File("images/DefaultMonster.jpg");
        File fLakai = new File("images/Lakai.jpg");

        // Erstellung eines Images, in welches danach die Bilddateien geladen werden
        try {
            mauerImage = ImageIO.read(fMauer);
            turmImage = ImageIO.read(fTurm);
            basisImage = ImageIO.read(fBasis);
            defaultMonsterImage = ImageIO.read(fDefaultMonster);
            lakaiImage = ImageIO.read(fLakai);
        } catch (IOException e) {
            e.printStackTrace();
        }

        turmIcon = new ImageIcon(turmImage.getScaledInstance(spaceBetweenLinesPixels - 2, spaceBetweenLinesPixels - 2, Image.SCALE_SMOOTH));
        mauerIcon = new ImageIcon(mauerImage.getScaledInstance(spaceBetweenLinesPixels - 2, spaceBetweenLinesPixels - 2, Image.SCALE_SMOOTH));

        // Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setBackground(Color.white);
        setSize(windowWidthPixels, windowHeightPixels);
        setLayout(null);
        setVisible(true);
    }

    // paint()-Methode
    public void paint(Graphics g) {
        // Aufruf der super.paint()-Methode
        super.paint(g);

        // Darstellung der Basis, wenn vorhanden
        if(karte.getBasis() != null) {
            //Position der linken oberen Ecke der Basis
            int basisXCoordinate = karte.getBasis().getPosition().getX() * spaceBetweenLinesPixels + 1;
            int basisYCoordinate = karte.getBasis().getPosition().getY() * spaceBetweenLinesPixels + 1 + titelbalkenSizePixels;

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
            double lifeInPercent = ((double) karte.getBasis().getHealth()) / ((double) karte.getBasis().getMaxHealth());
            int widthOfLifeline = (int) (lifeInPercent * (spaceBetweenLinesPixels - 2));
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setStroke(new BasicStroke(3));
            graphics2D.setColor(Color.red);
            graphics2D.drawLine(basisXCoordinate + 1, basisYCoordinate, basisXCoordinate + widthOfLifeline, basisYCoordinate);
        }

        // Darstellen aller Gebäude, in der buildings-Liste in der Game-Logik
        for(Coords buildingCoords : karte.getBuildings().keySet()){
            Objekt building = karte.getBuildings().get(buildingCoords);

            // Koordinaten des Gebäudes
            int buildingX = buildingCoords.getX()* spaceBetweenLinesPixels +1;
            int buildingY = buildingCoords.getY()* spaceBetweenLinesPixels +1 + titelbalkenSizePixels;

            // Unterscheidung zwischen unterschiedlichen Arten von Gebäuden
            if(building.getType().equals("Mauer")){
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

                setLifeBar((Graphics2D) g, buildingX, buildingY, building.getHealth(), building.getMaxHealth());

            }else if(building.getType().equals("Turm")){
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

                setLifeBar((Graphics2D) g, buildingX, buildingY, building.getHealth(), building.getMaxHealth());
            }
        }

        // Zeichnen der Monster
        for(Monster monster : karte.getMonsterList()){

            int monsterX = monster.getPosition().getX()* spaceBetweenLinesPixels +1;
            int monsterY = monster.getPosition().getY()* spaceBetweenLinesPixels +1 + titelbalkenSizePixels;
            g.setColor(Color.black);
            if(monster.getType().equals("Default")) {
                g.drawImage(
                        defaultMonsterImage,
                        monsterX,
                        monsterY,
                        spaceBetweenLinesPixels - 2,
                        spaceBetweenLinesPixels - 2,
                        null
                );
            } else if (monster.getType().equals("Lakai")) {
                g.drawImage(
                        lakaiImage,
                        monsterX,
                        monsterY,
                        spaceBetweenLinesPixels - 2,
                        spaceBetweenLinesPixels - 2,
                        null
                );
            }
            setLifeBar((Graphics2D) g, monsterX, monsterY, monster.getHealth(), monster.getMaxHealth());
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

        // Zeichnen des Schusses
        for(Map<String, Integer> shot : Main.shotMonster) {
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

        // Hinzufügen des MouseListeners
        this.addMouseListener(
                new MouseListener() {
                    // Wird aufgerufen, wenn die Maus gedrückt oder losgelassen wird
                    public void mouseClicked(MouseEvent e) {

                    }

                    // Wird aufgerufen, wenn die Maus gedrückt wird
                    public void mousePressed(MouseEvent e) {
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
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }else {
                                new PopupSetzen(mauerIcon, turmIcon, x, y, e.getX(), e.getY());
                            }
                        }
                    }

                    //Wird aufgerufen, wenn die Maus losgelassen wird
                    public void mouseReleased(MouseEvent e) {
                    }

                    //Wird aufgerufen, wenn die Maus in den Frame kommt
                    public void mouseEntered(MouseEvent e) {
                    }

                    //Wird aufgerufen, wenn die Maus den Frame verlässt
                    public void mouseExited(MouseEvent e) {
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
