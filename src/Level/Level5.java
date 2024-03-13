package src.Level;

import org.javatuples.Pair;
import src.Coords;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Monster.Boss1;
import src.Objekte.Monster.DefaultMonster;
import src.Objekte.Monster.Lakai;
import src.Objekte.Monster.Sprinter;

import java.util.ArrayList;

public class Level5 extends Level{
    public Level5(Basis basis){
        super(
                3,
                39,
                21,
                new Coords(19, 10),
                basis,
                new ArrayList<>(),
                80,
                30
        );
        addSpawnArea(new Pair<>(new Coords(0, 0), new Coords(0, height - 1)));
        addSpawnArea(new Pair<>(new Coords(0, height - 1), new Coords(width - 1, height - 1)));
        addSpawnArea(new Pair<>(new Coords(0, 0), new Coords(width - 1, 0)));
        addSpawnArea(new Pair<>(new Coords(width - 1, 0), new Coords(width - 1, height - 1)));
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
        basis.setPosition(new Coords(19, 10));
    }
}
