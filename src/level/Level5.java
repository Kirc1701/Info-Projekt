package src.level;

import org.javatuples.Pair;
import src.drawables.objects.buildings.basis.Basis;
import src.drawables.objects.monster.Boss1;
import src.drawables.objects.monster.Lakai;
import src.drawables.objects.monster.Runner;
import src.util.CoordsInt;

import java.util.ArrayList;

public class Level5 extends Level{
    public Level5(Basis basis){
        super(
                3,
                39,
                21,
                new CoordsInt(19, 10),
                basis,
                new ArrayList<>(),
                80,
                30
        );
        addSpawnArea(new Pair<>(new CoordsInt(0, 0), new CoordsInt(0, height - 1)));
        addSpawnArea(new Pair<>(new CoordsInt(0, height - 1), new CoordsInt(width - 1, height - 1)));
        addSpawnArea(new Pair<>(new CoordsInt(0, 0), new CoordsInt(width - 1, 0)));
        addSpawnArea(new Pair<>(new CoordsInt(width - 1, 0), new CoordsInt(width - 1, height - 1)));
        monstersToSpawn.add(new Lakai(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Lakai(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Lakai(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Lakai(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Boss1(new CoordsInt(-1, -1)));
        basis.setHealth(basis.getMaxHealth());
        basis.setPosition(new CoordsInt(19, 10));
    }
}
