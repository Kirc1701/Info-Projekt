package src;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/soundtrack.wav");
        soundURL[1] = getClass().getResource("/sounds/bloop-1.wav");
        soundURL[2] = getClass().getResource("/sounds/anticipation.wav");
        soundURL[3] = getClass().getResource("/sounds/calmpiano.wav");
        soundURL[4] = getClass().getResource("/sounds/happy-pop.wav");
        soundURL[5] = getClass().getResource("/sounds/boss.wav");
        soundURL[6] = getClass().getResource("/sounds/boom.wav");

    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        }catch(Exception e) {

        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop () {
        clip.stop();
    }
}
