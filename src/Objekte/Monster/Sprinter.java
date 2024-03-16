package src.Objekte.Monster;

import src.Direction;
import src.Karte;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static src.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;
import static src.util.Math.mirrorImage;

public class Sprinter extends Monster{
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
    public Sprinter(CoordsInt position) {
        super(15, 10, position, 1, 4, 5, "Sprinter");
    }

    @Override
    public void updateMonsterPath(Karte karte){
        updateWalkingMonsterPath(karte);
    }

    
    @Override
    public Image getImage() {
        return directionalImages.get(getDirection());
    }
}
