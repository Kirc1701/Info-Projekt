package src.Level;

import org.javatuples.Pair;
import src.Coords;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Baubar.Basis.DefaultBasis;
import src.Objekte.Monster.DefaultMonster;
import src.Objekte.Monster.Lakai;

import java.util.ArrayList;

public class Level1 extends Level{
    public Level1(){
        super(
                3,
                40,
                25,
                new Coords(20, 12),
                new DefaultBasis(
                        new Coords(20, 12)
                ),
                new ArrayList<>(),
                80
        );
        addSpawnArea(new Pair<>(new Coords(0, 0), new Coords(0, 24)));
        addSpawnArea(new Pair<>(new Coords(0, 24), new Coords(39, 24)));
        addSpawnArea(new Pair<>(new Coords(0, 0), new Coords(39, 0)));
        addSpawnArea(new Pair<>(new Coords(39, 0), new Coords(39, 24)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
        monstersToSpawn.add(new Lakai(new Coords(-1, -1)));
    }
    public Level1(Basis basis){
        super(5, 40, 25, new Coords(39, 1), basis, new Coords(0, 24), 80);
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new Coords(-1, -1)));
    }
}
