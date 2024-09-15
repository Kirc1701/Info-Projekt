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
public class Lakai extends Monster{
    private static Image image;
    static {
        try {
            image = mirrorImage(ImageIO.read(Objects.requireNonNull(BombCarrier.class.getClassLoader().getResourceAsStream("images/Lakai.png"))));
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
    public Lakai(CoordsInt position){
        super(10, 20, position, 4, 3, 20, ObjectType.Lakai, true);
    }

    @Override
    public void updateMonsterPath(LogicRepresentation logicRepresentation) {
        updateFlyingMonsterPath(logicRepresentation);
    }

    
    @Override
    public Image getImage() {
        return directionalImages.get(getDirection());
    }
}
