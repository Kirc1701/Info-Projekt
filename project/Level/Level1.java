package project.Level;

import project.Coords;
import project.Objekte.Monster.DefaultMonster;

public class Level1 extends Level{
    public Level1(){
        super(20);
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
    }
}
