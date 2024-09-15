package src.drawables.objects.buildings;

import lombok.Getter;
import lombok.NoArgsConstructor;
import src.drawables.objects.Object;
import src.drawables.objects.ObjectType;
import src.drawables.objects.monster.Monster;
import src.util.CoordsDouble;
import src.util.CoordsInt;

import java.util.List;

import static src.Main.loop;

@NoArgsConstructor
public abstract class Building extends Object {
    @Getter
    protected double cost;
    protected boolean isBlueprint;
    public Building(int pStrength, int pHealth, CoordsInt position, ObjectType type, double cost) {
        super(pStrength, pHealth, position, type);
        this.cost = cost;
        this.isBlueprint = false;
    }


    public void setBlueprint(boolean blueprint){
        this.isBlueprint = blueprint;
    }

    @Override
    public CoordsDouble getDrawnPosition() {
        return position.toCoordsDouble();
    }

    @Override
    public float getOpacity() {
        return isBlueprint? .5f : 1.0f;
    }

    @Override
    public void die() {
        super.die();
        loop.getLogic_representation().removeBuilding(position);
        List<Monster> monsters = loop.getLogic_representation().getMonsterList();
        for (Monster monster : monsters){
            monster.updateMonsterPath(loop.getLogic_representation());
        }
    }
}
