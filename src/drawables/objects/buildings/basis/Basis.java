package src.drawables.objects.buildings.basis;

import src.Main;
import src.drawables.objects.buildings.Building;
import src.util.CoordsInt;

public abstract class Basis extends Building {
    public Basis(int strength, int health, CoordsInt position, String type){
        super(strength, health, position, type, 0);
    }

    @Override
    public void die() {
        Main.onGameOver();
    }
}
