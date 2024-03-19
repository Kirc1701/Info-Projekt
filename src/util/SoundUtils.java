package src.util;

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
}
