package project.Objekte.Monster;

import project.Coords;
import project.Karte;
import project.Objekte.Objekt;

import java.awt.*;
import java.util.List;

public abstract class Monster extends Objekt {
    protected int movingSpeed;
    protected int schritteBisZiel;
    public Monster(int strength, int health, Coords position, int movingSpeed, int attackSpeed){
        super(strength, health, position, attackSpeed,  "Monster");
        this.movingSpeed = movingSpeed;
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
}
