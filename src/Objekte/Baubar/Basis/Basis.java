package src.Objekte.Baubar.Basis;

import src.Coords;
import src.Objekte.Baubar.Baubar;

public abstract class Basis extends Baubar {
    public Basis(int strength, int health, Coords position, String type){
        super(strength, health, position, 0, type, 0);
    }
}
