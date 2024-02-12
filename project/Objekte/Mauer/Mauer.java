package project.Objekte.Mauer;

import project.Coords;
import project.Objekte.Objekt;

public abstract class Mauer extends Objekt {
    public Mauer(int health, Coords position){
        super(0, health, position, 0, "Mauer");
    }
}
