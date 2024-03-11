package src.Objekte.Monster;

import src.Coords;
import src.Karte;

public class Bombenschiff extends Monster{
    public Bombenschiff(Coords position){
        super(30, 35, position, 4, 1, 40, "Bombenschiff");
    }

    @Override
    public void updateMonsterPath(Karte karte){
        updateFlyingMonsterPath(karte);
    }
}
