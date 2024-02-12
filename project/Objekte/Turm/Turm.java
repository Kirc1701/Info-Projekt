package project.Objekte.Turm;

import project.Coords;
import project.Objekte.Monster.Monster;
import project.Objekte.Objekt;

import java.awt.*;
import java.util.List;
import java.util.Map;

public abstract class Turm extends Objekt {
    int reach;
    public Turm(int strength, int health, Coords position, int reach, long speed){
        super(strength, health, position, speed, "Turm");
        this.reach = reach;
    }



    public abstract Map<String, Integer> shoot(List<Monster> monsters);

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }
}
