package src.visuals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import static src.LoopType.game_loop_started;
import static src.Main.loop;
import static src.util.SoundUtils.playMusic;
import static src.util.SoundUtils.stopMusic;


public class StartGamePopUp extends JFrame {
    public StartGamePopUp(int x) {
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        try (FileOutputStream fileOutputStream = new FileOutputStream("Save.txt");
                             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                            objectOutputStream.writeObject(loop.getLogic_representation());
                            objectOutputStream.flush();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        dispose();
                        System.exit(0);
                    }
                }
        );
        //Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setBackground(Color.white);
        JButton button = new JButton("Start");
        button.addActionListener(_ -> {
            setVisible(false);
            loop.update(game_loop_started);
            stopMusic();
            playMusic(0);
            dispose();
        });
        add(button);
        setSize(100, 100);
        setLocation(x + 30, 0);
        setLayout(new FlowLayout());
        setVisible(true);
    }
}
