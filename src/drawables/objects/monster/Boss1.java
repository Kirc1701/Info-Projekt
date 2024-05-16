package src.drawables.objects.monster;

import src.Direction;
import src.LogicRepresentation;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static src.visuals.GameScreen.spaceBetweenLinesPixels;
import static src.util.Math.mirrorImage;

public class Boss1 extends Monster{
    private static Image image;
    static {
        try {
            image = ImageIO.read(Objects.requireNonNull(BombCarrier.class.getClassLoader().getResourceAsStream("images/Boss.png")));
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
    public Boss1(CoordsInt position) {
        super(30, 140, position, 1.5f, 3, 100, "Boss1", false);
    }

    @Override
    public void updateMonsterPath(LogicRepresentation logicRepresentation) {
        updateWalkingMonsterPath(logicRepresentation);
    }

    
    @Override
    public Image getImage() {
        return directionalImages.get(getDirection());
    }
}
