package src.drawables.objects.monster;

import lombok.NoArgsConstructor;
import src.Direction;
import src.LogicRepresentation;
import src.drawables.objects.ObjectType;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static src.util.Math.mirrorImage;
import static src.visuals.GameScreen.spaceBetweenLinesPixels;

@NoArgsConstructor
public class DefaultMonster extends Monster{
    private static Image image;
    static {
        try {
            image = ImageIO.read(Objects.requireNonNull(BombCarrier.class.getClassLoader().getResourceAsStream("images/DefaultMonster.png")));
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
    public DefaultMonster(CoordsInt position) {
        super(10, 20, position, 2, 4, 8, ObjectType.Default, false);
    }

    @Override
    public void updateMonsterPath(LogicRepresentation logicRepresentation) {
        updateWalkingMonsterPath(logicRepresentation);
    }

    public static Image getStaticImage() {
        return directionalImages.get(null);
    }
    @Override
    public Image getImage() {
        return directionalImages.get(getDirection());
    }
}
