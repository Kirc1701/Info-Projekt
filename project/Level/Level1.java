package project.Level;

import project.Coords;
import project.Objekte.Baubar.Basis.Basis;
import project.Objekte.Baubar.Basis.DefaultBasis;
import project.Objekte.Monster.DefaultMonster;

public class Level1 extends Level{
    public Level1(){
        super(5, 40, 25, new Coords(39, 1), new DefaultBasis(new Coords(39, 1)), new Coords(0, 24), 80);
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
    }
    public Level1(Basis basis){
        super(5, 40, 25, new Coords(39, 1), basis, new Coords(0, 24), 80);
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1), 0));

    }
}
