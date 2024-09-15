package src.drawables.objects.buildings.basis;

import lombok.NoArgsConstructor;
import src.Main;
import src.drawables.objects.ObjectType;
import src.drawables.objects.buildings.Building;
import src.util.CoordsInt;

import java.io.Serializable;

import static src.LoopType.game_over;
import static src.Main.loop;

public abstract class Basis extends Building implements Serializable {
    public Basis(int strength, int health, CoordsInt position, ObjectType type){
        super(strength, health, position, type, 0);
    }

    public Basis(){
        super();
    }

    @Override
    public void die() {
        loop.update(game_over);
    }
}
