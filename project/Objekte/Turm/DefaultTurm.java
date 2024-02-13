package project.Objekte.Turm;

import project.Coords;
import project.Objekte.Monster.Monster;

import java.util.*;
import java.util.List;

public class DefaultTurm extends Turm{
    public DefaultTurm(Coords position){
        super(5, 25, position, 4, 6);
    }

    @Override
    public Map<String, Integer> shoot(List<Monster> monsters) {
        List<Monster> shootingMonsters = new ArrayList<>();
        for (Monster monster: monsters) {
            if(position.isInRange(reach, monster.getPosition())){
                shootingMonsters.add(monster);
            }
        }
        if(!shootingMonsters.isEmpty()) {
            Monster monsterToShoot = shootingMonsters.get(0);
            monsterToShoot.setHealth(monsterToShoot.getHealth() - strength);
            System.out.println("health: "+monsterToShoot.getHealth());

            Map<String, Integer> map = new HashMap<>();
            map.put("MonsterX", monsterToShoot.getPosition().getX());
            map.put("MonsterY", monsterToShoot.getPosition().getY());
            map.put("TurmX", position.getX());
            map.put("TurmY", position.getY());
            return map;
        }
        return new HashMap<>();
    }
}
