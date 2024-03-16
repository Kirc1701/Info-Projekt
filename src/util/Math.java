package src.util;

import src.Direction;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Dictionary;

public class Math {
    public static Direction getDirectionDifference(CoordsDouble c1, CoordsDouble c2){
        double diffX = c2.x() - c1.x();
        double diffY = c2.y() - c1.y();
        if (diffX > 0) {
            return Direction.EAST;
        } else if (diffX < 0) {
            return Direction.WEST;
        } else if (diffY > 0) {
            return Direction.NORTH;
        } else if (diffY < 0) {
            return Direction.SOUTH;
        } else {
            return null;
        }
    }

    public static BufferedImage mirrorImage(Image image){
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(-1, 1));
        at.concatenate(AffineTransform.getTranslateInstance(-image.getWidth(null) ,0));
        return createTransformed(image, at);
    }

    /**
     * Transforms an <code>Image</code> according to an <code>AffineTransform</code>
     *
     * @param image     <code>Image</code> to be transformed
     * @param transform <code>AffineTransform</code> to be applied
     * @return <code>BufferedImage</code> of the transformed image
     */
    private static BufferedImage createTransformed(Image image, AffineTransform transform) {
        BufferedImage newImage = new BufferedImage(
                image.getWidth(null), image.getHeight(null),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = newImage.createGraphics();
        g.transform(transform);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

}
