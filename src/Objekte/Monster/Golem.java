package src.Objekte.Monster;

import src.Coords;
import src.Karte;

public class Golem extends Monster{
    public Golem(Coords position) {
        super(20, 60, position, 4, 1, 20, "Golem");
    }

    @Override
    public void updateMonsterPath(Karte karte){
        updateWalkingMonsterPath(karte);
    }
}
