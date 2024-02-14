package project.Level;

import project.Coords;
import project.Objekte.Basis.Basis;
import project.Objekte.Basis.DefaultBasis;
import project.Objekte.Monster.DefaultMonster;

public class Level2 extends Level{
    public Level2(Basis basis){
        super(10, 10, 10, new Coords(9, 4), basis, new Coords(0, 4));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        basis.setHealth(basis.getMaxHealth());
        basis.setPosition(new Coords(9, 4));
    }
}
