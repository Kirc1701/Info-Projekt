package src;

import java.awt.*;

public interface Drawable {
    float getOpacity();
    default void draw(Graphics g){
    }
}
