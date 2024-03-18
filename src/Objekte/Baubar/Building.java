package src.Objekte.Baubar;

import src.Objekte.Baubar.Mauer.DefaultMauer;
import src.Objekte.Baubar.Turm.DefaultTurm;
import src.Objekte.Baubar.Turm.Scharfschuetzenturm;
import src.Objekte.Baubar.Turm.Schnellschussgeschuetz;
import src.Objekte.Objekt;
import src.util.CoordsDouble;
import src.util.CoordsInt;

import static src.Main.karte;

public abstract class Building extends Objekt {
    protected static double kosten;
    protected boolean isBlueprint;
    public Building(int pStrength, int pHealth, CoordsInt position, String type, double kosten) {
        super(pStrength, pHealth, position, type);
        this.kosten = kosten;
        this.isBlueprint = false;
    }
    public void setBlueprint(boolean blueprint){
        this.isBlueprint = blueprint;
    }

    public static double getKosten() {
        return kosten;
    }

    static public Building getBaubar(String name, CoordsInt position){
        if(name.equals("DefaultTurm")){
            return new DefaultTurm(position);
        }else if(name.equals("DefaultMauer")){
            return new DefaultMauer(position);
        }else if(name.equals("SchnellschussTurm")){
            return new Schnellschussgeschuetz(position);
        }else if(name.equals("ScharfschuetzenTurm")){
            return new Scharfschuetzenturm(position);
        }else{
            return null;
        }
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
    }
}
