package project.Graphikcontroller;

// Import anderer Klassen von außerhalb des Projekts
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EndbildschirmGewonnen extends JFrame {
    public EndbildschirmGewonnen(int x, int y){
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
        JLabel text = new JLabel("Du hast Gewonnen");
        add(text);
        setSize(200, 100);
        setLocation(x, y);
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
