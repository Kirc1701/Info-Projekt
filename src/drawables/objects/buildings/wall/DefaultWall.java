package src.drawables.objects.buildings.wall;

import lombok.NoArgsConstructor;
import src.drawables.objects.ObjectType;
import src.drawables.objects.monster.BombCarrier;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static src.visuals.GameScreen.spaceBetweenLinesPixels;

@NoArgsConstructor
public class DefaultWall extends Wall {
    private static final Image image;

    static {
        try {
            image = ImageIO.read(Objects.requireNonNull(BombCarrier.class.getClassLoader().getResourceAsStream("images/Mauer.png"))).getScaledInstance(spaceBetweenLinesPixels, spaceBetweenLinesPixels, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DefaultWall(CoordsInt position) {
        super(10, position, 0, ObjectType.DefaultWall);
    }


    @Override
    public Image getImage() {
        return image;
    }

    public static Image getStaticImage() {
        return image;
    }
}
