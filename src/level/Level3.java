package src.level;

import org.javatuples.Pair;
import src.drawables.objects.buildings.basis.Basis;
import src.drawables.objects.monster.DefaultMonster;
import src.drawables.objects.monster.Golem;
import src.drawables.objects.monster.Lakai;
import src.drawables.objects.monster.Runner;
import src.util.CoordsInt;

import java.util.ArrayList;

public class Level3 extends Level{
    public Level3(Basis basis){
        super(
                3,
                39,
                21,
                new CoordsInt(19, 9),
                basis,
                new ArrayList<>(),
                80,
                30
        );
        addSpawnArea(new Pair<>(new CoordsInt(0, 0), new CoordsInt(0, height - 1)));
        addSpawnArea(new Pair<>(new CoordsInt(0, height - 1), new CoordsInt(width - 1, height - 1)));
        addSpawnArea(new Pair<>(new CoordsInt(0, 0), new CoordsInt(width - 1, 0)));
        addSpawnArea(new Pair<>(new CoordsInt(width - 1, 0), new CoordsInt(width - 1, height - 1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new DefaultMonster(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Golem(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Golem(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Lakai(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Lakai(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Lakai(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        monstersToSpawn.add(new Runner(new CoordsInt(-1, -1)));
        basis.setHealth(basis.getMaxHealth());
        basis.setPosition(new CoordsInt(19, 9));
    }
}
