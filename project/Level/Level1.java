package project.Level;

import project.Coords;
import project.Objekte.Basis.Basis;
import project.Objekte.Basis.DefaultBasis;
import project.Objekte.Monster.DefaultMonster;

public class Level1 extends Level{
    public Level1(){
        super(10, 40, 25, new Coords(39, 12), new DefaultBasis(new Coords(39, 12)), new Coords(0, 12));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
    }
    public Level1(Basis basis){
        super(10, 40, 25, new Coords(39, 12), basis, new Coords(0, 12));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
    }
}
