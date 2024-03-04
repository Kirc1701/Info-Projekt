package src.Graphikcontroller;

import src.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Hauptmenü extends JFrame {
    public static int chosenLevel = 1;
    public Hauptmenü(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );
        JButton startGame = new JButton("Starte Spiel | Gewähltes Level: Level " + chosenLevel);
        startGame.addActionListener(
                e -> {
                    Main.screenSelection = 1;
                    setVisible(false);
                    dispose();
                }
        );
        JButton levelSelect = new JButton("-> Levelauswahl");
        levelSelect.addActionListener(
                e -> {
                    new LevelAuswahl();
                    setVisible(false);
                    dispose();
                }
        );
        add(startGame);
        add(levelSelect);
        setBackground(Color.white);
        setSize(300, 300);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150
        );
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
