package src.Level;

import src.Coords;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Monster.Bombenschiff;
import src.Objekte.Monster.DefaultMonster;
import src.Objekte.Monster.Lakai;

public class Level4 extends Level{
    public Level4(Basis basis){
        super(
                3,
                40,
                20,
                new Coords(39, 4),
                basis,
                new Coords(0, 18),
                140,
                30
        );
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Bombenschiff(new Coords(-1, -1)));
        monstersToSpawn.add(new Bombenschiff(new Coords(-1, -1)));
        basis.setPosition(new Coords(9, 4));
    }
}
