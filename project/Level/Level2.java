package project.Level;

import project.Coords;
import project.Objekte.Baubar.Basis.Basis;
import project.Objekte.Monster.DefaultMonster;

public class Level2 extends Level{
    public Level2(Basis basis){
        super(3, 40, 20, new Coords(39, 4), basis, new Coords(0, 18), 80);
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
        basis.setHealth(basis.getMaxHealth());
        basis.setPosition(new Coords(9, 4));
    }
}
