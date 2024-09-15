package src.util;

import src.Main;
import src.visuals.Settings;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundUtils {
    public static void setVolume(float volume, Clip clip) {
        if (clip == null) {
            return; // Exit if clip is not initialized
        }
        if (volume < 0f || volume > 1f) {
            throw new IllegalArgumentException("Volume not valid: " + volume);
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(20f * (float) java.lang.Math.log10(volume));
    }

    public static void playMusic(int i) {
        if (!Settings.musicMuted) {
            Main.sound.setFile(i);
            Main.sound.play();
            Main.sound.loop();
        }
    }

    public static void stopMusic() {
        try {Main.sound.stop();}
        catch(Exception e) {System.out.println("Nope");}
    }

    public static void playSFX(int i) {
        if (!Settings.soundmute) {
            Main.sfx.setFile(i);
            Main.sfx.play();
        }
    }
}
