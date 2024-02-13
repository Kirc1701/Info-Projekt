package project.Graphikcontroller;

import project.Coords;
import project.Karte;
import project.Main;
import project.Objekte.Monster.Monster;
import project.Objekte.Objekt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

public class HauptgrafikSpiel extends JFrame{
    //Abstand zwischen den Linien, somit Größe der Kästchen in Pixeln
    public final static int space = 27;
    public final static int titelbalken = 27;
    //Höhe und Breite des geöffneten Fensters in Pixeln
    private final int width;
    private final int height;
    //Verknüpfung mit der logischen Implementierung der Karte
    private final Karte karte;
    //Vorbereitung des popup-Fensters für die Auswahl
    //boolean der eine häufigere Öffnung des popups verhindert
    public final static boolean[] pressed = {false};
    //Konstruktor für die Klasse Graphic
    public HauptgrafikSpiel(Karte karte){
        //Initialisierung der Karte
        this.karte = karte;
        //Initialisierung der Höhe und Breite als Vielfaches der Node-Anzahl
        width = karte.getWidth() * space;
        height = karte.getHeight() * space + titelbalken; //Anpassung der Höhe unter Einbeziehung des Titelbalkens
        //WindowListener um das Schließen des Fensters zu registrieren
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );
        //Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setBackground(Color.white);
        setSize(width, height);
        setLayout(null);
        setVisible(true);
    }

    //paint()-Methode
    public void paint(Graphics g) {
        //Aufruf der super.paint()-Methode
        super.paint(g);
        //Initialisierung des popup-Fensters ohne Darstellung
        //Zugriff auf die Bilddateien
        File fMauer = new File("images/Mauer.jpg");
        File fTurm = new File("images/Turm.jpg");
        File fBasis = new File("images/Basis.jpg");
        File fMonster = new File("images/Monster.jpg");
        //Erstellung eines Images, in welches danach die Bilddateien geladen werden
        BufferedImage mauerImage = null;
        BufferedImage turmImage = null;
        BufferedImage basisImage = null;
        BufferedImage monsterImage = null;
        try {
            mauerImage = ImageIO.read(fMauer);
            turmImage = ImageIO.read(fTurm);
            basisImage = ImageIO.read(fBasis);
            monsterImage = ImageIO.read(fMonster);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(karte.getBasis() != null) {
            int basisX = karte.getBasis().getPosition().getX() * space + 1;
            int basisY = karte.getBasis().getPosition().getY() * space + 1 + titelbalken;
            g.setColor(Color.black);
            g.drawImage(
                    basisImage,
                    basisX,
                    basisY,
                    space - 2,
                    space - 2,
                    null
            );
            g.setColor(Color.red);
            double lifeInPercent = ((double) karte.getBasis().getHealth()) / ((double) karte.getBasis().getMaxHealth());
            int width = (int) (lifeInPercent * (space - 2));
            g.drawLine(basisX, basisY, basisX + width, basisY);
        }
        //Zeichnen der Monster
        for(Monster monster : karte.getMonsterList()){
            int monsterX = monster.getPosition().getX()*space+1;
            int monsterY = monster.getPosition().getY()*space+1 + titelbalken;
            g.setColor(Color.black);
            g.drawImage(
                    monsterImage,
                    monsterX,
                    monsterY,
                    space-2,
                    space-2,
                    null
            );
            g.setColor(Color.red);
            double lifeInPercent = ((double) monster.getHealth()) /  ((double) monster.getMaxHealth());
            int width = (int)(lifeInPercent * (space - 2));
            g.drawLine(monsterX, monsterY, monsterX + width, monsterY);
        }
        //Darstellen aller Gebäude, in der buildings-Liste in der Game-Logik
        for(Coords buildingCoords : karte.getBuildings().keySet()){
            Objekt building = karte.getBuildings().get(buildingCoords);
            //Unterscheidung zwischen unterschiedlichen Arten von Gebäuden
            if(building.getType().equals("Mauer")){
                int mauerX = buildingCoords.getX()*space+1;
                int mauerY = buildingCoords.getY()*space+1 + titelbalken;
                //Zeichnen der Mauer
                g.setColor(Color.black);
                g.drawImage(
                        mauerImage,
                        mauerX,
                        mauerY,
                        space-2,
                        space-2,
                        null
                );
                g.setColor(Color.red);
                double lifeInPercent = ((double) building.getHealth()) /  ((double) building.getMaxHealth());
                int width = (int)(lifeInPercent * (space - 2));
                g.drawLine(mauerX, mauerY, mauerX + width, mauerY);
            }else if(building.getType().equals("Turm")){
                int turmX = buildingCoords.getX()*space+1;
                int turmY = buildingCoords.getY()*space+1 + titelbalken;
                //Zeichnen des Turms
                g.setColor(Color.black);
                g.drawImage(
                        turmImage,
                        turmX,
                        turmY,
                        space-2,
                        space-2,
                        null
                );
                g.setColor(Color.red);
                double lifeInPercent = ((double) building.getHealth()) /  ((double) building.getMaxHealth());
                int width = (int)(lifeInPercent * (space - 2));
                g.drawLine(turmX, turmY, turmX + width, turmY);
            }
        }

        g.setColor(Color.black);
        //Zeichnen der Kanten
        for(int i = 1; i < karte.getHeight(); i++){
            g.drawLine(0, i*space + titelbalken, width, i*space + titelbalken);
        }
        for(int i = 1; i < karte.getWidth(); i++){
            g.drawLine(i*space,0, i*space, height);
        }
        g.setColor(Color.red);
        if(!Main.shotMonster.isEmpty()){
            int monsterX = Main.shotMonster.get("MonsterX") * space + space / 2;
            int monsterY = Main.shotMonster.get("MonsterY") * space + space / 2 + titelbalken;
            int turmX = Main.shotMonster.get("TurmX") * space + space / 2;
            int turmY = Main.shotMonster.get("TurmY") * space + space / 2 + titelbalken;
            g.drawLine(turmX, turmY, monsterX, monsterY);
        }
        //Übergabe der Bilder für Verwendung im MouseListener
        BufferedImage finalMauer = mauerImage;
        BufferedImage finalTurm = turmImage;
        //Hinzufügen des MouseListeners
        this.addMouseListener(
                new MouseListener() {
                    //Wird aufgerufen, wenn die Maus gedrückt oder losgelassen wird
                    public void mouseClicked(MouseEvent e) {

                    }
                    //Wird aufgerufen, wenn die Maus gedrückt wird
                    public void mousePressed(MouseEvent e) {
                        //Wenn pressed auf false steht, kann das Programm ausgeführt werden
                        if(!pressed[0]) {
                            //pressed wird auf true gesetzt, um häufigere Öffnung des Fensters zu vermeiden
                            pressed[0] = true;
                            //x und y werden aus dem Event gezogen
                            int x = e.getX() / space;
                            int y = (e.getY() - titelbalken) / space;
                            if(karte.getBuildings().containsKey(new Coords(x, y))){
                                try {
                                    new PopupRemoveBuilding(x, y, e.getX(), e.getY());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }else {
                                new PopupSetzen(finalMauer, finalTurm, x, y, e.getX(), e.getY());
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
}
