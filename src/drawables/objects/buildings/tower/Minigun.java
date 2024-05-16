package src.drawables.objects.buildings.tower;

import src.LogicRepresentation;
import src.drawables.objects.monster.BombCarrier;
import src.drawables.objects.monster.Monster;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static src.visuals.GameScreen.spaceBetweenLinesPixels;

public class Minigun extends Tower {
    private static final Image image;

    static {
        try {
            image = ImageIO.read(Objects.requireNonNull(BombCarrier.class.getClassLoader().getResourceAsStream("images/SchnellschussTurm.png"))).getScaledInstance(spaceBetweenLinesPixels, spaceBetweenLinesPixels, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Minigun(CoordsInt position) {
        super(2, 25, position, 5, 2, 40, "Minigun");
    }

    @Override
    public Monster[] shoot(LogicRepresentation logicRepresentation) {
        return new Monster[]{shootNormal(logicRepresentation)};
    }


    @Override
    public Image getImage() {
        return image;
    }

    public static Image getStaticImage() {
        return image;
    }
}
