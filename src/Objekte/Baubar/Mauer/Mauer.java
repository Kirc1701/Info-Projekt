package src.Objekte.Baubar.Mauer;

import src.Coords;
import src.Objekte.Baubar.Baubar;

public abstract class Mauer extends Baubar {
    public Mauer(int health, Coords position, double kosten){
        super(0, health, position, 0, "Mauer", kosten);
    }
}
