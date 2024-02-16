package src.Objekte.Monster;

import src.Coords;
import src.Karte;
import src.Objekte.Objekt;

import java.awt.*;
import java.util.List;

public abstract class Monster extends Objekt {
    protected int movingSpeed;
    protected int schritteBisZiel;
    protected double kopfgeld;
    protected double monsterPathWeight;
    protected List<Coords> monsterPathNodes;

    public Monster(int strength, int health, Coords position, int movingSpeed, int attackSpeed, double kopfgeld, String type){
        super(strength, health, position, attackSpeed, type);
        this.movingSpeed = movingSpeed;
        this.kopfgeld = kopfgeld;
        schritteBisZiel = 250;
    }

    public abstract Rectangle makeMove(Karte karte);

    public abstract void updateMonsterPath(Karte karte);

    public void attack(Objekt objekt){
        objekt.setHealth(objekt.getHealth() - strength);
    }

    public int getMovingSpeed() {
        return movingSpeed;
    }

    public int getSchritteBisZiel() {
        return schritteBisZiel;
    }

    public double getKopfgeld() {
        return kopfgeld;
    }
}
