package src;

import src.util.CoordsDouble;

import java.awt.*;

public interface Drawable {
    public Image getImage();

    public CoordsDouble getDrawnPosition();
    public float getOpacity();
    public default void draw(Graphics g){
        return;
    }
}
