package src;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;


public class SFX {
    static Clip clip;
    URL[] soundURL = new URL[30];

    public SFX() {
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
        SFX.setVolume(0.5f); // Set the volume to 50%
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (clip == null) {
            return; // Exit if clip is not initialized
        }
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) Math.log10(volume));
    }
}
