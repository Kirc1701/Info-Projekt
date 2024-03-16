package src.Objekte.Baubar.Mauer;

import src.Objekte.Monster.Bombenschiff;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static src.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;

public class DefaultMauer extends Mauer{
    private static final Image image;
    static {
        try {
            image = ImageIO.read(Objects.requireNonNull(Bombenschiff.class.getClassLoader().getResourceAsStream("images/Mauer.png"))).getScaledInstance(spaceBetweenLinesPixels, spaceBetweenLinesPixels, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public DefaultMauer(CoordsInt position) {
        super(10, position, 0, "DefaultMauer");
    }


    @Override
    public Image getImage() {
        return image;
    }

    public static Image getStaticImage() {
        return image;
    }}
