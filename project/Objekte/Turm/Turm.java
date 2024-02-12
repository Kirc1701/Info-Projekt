package project.Objekte.Turm;

import project.Coords;
import project.Objekte.Monster.Monster;
import project.Objekte.Objekt;

public abstract class Turm extends Objekt {
    int reach;
    public Turm(int strength, int health, Coords position, int reach){
        super(strength, health, position, "Turm");
        this.reach = reach;
    }

    public abstract boolean shoot(Monster[] monsters);

    public int getReach() {
        return reach;
    }

    public void setReach(int reach) {
        this.reach = reach;
    }
}
