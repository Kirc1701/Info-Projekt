package src.graphikcontroller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static src.Main.loadDesign;

public class Load extends JFrame {
    public Load(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        HauptgrafikSpiel.pressed[1] = false;
                        dispose();
                    }
                }
        );
        //Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setBackground(Color.white);
        JTextField textField = new JTextField("", 8);
        JButton acceptText = new JButton("Load level");
        acceptText.addActionListener(
                e -> {
                    loadDesign = textField.getText();
                    setVisible(false);
                    HauptgrafikSpiel.pressed[1] = false;
                    dispose();
                }
        );
        add(textField);
        add(acceptText);
        setSize(200, 200);
        setLocation(0, 0);
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
