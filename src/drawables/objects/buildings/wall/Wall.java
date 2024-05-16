package src.drawables.objects.buildings.wall;

import src.drawables.objects.buildings.Building;
import src.util.CoordsInt;

public abstract class Wall extends Building {
    public Wall(int health, CoordsInt position, double kosten, String type) {
        super(0, health, position, type, kosten);
    }
}
