package src.Graphikcontroller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static src.Main.gameHasStarted;
import static src.Main.screenSelection;

public class StarteSpielBildschirm extends JFrame {
    public StarteSpielBildschirm(){
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
            dispose();
        });
        add(button);
        setSize(100, 100);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width - 100, 0);
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
