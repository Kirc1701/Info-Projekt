package project;

import project.Objekte.Mauer.DefaultMauer;
import project.Objekte.Monster.Monster;
import project.Objekte.Turm.DefaultTurm;
import project.Objekte.Turm.Turm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Graphic extends JFrame{
    //Abstand zwischen den Linien, somit Größe der Kästchen in Pixeln
    public final static int space = 27;
    //Höhe und Breite des geöffneten Fensters in Pixeln
    private final int width;
    private final int height;
    //Verknüpfung mit der logischen Implementierung der Karte
    private final Karte karte;
    //Vorbereitung des popup-Fensters für die Auswahl

    //Konstruktor für die Klasse Graphic
    public Graphic(Karte karte){
        //Initialisierung der Karte
        this.karte = karte;
        //Initialisierung der Höhe und Breite als Vielfaches der Node-Anzahl
        width = karte.getWidth() * space;
        height = karte.getHeight() * space + 27; //Anpassung der Höhe unter Einbeziehung des Titelbalkens
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
        //Erstellung eines Images, in welches danach die Balddateien geladen werden
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
        g.drawImage(
                basisImage,
                karte.getBasis().getPosition().getX()*space+1,
                karte.getBasis().getPosition().getY()*space+1,
                space-2,
                space-2,
                null
        );
        //Darstellen aller Gebäude, in der buildings-Liste in der Game-Logik
        for(Coords building : karte.getBuildings().keySet()){
            //Unterscheidung zwischen unterschiedlichen Arten von Gebäuden
            if(karte.getBuildings().get(building).getType().equals("Mauer")){
                //Zeichnen der Mauer
                g.drawImage(
                        mauerImage,
                        building.getX()*space+1,
                        building.getY()*space+1,
                        space-2,
                        space-2,
                        null
                );
            }else if(karte.getBuildings().get(building).getType().equals("Turm")){
                //Zeichnen des Turms
                g.drawImage(
                        turmImage,
                        building.getX()*space+1,
                        building.getY()*space+1,
                        space-2,
                        space-2,
                        null
                );
            }
        }
        //Zeichnen der Monster
        for(Monster monsters : karte.getMonsterList()){
            g.drawImage(
                    monsterImage,
                    monsters.getPosition().getX()*space+1,
                    monsters.getPosition().getY()*space+1,
                    space-2,
                    space-2,
                    null
            );
        }

        g.setColor(Color.black);
        //Zeichnen der Kanten
        for(int i = 1; i < karte.getHeight(); i++){
            g.drawLine(0, i*space + 27, width, i*space + 27);
        }
        for(int i = 1; i < karte.getWidth(); i++){
            g.drawLine(i*space,0, i*space, height);
        }
        g.setColor(Color.red);
        if(!Main.shotMonster.isEmpty()){
            int monsterX = Main.shotMonster.get("MonsterX") * space + space / 2;
            int monsterY = Main.shotMonster.get("MonsterY") * space + space / 2;
            int turmX = Main.shotMonster.get("TurmX") * space + space / 2;
            int turmY = Main.shotMonster.get("TurmY") * space + space / 2;
            g.drawLine(turmX, turmY, monsterX, monsterY);
        }
        //Übergabe der Bilder für Verwendung im MouseListener
        BufferedImage finalMauer = mauerImage;
        BufferedImage finalTurm = turmImage;
        //boolean der eine häufigere Öffnung des popups verhindert
        final boolean[] pressed = {false};
        //Hinzufügen des MouseListeners
        this.addMouseListener(
                new MouseListener() {
                    //Wird aufgerufen, wenn die Maus gedrückt oder losgelassen wird
                    public void mouseClicked(MouseEvent e) {

                    }
                    //Wird aufgerufen, wenn die Maus gedrückt wird
                    public void mousePressed(MouseEvent e) {
                        JDialog popup;
                        popup = new JDialog(getGraphic(), "Select Building", true);
                        popup.setVisible(false);
                        //Wenn pressed auf false steht, kann das Programm ausgeführt werden
                        if(!pressed[0]) {
                            popup.setLayout(new FlowLayout());
                            //pressed wird auf true gesetzt, um häufigere Öffnung des Fensters zu vermeiden
                            pressed[0] = true;
                            //x und y werden aus dem Event gezogen
                            int x = e.getX() / space;
                            int y = e.getY() / space;
                            //ActionListener für die Buttons im popup
                            ActionListener actionListener = e1 -> {
                                //Wenn der Button "Turm" gedrückt wird
                                if (e1.getActionCommand().equals("Turm")) {
                                    //Es wird ein Turm an den x, y-Koordinaten zum Graphen hinzugefügt
                                    if(!karte.addBuilding(new Coords(x, y), new DefaultTurm(new Coords(x,y)))){
                                        System.out.println("Something went wrong");
                                    }
                                    //Das popup wird deaktiviert
                                    popup.setVisible(false);
                                    Main.building_update_place = new Rectangle(x, y, space, space);
                                    pressed[0] = false;
                                    //Das Fenster wird aktualisiert
                                    Main.building_update = true;
                                //Wenn der Button "Mauer" gedrückt wird
                                } else if (e1.getActionCommand().equals("Mauer")) {
                                    //Es wird eine Mauer an den x, y-Koordinaten zum Graphen hinzugefügt
                                    if(!karte.addBuilding(new Coords(x, y), new DefaultMauer(new Coords(x,y)))){
                                        System.out.println("Something went wrong");
                                    }
                                    //Das popup wird deaktiviert
                                    Main.building_update_place = new Rectangle(x, y, space, space);
                                    popup.repaint();
                                    popup.setVisible(false);
                                    pressed[0] = false;
                                    //Das Fenster wird aktualisiert
                                    Main.building_update = true;
                                }else{
                                    popup.setVisible(false);
                                    pressed[0] = false;
                                }
                            };
                            //Erstellung der Buttons
                            JButton m = new JButton(new ImageIcon(finalMauer.getScaledInstance(space - 2, space - 2, Image.SCALE_SMOOTH)));
                            m.setActionCommand("Mauer");
                            m.addActionListener(actionListener);

                            JButton t = new JButton(new ImageIcon(finalTurm.getScaledInstance(space - 2, space - 2, Image.SCALE_SMOOTH)));
                            t.setActionCommand("Turm");
                            t.addActionListener(actionListener);

                            JButton c = new JButton("Cancel");
                            c.setActionCommand("");
                            c.addActionListener(actionListener);

                            //Hinzufügen der Buttons
                            popup.add(m);
                            popup.add(t);
                            popup.add(c);
                            //Finalisierung des popups und Darstellung des popups
                            popup.setSize(150, 100);
                            popup.setLocation(e.getX() - 75, e.getY() - 50);
                            popup.setVisible(true);
                            pressed[0] = true;
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

    public Graphic getGraphic() {
        return this;
    }

    public void animateShoot() {

    }
}
