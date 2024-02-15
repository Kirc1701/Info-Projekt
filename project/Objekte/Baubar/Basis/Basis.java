package project.Objekte.Baubar.Basis;

import project.Coords;
import project.Objekte.Baubar.Baubar;

public abstract class Basis extends Baubar {
    public Basis(int strength, int health, Coords position){
        super(strength, health, position, 0, "Basis", 0);
    }
}
