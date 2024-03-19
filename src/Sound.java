package src;

import src.util.SoundUtils;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;


public class Sound {
    static Clip clip;
    final URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sounds/soundtrack.wav");
        soundURL[1] = getClass().getResource("/sounds/bloop-1.wav");
        soundURL[2] = getClass().getResource("/sounds/anticipation.wav");
        soundURL[3] = getClass().getResource("/sounds/calmpiano.wav");
        soundURL[4] = getClass().getResource("/sounds/happy-pop.wav");
        soundURL[5] = getClass().getResource("/sounds/boss.wav");
        soundURL[6] = getClass().getResource("/sounds/boom.wav");
        soundURL[7] = getClass().getResource("/sounds/win.wav");
        soundURL[8] = getClass().getResource("/sounds/placed.wav");
        soundURL[9] = getClass().getResource("/sounds/bosstot.wav");
        soundURL[10] = getClass().getResource("/sounds/basedamage.wav");
        Sound.setVolume(0.5f); // Set the volume to 50%
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ignored) {}
    }

    public void play() {
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public float getVolume() {
        if (clip == null) {
            return 0.0f; // Return 0 if clip is not initialized
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public static void setVolume(float volume) {
        SoundUtils.setVolume(volume, clip);
    }
}
