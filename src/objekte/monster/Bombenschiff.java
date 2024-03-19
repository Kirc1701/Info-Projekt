package src.objekte.monster;

import src.Direction;
import src.Karte;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static src.graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;
import static src.util.Math.mirrorImage;

public class Bombenschiff extends Monster{
    private static Image image;
    static {
        try {
            image = ImageIO.read(Objects.requireNonNull(Bombenschiff.class.getClassLoader().getResourceAsStream("images/Bombenschiff.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static final Map<Direction, Image> directionalImages = new HashMap<>();
    static {
        image = image.getScaledInstance(spaceBetweenLinesPixels, spaceBetweenLinesPixels, Image.SCALE_SMOOTH);
        directionalImages.put(Direction.EAST, image);
        directionalImages.put(Direction.WEST, mirrorImage(image));
        directionalImages.put(Direction.NORTH, image);
        directionalImages.put(Direction.SOUTH, image);
    }
    public Bombenschiff(CoordsInt position){
        super(30, 35, position, 4, 1, 40, "Bombenschiff");
    }

    @Override
    public void updateMonsterPath(Karte karte){
        updateFlyingMonsterPath(karte);
    }

    
    @Override
    public Image getImage() {
        return directionalImages.get(getDirection());
    }
}
