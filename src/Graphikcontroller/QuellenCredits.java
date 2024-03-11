package src.Graphikcontroller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuellenCredits extends JFrame {
    public QuellenCredits(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        new Hauptmenue();
                        setVisible(false);
                        dispose();
                    }
                }
        );


        JLabel label1 = new JLabel("Lead Guitar: Anton Kirchert");
        add(label1);


        setSize(450, 300);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 225,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150
        );
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
