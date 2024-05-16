package src.visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class QuellenCredits extends JFrame {
    public QuellenCredits(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        new MainMenu();
                        setVisible(false);
                        dispose();
                    }
                }
        );

        add(new JLabel("    Sound Design"));
        add(new JLabel("Anton K."));

        add(new JLabel("    Design Artist"));
        add(new JLabel("Helena M."));

        add(new JLabel("    External Design Artist"));
        add(new JLabel("Maya R."));

        add(new JLabel("    level Design/Balancing"));
        add(new JLabel("Marius P."));

        add(new JLabel("    Lead Technical Development"));
        add(new JLabel("Samuel S."));

        add(new JLabel("    Hintergrundbild level-Auswahl"));
        JLabel levelAuswahl = new JLabel("dreamstime.com");
        levelAuswahl.setForeground(Color.BLUE.darker());
        levelAuswahl.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        levelAuswahl.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://shorturl.at/iFKO9"));
                } catch (IOException | URISyntaxException ignored) {}
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                levelAuswahl.setText("<html><a href=''>dreamstime.com</a></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                levelAuswahl.setText("dreamstime.com");
            }
        });
        add(levelAuswahl);

        add(new JLabel("    Hintergrundbild level 1/2/3/4"));
        JLabel level1234 = new JLabel("stock.adobe.com");
        level1234.setForeground(Color.BLUE.darker());
        level1234.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        level1234.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://shorturl.at/bluvU"));
                } catch (IOException | URISyntaxException ignored) {}
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                level1234.setText("<html><a href=''>stock.adobe.com</a></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                level1234.setText("stock.adobe.com");
            }
        });
        add(level1234);

        add(new JLabel("    Hintergrundbild level 5"));
        JLabel level1 = new JLabel("stock.adobe.com");
        level1.setForeground(Color.BLUE.darker());
        level1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        level1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://shorturl.at/enBTV"));
                } catch (IOException | URISyntaxException ignored) {}
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                level1.setText("<html><a href=''>stock.adobe.com</a></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                level1.setText("stock.adobe.com");
            }
        });
        add(level1);

        add(new JLabel("    Hintergrundbild Hauptmenü"));
        JLabel mainMenu = new JLabel("bigstockphoto.com");
        mainMenu.setForeground(Color.BLUE.darker());
        mainMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://shorturl.at/crtQU"));
                } catch (IOException | URISyntaxException ignored) {}
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                mainMenu.setText("<html><a href=''>bigstockphoto.com</a></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                mainMenu.setText("bigstockphoto.com");
            }
        });
        add(mainMenu);

        add(new JLabel("    SFX"));
        JLabel sfx1 = new JLabel("pixabay.com");
        sfx1.setForeground(Color.BLUE.darker());
        sfx1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sfx1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://pixabay.com/"));
                } catch (IOException | URISyntaxException ignored) {}
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                sfx1.setText("<html><a href=''>pixabay.com</a></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                sfx1.setText("pixabay.com");
            }
        });
        add(sfx1);

        add(new JLabel("    SFX"));
        JLabel sfx = new JLabel("opengameart.org");
        sfx.setForeground(Color.BLUE.darker());
        sfx.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        sfx.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://opengameart.org/"));
                } catch (IOException | URISyntaxException ignored) {}
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                sfx.setText("<html><a href=''>opengameart.org</a></html>");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                sfx.setText("opengameart.org");
            }
        });
        add(sfx);


        setSize(550, 350);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 275,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 175
        );
        setLayout(new GridLayout(0, 2));
        setVisible(true);
    }
}
