package src.Objekte.Monster;

import src.Coords;
import src.Karte;

public class Lakai extends Monster{
    public Lakai(Coords position){
        super(10, 20, position, 1, 3, 20, "Lakai");
    }

    @Override
    public void updateMonsterPath(Karte karte){
        updateFlyingMonsterPath(karte);
    }
}
