package src;

import src.util.CoordsDouble;

import java.awt.*;

public interface Drawable {
    Image getImage();

    CoordsDouble getDrawnPosition();
    float getOpacity();
    default void draw(Graphics g){
    }
}
