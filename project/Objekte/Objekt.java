package project.Objekte;

import project.Coords;

public class Objekt {
    protected int strength;
    protected int maxHealth;
    protected int health;
    protected Coords position;
    protected String type;
    protected int attackSpeed;

    public Objekt(int pStrength, int pHealth, Coords position, int speed, String type){
        strength = pStrength;
        health = pHealth;
        maxHealth = health;
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

    public int getSpeed() {
        return attackSpeed;
    }

    public void setSpeed(int speed) {
        this.attackSpeed = speed;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(int attackSpeed) {
        this.attackSpeed = attackSpeed;
    }
}
