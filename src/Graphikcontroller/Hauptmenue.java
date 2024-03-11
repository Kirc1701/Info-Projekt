package src.Graphikcontroller;

import src.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Hauptmenue extends JFrame implements MouseListener {
    public static int chosenLevel = 1;
    private BufferedImage bufferedImage = null;
    public Hauptmenue(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );
        File file = new File("images/BackgroundMenu.png");
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException ignored) {}
        setSize(450, 300);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 225,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150
        );
        addMouseListener(this);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(
                bufferedImage,
                0,
                0,
                450,
                300,
                null
        );
        g.setFont(new Font("Helvetica", Font.BOLD, 18));
        g.drawString("Starte Spiel | Gewähltes Level: Level " + chosenLevel, 59, 78);
        g.drawString("-> Levelauswahl", 152, 121);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() >= 52 && e.getX() <= 399 && e.getY() >= 52 && e.getY() <= 89){
            Main.screenSelection = 1;
            setVisible(false);
            dispose();
        }else if(e.getX() >= 147 && e.getX() <= 299 && e.getY() >= 95 && e.getY() <= 132){
            new LevelAuswahl();
            setVisible(false);
            dispose();
        }
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
