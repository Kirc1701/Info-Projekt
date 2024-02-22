package src.Objekte.Baubar.Turm;

import src.Coords;
import src.Objekte.Baubar.Baubar;
import src.Objekte.Monster.Monster;

import java.util.List;
import java.util.Map;

public abstract class Turm extends Baubar {
    int reach;
    public Turm(int strength, int health, Coords position, int reach, int speed, double kosten, String type){
        super(strength, health, position, speed, type, kosten);
        this.reach = reach;
    }



    public abstract Map<String, Integer> shoot(List<Monster> monsters);

//    public int getReach() {
//        return reach;
//    }

//    public void setReach(int reach) {
//        this.reach = reach;
//    }
}
