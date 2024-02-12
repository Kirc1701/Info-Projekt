package project.Objekte.Turm;

import project.Coords;
import project.Objekte.Monster.Monster;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultTurm extends Turm{
    public DefaultTurm(Coords position){
        super(10, 25, position, 4, 2);
    }

    @Override
    public boolean shoot(List<Monster> monsters) {
        Map<Monster, Double> distances = new HashMap<>();
        for (Monster monster: monsters) {
            if(position.isInRange(reach, monster.getPosition())){
                distances.put(monster, position.getDistance(monster.getPosition()));
            }
        }
        Double smallest = 240.0;
        for(Double distance : distances.values()){
            if(distance < smallest){
                smallest = distance;
            }
        }


        return false;
    }
}
