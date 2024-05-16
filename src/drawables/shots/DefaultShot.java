package src.drawables.shots;

import src.util.CoordsDouble;

import java.awt.*;

public class DefaultShot extends Shot {
    public DefaultShot(CoordsDouble startingPosition, CoordsDouble targetPosition) {
        super(Color.RED, 0.5, startingPosition, targetPosition, 1);
    }
}
