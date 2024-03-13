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
        setSize(900, 600);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 450,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 300
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
                900,
                600,
                null
        );
        g.setFont(new Font("Helvetica", Font.BOLD, 36));
        g.drawString("Starte Spiel | Gewähltes Level: Level " + chosenLevel, 118, 156);
        g.drawString("-> Levelauswahl", 304, 242);
        g.drawString("Einstellungen",340, 328);
        g.drawString("Credits/Quellen",320, 414);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() >= 104 && e.getX() <= 798 && e.getY() >= 104 && e.getY() <= 178){
            Main.stopMusic(); //evtl falls neuer song bei build mode
            Main.screenSelection = 1;
            setVisible(false);
            dispose();
        }else if(e.getX() >= 294 && e.getX() <= 598 && e.getY() >= 190 && e.getY() <= 264){
            new LevelAuswahl();
            setVisible(false);
            dispose();
        }else if(e.getX() >= 330 && e.getX() <= 598 && e.getY() >= 276 && e.getY() <= 350){
            new Einstellungen();
            setVisible(false);
            dispose();
        }else if(e.getX() >= 310 && e.getX() <= 598 && e.getY() >= 362 && e.getY() <= 436){
            new QuellenCredits();
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
