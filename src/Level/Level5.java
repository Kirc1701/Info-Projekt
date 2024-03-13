package src.Level;

import src.Coords;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Monster.Boss1;
import src.Objekte.Monster.DefaultMonster;
import src.Objekte.Monster.Lakai;
import src.Objekte.Monster.Sprinter;

public class Level5 extends Level{
    public Level5(Basis basis){
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
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Sprinter(new Coords(-1, -1)));
        monstersToSpawn.add(new Sprinter(new Coords(-1, -1)));
        monstersToSpawn.add(new Sprinter(new Coords(-1, -1)));
        monstersToSpawn.add(new Sprinter(new Coords(-1, -1)));
        monstersToSpawn.add(new Sprinter(new Coords(-1, -1)));
        monstersToSpawn.add(new Boss1(new Coords(-1, -1)));
        basis.setHealth(basis.getMaxHealth());
        basis.setPosition(new Coords(20, 12));
    }
}
