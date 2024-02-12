package project.Objekte.Basis;

import project.Coords;
import project.Objekte.Objekt;

public abstract class Basis extends Objekt {
    public Basis(int strength, int health, Coords position){
        super(strength, health, position, 0, "Basis");
    }
}
