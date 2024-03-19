package src.objekte.building.turm;

import src.Karte;
import src.Main;
import src.objekte.building.Building;
import src.objekte.monster.Monster;
import src.Tickable;
import src.util.CoordsDouble;
import src.util.CoordsInt;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Collections.min;
import static src.graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;
import static src.graphikcontroller.HauptgrafikSpiel.titelbalkenSizePixels;

public abstract class Turm extends Building implements Tickable {
    final int reach;
    protected final int attackSpeed;
    protected double timeTilShoot = attackSpeed;

    private Monster[] shotMonsters = new Monster[0];
    public Turm(int strength, int health, CoordsInt position, int reach, int speed, double kosten, String type){
        super(strength, health, position, type, kosten);
        attackSpeed = speed;
        this.reach = reach;
        Main.registerTickable(this);
    }

    @Override
    public void tick(double timeDelta, Karte karte) {
        if(isBlueprint) return;
        timeTilShoot = Math.max(0, timeTilShoot - timeDelta);
        if(timeTilShoot == 0){
            shotMonsters = shoot(karte);
            if(shotMonsters[0] != null){
                timeTilShoot = attackSpeed;
            }
        }
    }

    public abstract Monster[] shoot(Karte karte);

    public Monster shootNormal(Karte karte){
        List<Monster> monsters = karte.getMonsterList();
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
            return monsterToShoot;
        }
        return null;
    }

    @Override
    public void die() {
        super.die();
        Main.unregisterTickable(this);
    }

    @Override
    public void draw(Graphics g){
        super.draw(g);
        CoordsDouble drawnPositionTower = this.getDrawnPosition().scale(spaceBetweenLinesPixels);
        for(Monster monster : shotMonsters){
            if(monster == null){
                continue;
            }
            CoordsDouble drawnPositionMonster = monster.getDrawnPosition().scale(spaceBetweenLinesPixels);
            g.setColor(Color.RED);
            g.drawLine((int) drawnPositionTower.x()+ spaceBetweenLinesPixels/2, (int) drawnPositionTower.y()+ spaceBetweenLinesPixels/2 + titelbalkenSizePixels, (int) drawnPositionMonster.x() +spaceBetweenLinesPixels/2, (int) drawnPositionMonster.y() + spaceBetweenLinesPixels/2+ titelbalkenSizePixels);
            shotMonsters = new Monster[0];
        }
    }
}
