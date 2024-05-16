package src.visuals;

import src.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class StartGamePopUp extends JFrame {
    public StartGamePopUp(int x) {
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );
        //Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setBackground(Color.white);
        JButton button = new JButton("Start");
        button.addActionListener(e -> {
            setVisible(false);
            Main.screenSelection = 2;
            Main.stopMusic();
            Main.playMusic(0);
            dispose();
        });
        add(button);
        setSize(100, 100);
        setLocation(x + 30, 0);
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
