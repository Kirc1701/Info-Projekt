package src.Level;

import src.Coords;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Monster.DefaultMonster;
import src.Objekte.Monster.Lakai;

public class Level2 extends Level{
    public Level2(Basis basis){
        super(
                3,
                40,
                20,
                new Coords(39, 4),
                basis,
                new Coords(0, 18),
                80,
                30
        );
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        basis.setPosition(new Coords(9, 4));
    }
}
