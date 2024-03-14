package src.Graphikcontroller;

// Import anderer Klassen von außerhalb des Projekts
import src.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Das Fenster welches angezeigt wird, wenn die Monster es schaffen die Basis zu zerstören
public class EndbildschirmVerloren extends JFrame {
    // Konstruktor des Frames + Initialisierung
    // xCoordinateUpperLeftCorner: x-Koordinate der linken oberen Ecke
    // yCoordinateUpperLeftCorner: y-Koordinate der linken oberen Ecke
    public EndbildschirmVerloren(int x, int y){
        Main.stopMusic();
        Main.playSFX(6);
        // WindowListener, der bei Betätigung des Schließen-Buttons das Endfenster beendet
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        // Fenster wird nicht mehr dargestellt
                        setVisible(false);
                        // Alle weiteren Prozesse im Fenster werden unterbrochen
                        dispose();
                        // Das Programm wird mit dem Exit-code 0 beendet
                        System.exit(0);
                    }
                }
        );
        // Neues Label mit Text
        JLabel text = new JLabel("Du hast Verloren");
        text.setFont(new Font("font", Font.BOLD, 20));
        add(text);

        JButton mainMenu = new JButton("Hauptmenü");
        mainMenu.addActionListener(
                e -> {
                    Main.stopMusic();
                    new Hauptmenue();
                    setVisible(false);
                    dispose();
                }
        );
        add(mainMenu);

        // Initialisierung des Frames und Sichtbarkeit
        setBackground(Color.white);
        setSize(200, 100);
        setLocation(x, y);
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
