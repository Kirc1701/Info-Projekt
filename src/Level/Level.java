package src.Level;

import org.javatuples.Pair;
import src.Coords;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Monster.Monster;

import java.util.ArrayList;
import java.util.List;

public abstract class Level {
    //Liste in der die zu spawnenden Monster gespeichert sind
    protected List<Monster> monstersToSpawn;
    //Double wert, der die Wartezeit zwischen dem Spawning von zwei Monstern angibt
    protected long spawnTime;
    protected int width;
    protected int height;
    protected Coords basisPosition;
    protected Basis basis;
    protected boolean spawnAtPoint;
    protected Coords spawnPoint;
    protected List<Pair<Coords, Coords>> spawnArea;
    protected double startKapital;

    //konstruktor
    public Level(long spawnTime, int width, int height, Coords basisPosition, Basis basis, Coords spawnPoint, double startKapital){
        this.width = width;
        this.height = height;
        this.basisPosition = basisPosition;
        this.basis = basis;
        this.spawnPoint = spawnPoint;
        this.startKapital = startKapital;
        monstersToSpawn = new ArrayList<>();
        this.spawnTime = spawnTime;
        spawnAtPoint = true;
    }
    public Level(long spawnTime, int width, int height, Coords basisPosition, Basis basis, List<Pair<Coords, Coords>> spawnArea, double startKapital){
        this.width = width;
        this.height = height;
        this.basisPosition = basisPosition;
        this.basis = basis;
        this.spawnArea = spawnArea;
        this.startKapital = startKapital;
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
    public Coords getBasisPosition() {
        return basisPosition;
    }
    public Basis getBasis() {
        return basis;
    }
    public Coords getSpawnPoint() {
        return spawnPoint;
    }
    public double getStartKapital() {
        return startKapital;
    }
    public void setBasis(Basis basis) {
        this.basis = basis;
    }
    public List<Pair<Coords, Coords>> getSpawnArea() {
        return spawnArea;
    }
    public void addSpawnArea(Pair<Coords, Coords> spawnArea) {
        this.spawnArea.add(spawnArea);
    }

    public boolean spawnAtPoint() {
        return spawnAtPoint;
    }
}