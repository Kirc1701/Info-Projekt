package project.Level;

import project.Coords;
import project.Objekte.Monster.DefaultMonster;

public class Level1 extends Level{
    public Level1(){
        super(10);
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
    }
}
