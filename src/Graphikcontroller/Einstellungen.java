package src.Graphikcontroller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Einstellungen extends JFrame {
    public Einstellungen(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        new Hauptmenue();
                        setVisible(false);
                        dispose();
                    }
                }
        );
        setSize(450, 300);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 225,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150
        );
        setLayout(null);
        setVisible(true);
    }

    public static boolean musicmute = false;
    public static boolean soundmute = false;

    //Muss noch Button für die beiten bools adden lol
}
