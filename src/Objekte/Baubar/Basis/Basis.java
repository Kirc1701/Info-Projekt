package src.Objekte.Baubar.Basis;

import src.Objekte.Baubar.Building;
import src.util.CoordsInt;

public abstract class Basis extends Building {
    public Basis(int strength, int health, CoordsInt position, String type){
        super(strength, health, position, type, 0);
    }

    @Override
    public void die() {
    }
}
