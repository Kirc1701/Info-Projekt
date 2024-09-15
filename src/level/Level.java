package src.level;

import lombok.Getter;
import lombok.Setter;
import org.javatuples.Pair;
import src.drawables.objects.buildings.basis.Basis;
import src.drawables.objects.monster.Monster;
import src.util.CoordsInt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Level implements Serializable {
    //Liste in der die zu spawnenden monster gespeichert sind
    @Getter
    protected final List<Monster> monstersToSpawn;
    //getter und setter Methoden
    //Double wert, der die Wartezeit zwischen dem Spawning von zwei Monstern angibt
    @Getter
    protected final long spawnTime;
    @Getter
    protected final int width;
    @Getter
    protected final int height;
    @Getter
    protected final CoordsInt basisPosition;
    @Setter
    @Getter
    protected Basis basis;
    protected final boolean spawnAtPoint;
    @Getter
    protected CoordsInt spawnPoint;
    @Getter
    protected List<Pair<CoordsInt, CoordsInt>> spawnArea;
    @Getter
    protected final double startKapital;
    @Getter
    protected int anzahlMauern;

    //konstruktor
    public Level(long spawnTime, int width, int height, CoordsInt basisPosition, Basis basis, CoordsInt spawnPoint, double startKapital, int anzahlMauern){
        this.width = width;
        this.height = height;
        this.basisPosition = basisPosition;
        this.basis = basis;
        this.spawnPoint = spawnPoint;
        this.startKapital = startKapital;
        this.anzahlMauern = anzahlMauern;
        monstersToSpawn = new ArrayList<>();
        this.spawnTime = spawnTime;
        spawnAtPoint = true;
    }
    public Level(long spawnTime, int width, int height, CoordsInt basisPosition, Basis basis, List<Pair<CoordsInt, CoordsInt>> spawnArea, double startKapital, int anzahlMauern){
        this.width = width;
        this.height = height;
        this.basisPosition = basisPosition;
        this.basis = basis;
        this.spawnArea = spawnArea;
        this.startKapital = startKapital;
        this.anzahlMauern = anzahlMauern;
        monstersToSpawn = new ArrayList<>();
        this.spawnTime = spawnTime;
        spawnAtPoint = false;
    }

    public void addSpawnArea(Pair<CoordsInt, CoordsInt> spawnArea) {
        this.spawnArea.add(spawnArea);
    }

    public boolean spawnAtPoint() {
        return spawnAtPoint;
    }

    public void setAnzahlMauern(int anzahlMauern) {
        this.anzahlMauern = anzahlMauern;
    }
}
