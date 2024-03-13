package src.Graphikcontroller;

import src.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static src.Main.gameHasStarted;
import static src.Main.screenSelection;

public class StarteSpielBildschirm extends JFrame {
    public StarteSpielBildschirm(int x){
        Main.playMusic(2);
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
            screenSelection = 2;
            Main.stopMusic();
            dispose();
        });
        add(button);
        setSize(100, 100);
        setLocation(x + 30, 0);
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
