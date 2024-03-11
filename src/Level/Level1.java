package src.Level;

import org.javatuples.Pair;
import src.Coords;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Baubar.Basis.DefaultBasis;
import src.Objekte.Monster.*;

import java.util.ArrayList;

public class Level1 extends Level{

    public Level1(Basis basis){
        super(
                1,
                40,
                25,
                new Coords(20, 12),
                basis,
                new ArrayList<>(),
                80,
                20
        );
        int height = 25;
        int width = 40;
        basis.setPosition(new Coords(20, 12));
        addSpawnArea(new Pair<>(new Coords(0, 0), new Coords(0, height - 1)));
        addSpawnArea(new Pair<>(new Coords(0, height - 1), new Coords(width - 1, height - 1)));
        addSpawnArea(new Pair<>(new Coords(0, 0), new Coords(width - 1, 0)));
        addSpawnArea(new Pair<>(new Coords(width - 1, 0), new Coords(width - 1, height - 1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
//        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
//        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
          monstersToSpawn.add(new Bombenschiff(new Coords(-1, -1)));
          monstersToSpawn.add(new Golem(new Coords(-1, -1)));
          monstersToSpawn.add(new Sprinter(new Coords(-1, -1)));
    }
}
