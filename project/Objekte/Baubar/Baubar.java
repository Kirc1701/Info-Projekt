package project.Objekte.Baubar;

import project.Coords;
import project.Objekte.Baubar.Mauer.DefaultMauer;
import project.Objekte.Baubar.Turm.DefaultTurm;
import project.Objekte.Objekt;

import java.util.ArrayList;
import java.util.List;

public abstract class Baubar extends Objekt {
    protected double kosten;
    public final static List<String> BAUBARE_KLASSEN = List.of(new String[]{
            "DefaultTurm",
            "DefaultMauer"
    });
    public Baubar(int pStrength, int pHealth, Coords position, int speed, String type, double kosten) {
        super(pStrength, pHealth, position, speed, type);
        this.kosten = kosten;
    }

    public double getKosten() {
        return kosten;
    }

    public void setKosten(double kosten) {
        this.kosten = kosten;
    }

    static public Baubar getBaubar(String name, Coords position){
        if(name.equals("DefaultTurm")){
            return new DefaultTurm(position);
        }else if(name.equals("DefaultMauer")){
            return new DefaultMauer(position);
        }else{
            return null;
        }
    }
}
