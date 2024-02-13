package project.Graphikcontroller;

import project.Coords;
import project.Main;
import project.Objekte.Mauer.DefaultMauer;
import project.Objekte.Turm.DefaultTurm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import static project.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;
import static project.Graphikcontroller.HauptgrafikSpiel.pressed;
import static project.Main.karte;

public class PopupSetzen extends JFrame implements ActionListener {
    private final int x;
    private final int y;

    public PopupSetzen(BufferedImage finalMauer, BufferedImage finalTurm, int x, int y, int locX, int locY){
        this.x = x;
        this.y = y;
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                    }
                }
        );
        //Erstellung der Buttons
        JButton m = new JButton(new ImageIcon(finalMauer.getScaledInstance(spaceBetweenLinesPixels - 2, spaceBetweenLinesPixels - 2, Image.SCALE_SMOOTH)));
        m.setActionCommand("Mauer");
        m.addActionListener(this);

        JButton t = new JButton(new ImageIcon(finalTurm.getScaledInstance(spaceBetweenLinesPixels - 2, spaceBetweenLinesPixels - 2, Image.SCALE_SMOOTH)));
        t.setActionCommand("Turm");
        t.addActionListener(this);

        JButton c = new JButton("Cancel");
        c.setActionCommand("");
        c.addActionListener(this);

        //Hinzufügen der Buttons
        add(m);
        add(t);
        add(c);
        //Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setBackground(Color.white);
        setSize(150, 100);
        setLocation(locX - 75, locY - 50);
        setLayout(new FlowLayout());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e1) {
        if (e1.getActionCommand().equals("Turm")) {
            //Es wird ein Turm an den x, y-Koordinaten zum Graphen hinzugefügt
            if(!karte.addBuilding(new Coords(x, y), new DefaultTurm(new Coords(x,y)))){
                System.out.println("Something went wrong");
            }
            //Das popup wird deaktiviert
            setVisible(false);
            Main.building_update_place = new Rectangle(x, y, spaceBetweenLinesPixels, spaceBetweenLinesPixels);
            pressed[0] = false;
            //Das Fenster wird aktualisiert
            Main.building_update = true;
            dispose();
            //Wenn der Button "Mauer" gedrückt wird
        } else if (e1.getActionCommand().equals("Mauer")) {
            //Es wird eine Mauer an den x, y-Koordinaten zum Graphen hinzugefügt
            if(!karte.addBuilding(new Coords(x, y), new DefaultMauer(new Coords(x,y)))){
                System.out.println("Something went wrong");
            }
            //Das popup wird deaktiviert
            Main.building_update_place = new Rectangle(x, y, spaceBetweenLinesPixels, spaceBetweenLinesPixels);
            setVisible(false);
            pressed[0] = false;
            //Das Fenster wird aktualisiert
            Main.building_update = true;
            dispose();
        }else{
            setVisible(false);
            pressed[0] = false;
            dispose();
        }
    }
}
