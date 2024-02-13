package project.Graphikcontroller;

import project.Coords;
import project.Main;

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

// Ein Fenster mit dem der User ein Gebäude entfernen kann
public class PopupRemoveBuilding extends JFrame implements ActionListener {
    // Koordinaten des Gebäudes
    private final int targetXCoordinate;
    private final int targetYCoordinate;

    // Konstruktor des Frames + Initialisierung
    // targetXCoordinate: x-Koordinate des Gebäudes NICHT in Pixeln
    // targetYCoordinate: y-Koordinate des Gebäudes NICHT in Pixeln
    // locationX: x-Koordinate der linken oberen Ecke des Popups in Pixeln
    // locationY: y-Koordinate der linken oberen Ecke des Popups in Pixeln
    public PopupRemoveBuilding(int targetXCoordinate, int targetYCoordinate, int locationX, int locationY) throws IOException {
        //Übergabe der Gebäudekoordinaten
        this.targetXCoordinate = targetXCoordinate;
        this.targetYCoordinate = targetYCoordinate;

        File trashcanFile = new File("images/Trashcan.jpg");
        BufferedImage trashcanImage;
        trashcanImage = ImageIO.read(trashcanFile);
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
        JButton m = new JButton(new ImageIcon(trashcanImage.getScaledInstance(space - 2, space - 2, Image.SCALE_SMOOTH)));
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
        setLocation(locationX - 75, locationY - 50);
        setLayout(new FlowLayout());
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e1) {
        if (e1.getActionCommand().equals("Delete")) {
            if(!karte.removeBuilding(new Coords(targetXCoordinate, targetYCoordinate))){
                System.out.println("Something went wrong");
            }
            //Das popup wird deaktiviert
            Main.building_update_place = new Rectangle(targetXCoordinate, targetYCoordinate, space, space);
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
