package src.objekte.building;

import src.objekte.Objekt;
import src.objekte.monster.Monster;
import src.util.CoordsDouble;
import src.util.CoordsInt;

import java.util.List;

import static src.Main.karte;

public abstract class Building extends Objekt {
    protected final double kosten;
    protected boolean isBlueprint;
    public Building(int pStrength, int pHealth, CoordsInt position, String type, double kosten) {
        super(pStrength, pHealth, position, type);
        this.kosten = kosten;
        this.isBlueprint = false;
    }
    public void setBlueprint(boolean blueprint){
        this.isBlueprint = blueprint;
    }

    public double getKosten() {
        return kosten;
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
        karte.removeBuilding(position);
        List<Monster> monsters = karte.getMonsterList();
        for (Monster monster : monsters){
            monster.updateMonsterPath(karte);
        }
    }
}
