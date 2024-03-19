package src.graphikcontroller;

import src.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Einstellungen extends JFrame {
    public static boolean musicMuted = false;
    public static boolean soundmute = false;
    public Einstellungen(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        if(Main.screenSelection == 0){
                            new MainMenu();
                        }else{
                            HauptgrafikSpiel.pressed[1] = false;
                        }
                        Main.source = false;
                        setVisible(false);
                        dispose();
                    }
                }
        );

        JButton mute = new JButton("Musik: an");
        if (musicMuted) {
            mute.setText("Musik: aus");
        }
        mute.addActionListener(actionEvent -> {
            musicMuted = !musicMuted;
            if (musicMuted) {
                Main.stopMusic();
                mute.setText("Musik: aus");
            } else {
                if (Main.source) {
                    Main.playMusic(0);
                } else Main.playMusic(3);
                mute.setText("Musik: an");
            }
        });
        JButton sound = new JButton("SFX: an");
        if (soundmute) {
            mute.setText("SFX: aus");
        }
        sound.addActionListener(actionEvent -> {
            soundmute = !soundmute;
            if (soundmute) {
                sound.setText("SFX: aus");
            } else {
                sound.setText("SFX: an");
            }
            Main.playSFX(1);
        });

        JButton menu = new JButton("Hauptmenü");
        menu.addActionListener(actionEvent -> {
            new MainMenu();
            setVisible(false);
            dispose();
        });


        add(mute);
        add(sound);

        add(menu);
        setSize(300, 300);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150
        );
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
