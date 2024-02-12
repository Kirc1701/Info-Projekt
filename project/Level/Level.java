package project.Level;

import project.Objekte.Monster.Monster;

import java.util.ArrayList;
import java.util.List;

public abstract class Level {
    //Liste in der die zu spawnenden Monster gespeichert sind
    protected List<Monster> monstersToSpawn;
    //Double wert, der die Wartezeit zwischen dem Spawning von zwei Monstern angibt
    protected long spawntime;
    //konstruktor
    public Level(long pSpawntime){
        monstersToSpawn = new ArrayList<>();
        spawntime = pSpawntime;
    }

    //getter und setter Methoden
    public long getSpawntime() {
        return spawntime;
    }
    public List<Monster> getMonstersToSpawn() {
        return monstersToSpawn;
    }
    public void setSpawntime(long spawntime) {
        this.spawntime = spawntime;
    }
    public void setMonstersToSpawn(List<Monster> monstersToSpawn) {
        this.monstersToSpawn = monstersToSpawn;
    }
}
