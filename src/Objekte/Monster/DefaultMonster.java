package src.Objekte.Monster;

import src.Coords;
import src.Karte;

public class DefaultMonster extends Monster{
    public DefaultMonster(Coords position) {
        super(10, 20, position, 2, 4, 8, "Default");
    }

    @Override
    public void updateMonsterPath(Karte karte){
        updateWalkingMonsterPath(karte);
    }
}
