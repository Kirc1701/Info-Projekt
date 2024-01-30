package project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

public class Graphic extends JFrame {
    //Abstand zwischen den Linien, somit Größe der Kästchen in Pixeln
    private final int space = 27;
    //Höhe und Breite des geöffneten Fensters in Pixeln
    private final int width;
    private final int height;
    //Verknüpfung mit der logischen Implementierung der Karte
    private final Karte karte;
    //Vorbereitung des popup-Fensters für die Auswahl
    private JDialog popup;

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
        popup = new JDialog(this, "Select Building", true);
        popup.setLayout(new FlowLayout());
        popup.setVisible(false);
        //Zugriff auf die Bilddateien
        File fMauer = new File("images/Mauer.jpg");
        File fTurm = new File("images/Turm.jpg");
        File fBasis = new File("images/Basis.jpg");
        //Erstellung eines Images, in welches danach die Balddateien geladen werden
        BufferedImage mauer = null;
        BufferedImage turm = null;
        BufferedImage basis = null;
        try {
            mauer = ImageIO.read(fMauer);
            turm = ImageIO.read(fTurm);
            basis = ImageIO.read(fBasis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Darstellen aller Gebäude, in der buildings-Liste in der Game-Logik
        for(Coords building : karte.getBuildings().keySet()){
            //Unterscheidung zwischen unterschiedlichen Arten von Gebäuden
            if(karte.getBuildings().get(building).equals("Mauer")){
                //Zeichnen der Mauer
                g.drawImage(
                        mauer,
                        building.getX()*space+1,
                        building.getY()*space+1,
                        space-2,
                        space-2,
                        null
                );
            }else if(karte.getBuildings().get(building).equals("Turm")){
                //Zeichnen des Turms
                g.drawImage(
                        turm,
                        building.getX()*space+1,
                        building.getY()*space+1,
                        space-2,
                        space-2,
                        null
                );
            }else if(karte.getBuildings().get(building).equals("Basis")){
                //Zeichnen der Basis
                g.drawImage(
                        basis,
                        building.getX()*space+1,
                        building.getY()*space+1,
                        space-2,
                        space-2,
                        null
                );
            }
        }
        //Zeichnen der Kanten
        for(int i = 1; i < karte.getHeight(); i++){
            g.drawLine(0, i*space + 27, width, i*space + 27);
        }
        for(int i = 1; i < karte.getWidth(); i++){
            g.drawLine(i*space,0, i*space, height);
        }
        //Übergabe der Bilder für Verwendung im MouseListener
        BufferedImage finalMauer = mauer;
        BufferedImage finalTurm = turm;
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
                        //Wenn pressed auf false steht, kann das Programm ausgeführt werden
                        if(!pressed[0]) {
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
                                    if(!karte.addBuilding(new Coords(x, y), "Turm")){
                                        System.out.println("Something went wrong");
                                    }
                                    //Das popup wird deaktiviert
                                    popup.setVisible(false);
                                    //Das Fenster wird aktualisiert
                                    repaint();
                                //Wenn der Button "Mauer" gedrückt wird
                                } else if (e1.getActionCommand().equals("Mauer")) {
                                    //Es wird eine Mauer an den x, y-Koordinaten zum Graphen hinzugefügt
                                    if(!karte.addBuilding(new Coords(x, y), "Mauer")){
                                        System.out.println("Something went wrong");
                                    }
                                    //Das popup wird deaktiviert
                                    popup.setVisible(false);
                                    //Das Fenster wird aktualisiert
                                    repaint();
                                }else{
                                    popup.setVisible(false);
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
                        }
                    }
                    //Wird aufgerufen, wenn die Maus losgelassen wird
                    public void mouseReleased(MouseEvent e) {
//                        if(!pressed){
//                            pressed = true;
//                            popup = new JDialog(getGraphic(), "Select Building", true);
//                            popup.setLayout(new FlowLayout());
//                            ActionListener actionListener = e12 -> {
//                                popup.setVisible(false);
//                                if (e12.getActionCommand().equals("Turm")) {
//                                    for(Coords coords : coords){
//                                        karte.addBuilding(new Coords(coords.getX(), coords.getY()), "Turm");
//                                    }
//                                    repaint();
//                                } else if (e12.getActionCommand().equals("Mauer")) {
//                                    for(Coords coords : coords) {
//                                        karte.addBuilding(new Coords(coords.getX(), coords.getY()), "Mauer");
//                                    }
//                                    repaint();
//                                }
//                            };
//                            JButton m = new JButton(new ImageIcon(finalMauer.getScaledInstance(space - 2, space - 2, Image.SCALE_SMOOTH)));
//                            m.setActionCommand("Mauer");
//                            m.addActionListener(actionListener);
//
//                            JButton t = new JButton(new ImageIcon(finalTurm.getScaledInstance(space - 2, space - 2, Image.SCALE_SMOOTH)));
//                            t.setActionCommand("Turm");
//                            t.addActionListener(actionListener);
//
//                            JButton c = new JButton("Cancel");
//                            c.setActionCommand("");
//                            c.addActionListener(actionListener);
//                            popup.add(m);
//                            popup.add(t);
//                            popup.add(c);
//                            popup.setSize(150, 100);
//                            popup.setLocation(e.getX() - 75, e.getY() - 50);
//                            popup.setVisible(true);
//                        }
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

    //statische Methode, welche von der Main-Klasse aufgerufen werden kann
    public static void showMap(Karte graphOfKarte){
        //Initialisierung
        new Graphic(graphOfKarte);
    }
}
