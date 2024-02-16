package src.Graphikcontroller;

import src.Coords;
import src.Objekte.Baubar.Baubar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static src.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;
import static src.Graphikcontroller.HauptgrafikSpiel.pressed;
import static src.Main.*;
import static src.Objekte.Baubar.Baubar.getBaubar;

public class PopupSetzen extends JFrame implements ActionListener {
    private final int x;
    private final int y;

    public PopupSetzen(ImageIcon mauerIcon, ImageIcon turmIcon, int x, int y, int locX, int locY){
        this.x = x;
        this.y = y;
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        // Es kann ein neues Popup erzeugt werden
                        pressed[0] = false;
                        dispose();
                    }
                }
        );
        //Erstellung der Buttons
        JButton m = new JButton(mauerIcon);
        m.setActionCommand("DefaultMauer");
        m.addActionListener(this);

        JButton t = new JButton(turmIcon);
        t.setActionCommand("DefaultTurm");
        t.addActionListener(this);

        JButton c = new JButton("Cancel");
        c.setActionCommand("Cancel");
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
        if (!e1.getActionCommand().equals("Cancel")) {
            Baubar newBaubar = getBaubar(e1.getActionCommand(), new Coords(x, y));
            assert newBaubar != null;
            if (newBaubar.getKosten() <= money) {
                if (!karte.addBuilding(newBaubar.getPosition(), newBaubar)) {
                    System.out.println("Something went wrong");
                } else {
                    laufendeKosten += newBaubar.getKosten();
//                    System.out.println("Laufende Kosten " + laufendeKosten);
                    building_update_place = new Rectangle(x, y, spaceBetweenLinesPixels, spaceBetweenLinesPixels);
                    building_update = true;
                }
            }
        }
        setVisible(false);
        pressed[0] = false;
        dispose();
    }
}
