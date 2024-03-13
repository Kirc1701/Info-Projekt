package src.Objekte.Monster;

import src.Coords;
import src.Karte;

public class Boss1 extends Monster{
    public Boss1(Coords position) {
        super(30, 140, position, 3, 3, 100, "Boss1");
    }

    @Override
    public void updateMonsterPath(Karte karte){
        updateWalkingMonsterPath(karte);
    }
}
