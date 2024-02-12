package project.Graphikcontroller;

import project.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static project.Main.gameHasStarted;
import static project.Main.karte;

public class Startbildschirm extends JFrame {
    public Startbildschirm(){
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
            Main.aktuelleGrafik = new HauptgrafikSpiel(karte);
            gameHasStarted = true;
            dispose();
        });
        add(button);
        setSize(100, 100);
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
