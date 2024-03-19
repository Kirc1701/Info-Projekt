package src.objekte.building.turm;

import src.Karte;
import src.objekte.monster.Bombenschiff;
import src.objekte.monster.Monster;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.*;

import static src.graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;

public class Schnellschussgeschuetz extends Turm{
    private static final Image image;
    static {
        try {
            image = ImageIO.read(Objects.requireNonNull(Bombenschiff.class.getClassLoader().getResourceAsStream("images/SchnellschussTurm.png"))).getScaledInstance(spaceBetweenLinesPixels, spaceBetweenLinesPixels, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Schnellschussgeschuetz(CoordsInt position){
        super(2, 25, position, 5, 2, 40, "Schnellschussgeschuetz");
    }

    @Override
    public Monster[] shoot(Karte karte) {
        return new Monster[]{shootNormal(karte)};
    }


    @Override
    public Image getImage() {
        return image;
    }

    public static Image getStaticImage() {
        return image;
    }}
