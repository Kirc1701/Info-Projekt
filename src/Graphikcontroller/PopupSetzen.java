package src.Graphikcontroller;

import src.Coords;
import src.Objekte.Baubar.Baubar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static src.Graphikcontroller.HauptgrafikSpiel.*;
import static src.Main.*;
import static src.Objekte.Baubar.Baubar.getBaubar;

public class PopupSetzen extends JFrame implements MouseListener, ActionListener {
    private final int x;
    private final int y;

    public PopupSetzen(int x, int y, int locX, int locY){
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

        JButton c = new JButton("Cancel");
        c.setBounds(100, 127, 100, 30);
        c.addActionListener(this);

        //Hinzufügen der Buttons
        add(c);
        addMouseListener(this);
        //Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setBackground(Color.white);
        setSize(300, 200);
        setLocation(locX - 150, locY - 100);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        if(defaultTurmImage != null) {
            g.drawImage(
                    defaultTurmImage,
                    30,
                    67,
                    2*(spaceBetweenLinesPixels - 2),
                    2*(spaceBetweenLinesPixels - 2),
                    null
            );
            g.drawString("Turm", 30, 140); // Add text label for Default Turm
        }
        if(schnellschussTurmImage != null) {
            g.drawImage(
                    schnellschussTurmImage,
                    80,
                    67,
                    2*(spaceBetweenLinesPixels - 2),
                    2*(spaceBetweenLinesPixels - 2),
                    null
            );
            g.drawString("Minigun", 85, 140); // Add text label for Schnellschuss Turm
        }
        if(scharfschuetzenTurmImage != null) {
            g.drawImage(
                    scharfschuetzenTurmImage,
                    150,
                    67,
                    2*(spaceBetweenLinesPixels - 2),
                    2*(spaceBetweenLinesPixels - 2),
                    null
            );
            g.drawString("Scharfschütze", 145, 140); // Add text label for Scharfschuetzen Turm
        }
        if(mauerImage != null) {
            g.drawImage(
                    mauerImage,
                    225,
                    67,
                    2*(spaceBetweenLinesPixels - 2),
                    2*(spaceBetweenLinesPixels - 2),
                    null
            );
            g.drawString("Mauer", 240, 140); // Add text label for Default Mauer
        }
    }

    @Override
    public void actionPerformed(ActionEvent e1) {
        setVisible(false);
        pressed[0] = false;
        dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getY() >= 67 && e.getY() <= 117) {
            if(e.getX()>= 40 && e.getX() <= 90) {
                Baubar newBaubar = getBaubar("DefaultTurm", new Coords(x, y));
                assert newBaubar != null;
                build(newBaubar);
            } else if(e.getX()>= 100 && e.getX() <= 150) {
                Baubar newBaubar = getBaubar("SchnellschussTurm", new Coords(x, y));
                assert newBaubar != null;
                build(newBaubar);
            } else if(e.getX()>= 160 && e.getX() <= 210) {
                Baubar newBaubar = getBaubar("ScharfschuetzenTurm", new Coords(x, y));
                assert newBaubar != null;
                build(newBaubar);
            } else if(e.getX()>= 220 && e.getX() <= 270) {
                Baubar newBaubar = getBaubar("DefaultMauer", new Coords(x, y));
                assert newBaubar != null;
                build(newBaubar);
            }
        }
    }

    private void build(Baubar newBaubar) {
        if (newBaubar.getKosten() <= money) {
            if (karte.addBuilding(newBaubar.getPosition(), newBaubar) == null) {
                System.out.println("Something went wrong");
            } else {
                laufendeKosten += newBaubar.getKosten();
                building_update_place = new Rectangle(x, y, spaceBetweenLinesPixels, spaceBetweenLinesPixels);
                building_update = true;
            }
        }
        setVisible(false);
        pressed[0] = false;
        dispose();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
