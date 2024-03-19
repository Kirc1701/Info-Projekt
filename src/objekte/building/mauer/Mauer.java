package src.objekte.building.mauer;

import src.objekte.building.Building;
import src.util.CoordsInt;

public abstract class Mauer extends Building {
    public Mauer(int health, CoordsInt position, double kosten, String type){
        super(0, health, position, type, kosten);
    }
}
