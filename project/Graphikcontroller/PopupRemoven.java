package project.Graphikcontroller;

import project.Coords;
import project.Main;
import project.Objekte.Mauer.DefaultMauer;
import project.Objekte.Turm.DefaultTurm;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static project.Graphikcontroller.HauptgrafikSpiel.pressed;
import static project.Graphikcontroller.HauptgrafikSpiel.space;
import static project.Main.karte;

public class PopupRemoven extends JFrame implements ActionListener {
    private int x;
    private int y;
    private int locX;
    private int locY;

    public PopupRemoven(int x, int y, int locX, int locY) throws IOException {
        this.x = x;
        this.y = y;
        this.locX = locX;
        this.locY = locY;
        File fMuell = new File("images/muell.jpg");
        BufferedImage muell = null;
        muell = ImageIO.read(fMuell);
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );
        //Erstellung der Buttons
        JButton m = new JButton(new ImageIcon(muell.getScaledInstance(space - 2, space - 2, Image.SCALE_SMOOTH)));
        m.setActionCommand("Delete");
        m.addActionListener(this);

        JButton c = new JButton("Cancel");
        c.setActionCommand("");
        c.addActionListener(this);

        //Hinzufügen der Buttons
        add(m);
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
        if (e1.getActionCommand().equals("Delete")) {
            if(!karte.removeBuilding(new Coords(x,y))){
                System.out.println("Something went wrong");
            }
            //Das popup wird deaktiviert
            Main.building_update_place = new Rectangle(x, y, space, space);
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
