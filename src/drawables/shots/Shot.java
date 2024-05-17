package src.drawables.shots;

import src.Drawable;
import src.Main;
import src.Tickable;
import src.util.CoordsDouble;
import src.util.CoordsInt;

import static src.visuals.GameScreen.spaceBetweenLinesPixels;
import static src.visuals.GameScreen.titelbalkenSizePixels;

public abstract class Shot implements Drawable, Tickable {
    protected double progress;
    protected double duration;
    protected CoordsInt startingPositionPixels;
    protected CoordsInt targetPositionPixels;
    protected float opacity;

    public Shot(double duration, CoordsDouble startingPosition, CoordsDouble targetPosition, float startingOpacity) {
        this.duration = duration;
        this.progress = 0;
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
    public float getOpacity() {
        return opacity;
    }
}
