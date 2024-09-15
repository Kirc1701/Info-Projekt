package src.visuals;

import src.LoopType;
import src.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static src.Main.loop;
import static src.util.SoundUtils.*;

public class Settings extends JFrame {
    public static boolean musicMuted = false;
    public static boolean soundmute = false;

    public Settings(boolean opened_during_game, boolean game_started) {
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        if(opened_during_game){
                            if(game_started) loop.update(LoopType.game_loop_started);
                            else loop.update(LoopType.forward);
                            setVisible(false);
                            dispose();
                        }else{
                            loop.update(LoopType.main_menu);
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
        mute.addActionListener(_ -> {
            musicMuted = !musicMuted;
            if (musicMuted) {
                stopMusic();
                mute.setText("Musik: aus");
            } else {
                if (Main.source) {
                    playMusic(0);
                } else playMusic(3);
                mute.setText("Musik: an");
            }
        });

        JButton sound = new JButton("SFX: an");
        if (soundmute) {
            mute.setText("SFX: aus");
        }
        sound.addActionListener(_ -> {
            soundmute = !soundmute;
            if (soundmute) {
                sound.setText("SFX: aus");
            } else {
                sound.setText("SFX: an");
            }
            playSFX(1);
        });

        JButton menu = new JButton("Hauptmenü");
        menu.addActionListener(_ -> {
            loop.update(LoopType.main_menu_during_game);
            setVisible(false);
            dispose();
        });
        JButton keep_playing = new JButton();
        if(opened_during_game) {
            keep_playing = new JButton("Weiter");
            keep_playing.addActionListener(_ -> {
                if(game_started) loop.update(LoopType.game_loop_started);
                else loop.update(LoopType.forward);
                setVisible(false);
                dispose();
            });
        }

        add(mute);
        add(sound);

        add(menu);
        if(opened_during_game) add(keep_playing);
        setSize(300, 300);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 150,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 150
        );
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
