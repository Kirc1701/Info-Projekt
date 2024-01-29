package project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Graphic extends JFrame {
    private final int space = 27;
    private final int width;
    private final int height;
    private final Karte karte;
    private JDialog popup;


    public Graphic(Karte karte){
        this.karte = karte;
        width = karte.getWidth() * space;
        height = karte.getHeight() * space + 27;
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );
        setBackground(Color.white);
        setSize(width, height);
        setLayout(null);
        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
        popup = new JDialog(getGraphic(), "Select Building", true);
        popup.setLayout(new FlowLayout());
        popup.setVisible(false);
        File fMauer = new File("images/Mauer.jpg");
        File fTurm = new File("images/Turm.jpg");
        File fBasis = new File("images/Basis.jpg");
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
        for(Coords building : karte.getBuildings().keySet()){
            if(karte.getBuildings().get(building).equals("Mauer")){
                g.drawImage(mauer, building.getX()*space+1, building.getY()*space+1, space-2, space-2, null);
            }else if(karte.getBuildings().get(building).equals("Turm")){
                g.drawImage(turm, building.getX()*space+1, building.getY()*space+1, space-2, space-2, null);
            }else if(karte.getBuildings().get(building).equals("Basis")){
                g.drawImage(basis, building.getX()*space+1, building.getY()*space+1, space-2, space-2, null);
                System.out.println("Basis gebaut");
            }
        }
        for(int i = 1; i < karte.getHeight(); i++){
            g.drawLine(0, i*space + 27, width, i*space + 27);
        }
        for(int i = 1; i < karte.getWidth(); i++){
            g.drawLine(i*space,0, i*space, height);
        }
        System.out.println("Done");
        BufferedImage finalMauer = mauer;
        BufferedImage finalTurm = turm;
        final boolean[] pressed = {false};
        this.addMouseListener(
                new MouseListener() {
                    private final ArrayList<Coords> coords = new ArrayList<>();
                    @Override
                    public void mouseClicked(MouseEvent e) {}

                    @Override
                    public void mousePressed(MouseEvent e) {
                        if(!pressed[0]) {
                            pressed[0] = true;
                            int x = e.getX() / space;
                            int y = e.getY() / space;
                            ActionListener actionListener = e1 -> {
                                if (e1.getActionCommand().equals("Turm")) {
                                    if(!karte.addBuilding(new Coords(x, y), "Turm")){
                                        System.out.println("Something went wrong");
                                    }
                                    popup.setVisible(false);
                                    repaint();
                                } else if (e1.getActionCommand().equals("Mauer")) {
                                    if(!karte.addBuilding(new Coords(x, y), "Mauer")){
                                        System.out.println("Something went wrong");
                                    }
                                    popup.setVisible(false);
                                    repaint();
                                }
                            };
                            JButton m = new JButton(new ImageIcon(finalMauer.getScaledInstance(space - 2, space - 2, Image.SCALE_SMOOTH)));
                            m.setActionCommand("Mauer");
                            m.addActionListener(actionListener);

                            JButton t = new JButton(new ImageIcon(finalTurm.getScaledInstance(space - 2, space - 2, Image.SCALE_SMOOTH)));
                            t.setActionCommand("Turm");
                            t.addActionListener(actionListener);

                            JButton c = new JButton("Cancel");
                            c.setActionCommand("");
                            c.addActionListener(actionListener);
                            popup.add(m);
                            popup.add(t);
                            popup.add(c);
                            popup.setSize(150, 100);
                            popup.setLocation(e.getX() - 75, e.getY() - 50);
                            popup.setVisible(true);
                        }
                    }

                    @Override
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

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                }
        );
    }

    public Graphic getGraphic(){
        return this;
    }

    public static void showMap(Karte graphOfKarte){
        new Graphic(graphOfKarte);
    }
}
