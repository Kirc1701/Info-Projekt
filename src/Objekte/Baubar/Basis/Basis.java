package src.Objekte.Baubar.Basis;

import src.Coords;
import src.Objekte.Baubar.Baubar;

public abstract class Basis extends Baubar {
    public Basis(int strength, int health, Coords position){
        super(strength, health, position, 0, "Basis", 0);
    }
}
