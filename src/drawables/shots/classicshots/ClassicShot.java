package src.drawables.shots.classicshots;

import src.LogicRepresentation;
import src.Main;
import src.drawables.shots.Shot;
import src.util.CoordsDouble;

import java.awt.*;

public abstract class ClassicShot extends Shot {
    protected Color color;
    protected int thickness;

    public ClassicShot(Color color, int thickness, double duration, CoordsDouble startingPosition, CoordsDouble targetPosition, float startingOpacity) {
        super(duration, startingPosition, targetPosition, startingOpacity);
        this.color = color;
        this.thickness = thickness;
    }

    @Override
    public void draw(Graphics graphics) {
        super.draw(graphics);

        Graphics2D g = (Graphics2D) graphics;
        g.setStroke(new BasicStroke(thickness));
        g.setColor(color);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, Math.clamp(opacity, 0.0f, 1.0f)));

        g.drawLine(startingPositionPixels.x(), startingPositionPixels.y(), targetPositionPixels.x(), targetPositionPixels.y());

        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    @Override
    public void tick(double timeDelta, LogicRepresentation logicRepresentation) {
        System.out.println(timeDelta);
        if (progress >= duration) {
            Main.unregisterDrawable(this);
            Main.unregisterTickable(this);
        }
        double opacityDifference = timeDelta / duration;
        opacity -= (float) opacityDifference;
        progress += timeDelta;
    }

    private float normalizedOpacity() {
        if (opacity > 1) {
            return 1.0f;
        }
        if (opacity < 0) {
            return 0.0f;
        }
        return opacity;
    }
}
