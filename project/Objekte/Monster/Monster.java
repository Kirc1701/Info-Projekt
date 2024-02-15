package project.Objekte.Monster;

import project.Coords;
import project.Karte;
import project.Objekte.Objekt;

import java.awt.*;

public abstract class Monster extends Objekt {
    protected int movingSpeed;
    protected int schritteBisZiel;
    protected double kopfgeld;
    protected int spawntime;

    public Monster(int strength, int health, Coords position, int movingSpeed, int attackSpeed, double kopfgeld, int spawntime){
        super(strength, health, position, attackSpeed,  "Monster");
        this.movingSpeed = movingSpeed;
        this.kopfgeld = kopfgeld;
        this.spawntime = spawntime;
        schritteBisZiel = 50;
    }

    public abstract Rectangle makeMove(Karte karte);

    public void attack(Objekt objekt){
        objekt.setHealth(objekt.getHealth() - strength);
    }

    public int getMovingSpeed() {
        return movingSpeed;
    }

    public void setMovingSpeed(int movingSpeed) {
        this.movingSpeed = movingSpeed;
    }

    public int getSchritteBisZiel() {
        return schritteBisZiel;
    }

    public void setSchritteBisZiel(int schritteBisZiel) {
        this.schritteBisZiel = schritteBisZiel;
    }

    public double getKopfgeld() {
        return kopfgeld;
    }

    public void setKopfgeld(double kopfgeld) {
        this.kopfgeld = kopfgeld;
    }

    public int getSpawntime() {
        return spawntime;
    }

    public void setSpawntime(int spawntime) {
        this.spawntime = spawntime;
    }
}
