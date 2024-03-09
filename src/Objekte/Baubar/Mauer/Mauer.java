package src.Objekte.Baubar.Mauer;

import src.Coords;
import src.Objekte.Baubar.Baubar;

public abstract class Mauer extends Baubar {
    public Mauer(int health, Coords position, double kosten, String type){
        super(0, health, position, 0, type, kosten);
    }
}