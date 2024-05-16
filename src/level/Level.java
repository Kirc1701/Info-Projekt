package src.level;

import org.javatuples.Pair;
import src.drawables.objects.buildings.basis.Basis;
import src.drawables.objects.monster.Monster;
import src.util.CoordsInt;

import java.util.ArrayList;
import java.util.List;

public abstract class Level {
    //Liste in der die zu spawnenden monster gespeichert sind
    protected final List<Monster> monstersToSpawn;
    //Double wert, der die Wartezeit zwischen dem Spawning von zwei Monstern angibt
    protected final long spawnTime;
    protected final int width;
    protected final int height;
    protected final CoordsInt basisPosition;
    protected Basis basis;
    protected final boolean spawnAtPoint;
    protected CoordsInt spawnPoint;
    protected List<Pair<CoordsInt, CoordsInt>> spawnArea;
    protected final double startKapital;
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

    //getter und setter Methoden
    public long getSpawnTime() {
        return spawnTime;
    }
    public List<Monster> getMonstersToSpawn() {
        return monstersToSpawn;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public CoordsInt getBasisPosition() {
        return basisPosition;
    }
    public Basis getBasis() {
        return basis;
    }
    public CoordsInt getSpawnPoint() {
        return spawnPoint;
    }
    public double getStartKapital() {
        return startKapital;
    }
    public void setBasis(Basis basis) {
        this.basis = basis;
    }
    public List<Pair<CoordsInt, CoordsInt>> getSpawnArea() {
        return spawnArea;
    }
    public void addSpawnArea(Pair<CoordsInt, CoordsInt> spawnArea) {
        this.spawnArea.add(spawnArea);
    }

    public boolean spawnAtPoint() {
        return spawnAtPoint;
    }

    public int getAnzahlMauern() {
        return anzahlMauern;
    }

    public void setAnzahlMauern(int anzahlMauern) {
        this.anzahlMauern = anzahlMauern;
    }
}
