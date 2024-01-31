package project;

public abstract class Turm extends Objekt{
    protected int reach;
    public Turm(int strength, int health, int pReach){
        super(strength, health);
        reach = pReach;
    }

    public abstract boolean shoot(Monster[] monster);
}
