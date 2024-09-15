package src.drawables.objects.buildings.wall;

import lombok.NoArgsConstructor;
import src.drawables.objects.ObjectType;
import src.drawables.objects.buildings.Building;
import src.util.CoordsInt;

@NoArgsConstructor
public abstract class Wall extends Building {
    public Wall(int health, CoordsInt position, double kosten, ObjectType type) {
        super(0, health, position, type, kosten);
    }
}
