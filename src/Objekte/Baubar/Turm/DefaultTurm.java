package src.Objekte.Baubar.Turm;

import src.Coords;
import src.Objekte.Monster.Monster;

import java.util.*;
import java.util.List;

import static java.util.Collections.min;

public class DefaultTurm extends Turm{
    public DefaultTurm(Coords position){
        super(5, 25, position, 4, 6, 20, "DefaultTurm");
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
            HashMap<Integer, Monster> monsterMap = new HashMap<>();
            for(Monster monster : shootingMonsters){
                monsterMap.put(monster.getSchritteBisZiel(), monster);
            }
            int minimum = min(monsterMap.keySet());
            Monster monsterToShoot = monsterMap.get(minimum);
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
