package src.Objekte.Monster;

import src.Coords;
import src.Karte;

public class Sprinter extends Monster{
    public Sprinter(Coords position) {
        super(15, 10, position, 1, 4, 5, "Sprinter");
    }

    @Override
    public void updateMonsterPath(Karte karte){
        updateWalkingMonsterPath(karte);
    }
}
