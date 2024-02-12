package project.Level;

import project.Coords;
import project.Objekte.Monster.DefaultMonster;
import project.Objekte.Monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class Level1 extends Level{
    public Level1(){
        super(10);
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
    }
}
