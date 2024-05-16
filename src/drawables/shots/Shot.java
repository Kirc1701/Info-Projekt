package src.drawables.shots;

import src.Drawable;
import src.LogicRepresentation;
import src.Main;
import src.Tickable;
import src.util.CoordsDouble;
import src.util.CoordsInt;

import java.awt.*;

import static src.visuals.GameScreen.spaceBetweenLinesPixels;
import static src.visuals.GameScreen.titelbalkenSizePixels;

public abstract class Shot implements Drawable, Tickable {
    Color color;
    double duration;
    double startingDuration;
    CoordsInt startingPositionPixels;
    CoordsInt targetPositionPixels;
    float opacity;

    public Shot(Color color, double duration, CoordsDouble startingPosition, CoordsDouble targetPosition, float startingOpacity) {
        this.color = color;
        this.startingDuration = duration;
        this.duration = startingDuration;
        this.startingPositionPixels = adaptForPixels(startingPosition);
        this.targetPositionPixels = adaptForPixels(targetPosition);
        this.opacity = startingOpacity;
        Main.registerTickable(this);
        Main.registerDrawable(this, Main.getDrawables().indexOf(Main.basis));
    }

    private CoordsInt adaptForPixels(CoordsDouble coords) {
        return coords.scale(spaceBetweenLinesPixels).add(new CoordsDouble((double) spaceBetweenLinesPixels / 2, (double) spaceBetweenLinesPixels / 2 + titelbalkenSizePixels)).toCoordsInt();
    }

    @Override
    public void draw(Graphics graphics) {
        Drawable.super.draw(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setStroke(new BasicStroke(3));
        g.setColor(color);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, normalizedOpacity()));
        System.out.println(normalizedOpacity());
        g.drawLine(startingPositionPixels.x(), startingPositionPixels.y(), targetPositionPixels.x(), targetPositionPixels.y());
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
    }

    @Override
    public void tick(double timeDelta, LogicRepresentation logicRepresentation) {
        System.out.println(timeDelta);
        if (duration <= 0) {
            Main.unregisterDrawable(this);
            Main.unregisterTickable(this);
        }
        double opacityDifference = timeDelta / startingDuration;
        opacity -= (float) opacityDifference;
        duration -= timeDelta;
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

    @Override
    public float getOpacity() {
        return opacity;
    }
}
