package src.drawables.shots.classicshots;

import src.util.CoordsDouble;

import java.awt.*;

public class DefaultShot extends ClassicShot {
    public DefaultShot(CoordsDouble startingPosition, CoordsDouble targetPosition) {
        super(Color.RED, 3, 0.5, startingPosition, targetPosition, 1);
    }
}
