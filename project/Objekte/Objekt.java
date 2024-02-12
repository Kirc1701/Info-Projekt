package project.Objekte;

import project.Coords;

public class Objekt {
    protected int strength;
    protected int health;
    protected Coords position;
    protected String type;
    protected long attackSpeed;

    public Objekt(int pStrength, int pHealth, Coords position, long speed, String type){
        strength = pStrength;
        health = pHealth;
        this.position = position;
        this.type = type;
        this.attackSpeed = speed;
    }

    public int getStrength() {
        return strength;
    }

    public int getHealth() {
        return health;
    }

    public Coords getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPosition(Coords position) {
        this.position = position;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getSpeed() {
        return attackSpeed;
    }

    public void setSpeed(long speed) {
        this.attackSpeed = speed;
    }
}
