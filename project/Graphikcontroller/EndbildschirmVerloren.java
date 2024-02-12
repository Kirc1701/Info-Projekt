package project.Graphikcontroller;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EndbildschirmVerloren extends JFrame {
    public EndbildschirmVerloren(int x, int y){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );
        setBackground(Color.white);
        setFont(new Font("font", Font.BOLD, 20));
        JLabel text = new JLabel("Du hast Verloren");
        add(text);
        setSize(200, 100);
        setLocation(x, y);
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
