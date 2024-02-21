package src.Objekte.Baubar.Turm;

import src.Coords;
import src.Objekte.Monster.Monster;

import java.util.*;
import java.util.List;

public class DefaultTurm extends Turm{
    public DefaultTurm(Coords position){
        super(5, 25, position, 4, 6, 20);
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
//            System.out.println("health: "+monsterToShoot.getHealth());

            Map<String, Integer> map = new HashMap<>();
            map.put("MonsterX", monsterToShoot.getPosition().x());
            map.put("MonsterY", monsterToShoot.getPosition().y());
            map.put("TurmX", position.x());
            map.put("TurmY", position.y());
            return map;
        }
        return new HashMap<>();
    }
}
