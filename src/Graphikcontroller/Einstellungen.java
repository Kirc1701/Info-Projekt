package src.Graphikcontroller;

import src.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Einstellungen extends JFrame {
    public static boolean musicmute = false;
    public static boolean soundmute = false;
    public Einstellungen(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        new Hauptmenue();
                        setVisible(false);
                        dispose();
                    }
                }
        );

        JButton mute = new JButton("Musik: an");
        if (musicmute) {
            mute.setText("Musik: aus");
        }
        mute.addActionListener(actionEvent -> {
            musicmute = !musicmute;
            if (musicmute) {
                Main.stopMusic();
                mute.setText("Musik: aus");
            } else {
                Main.playMusic(0);
                mute.setText("Musik: an");
            }
        });
        JButton sound = new JButton("SFX: an");
        sound.addActionListener(actionEvent -> {
            soundmute = !soundmute;
            if (soundmute) {
                sound.setText("SFX: aus");
            } else {
                sound.setText("SFX: an");
            }
            Main.playSFX(1);
        });

        add(mute);
        add(sound);

        setSize(450, 300);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 225,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150
        );
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
