package project.Level;

import project.Coords;
import project.Objekte.Baubar.Basis.Basis;
import project.Objekte.Monster.Monster;

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
    protected Coords spawnPoint;
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
    public void setSpawnTime(long spawnTime) {
        this.spawnTime = spawnTime;
    }
    public void setMonstersToSpawn(List<Monster> monstersToSpawn) {
        this.monstersToSpawn = monstersToSpawn;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public void setBasisPosition(Coords basisPosition) {
        this.basisPosition = basisPosition;
    }
    public void setBasis(Basis basis) {
        this.basis = basis;
    }
    public void setSpawnPoint(Coords spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
    public void setStartKapital(double startKapital) {
        this.startKapital = startKapital;
    }
}
