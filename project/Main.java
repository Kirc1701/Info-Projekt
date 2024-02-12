package project;

import project.Graphikcontroller.HauptgrafikSpiel;
import project.Objekte.Basis.Basis;
import project.Objekte.Monster.Monster;
import project.Objekte.Objekt;
import project.Objekte.Turm.Turm;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.util.List;

import static java.lang.Math.abs;
import static org.apache.commons.lang3.math.NumberUtils.min;
import static project.Graphikcontroller.HauptgrafikSpiel.space;
import static project.Graphikcontroller.HauptgrafikSpiel.titelbalken;


public class Main {
    public static boolean building_update = false;
    public static Rectangle building_update_place = new Rectangle();
    public static Rectangle monster_update_place;
    public static Map<String, Integer> shotMonster = new HashMap<>();
    public static Map<String, Integer>[] oldShots = new Map[0];

    public static void main(String[] args) throws InterruptedException {
        Karte karte = new Karte(25, 40, new Coords(39, 12), new Coords(0, 12));
        HauptgrafikSpiel graphic = new HauptgrafikSpiel(karte);
        long time = 0;
        while(!karte.gameOver()){
            shotMonster = new HashMap<>();

            if (!karte.getMonsterList().isEmpty()) {
                for (Monster monster : karte.getMonsterList()) {
                    if(monster.getSchritteBisZiel() > 1) {
                        if (time % monster.getMovingSpeed() == 0) {
                            monster_update_place = monster.makeMove(karte, graphic);
                            graphic.repaint(monster_update_place.x * space, monster_update_place.y * space + titelbalken, monster_update_place.width, monster_update_place.height);
                        }
                    }else{
                        if(time % monster.getAttackSpeed() == 0){
                            monster.attack(karte);
                            Basis basis = karte.getBasis();
                            graphic.repaint(basis.getPosition().getX() * space, basis.getPosition().getY() * space + titelbalken, space, space);
                        }
                    }
                }
            }
            if ((time % karte.getLevel().getSpawntime() == 0) && !karte.getLevel().getMonstersToSpawn().isEmpty()) {
                monster_update_place = karte.spawnMonster();
                graphic.repaint(monster_update_place.x*space, monster_update_place.y*space + titelbalken, monster_update_place.width, monster_update_place.height);
            }

            for(Map<String, Integer> shot : oldShots){
                int monsterX = shot.get("MonsterX");
                int monsterY = shot.get("MonsterY");
                int turmX = shot.get("TurmX");
                int turmY = shot.get("TurmY");

                int x = min(turmX, monsterX) * space;
                int y = min(turmY, monsterY) * space + titelbalken;
                int width = space * abs(turmX - monsterX);
                int height = space * abs(turmY - monsterY);

                graphic.repaint(x, y, width + 2* space, height +2* space);
                List<Map<String, Integer>> temp = new ArrayList<>(List.of(oldShots));
                temp.remove(shot);
                Map<String, Integer>[] arr = new Map[temp.size()];
                oldShots = temp.toArray(arr);
            }

            for(Objekt building : karte.getBuildings().values()){
                if(building.getType().equals("Turm")){
                    Turm turm = (Turm) building;
                    if(time % turm.getSpeed() == 0){
                        shotMonster = turm.shoot(karte.getMonsterList());

                        if(!shotMonster.isEmpty()) {
                            int monsterX = shotMonster.get("MonsterX");
                            int monsterY = shotMonster.get("MonsterY");
                            int turmX = shotMonster.get("TurmX");
                            int turmY = shotMonster.get("TurmY");

                            int x = min(turmX, monsterX) * space;
                            int y = min(turmY, monsterY) * space + titelbalken;
                            int width = space * abs(turmX - monsterX);
                            int height = space * abs(turmY - monsterY);

                            graphic.repaint(x - space, y - space, width + 2* space, height +2* space);
                            List<Map<String, Integer>> temp = new ArrayList<>(List.of(oldShots));
                            temp.add(shotMonster);
                            Map<String, Integer>[] arr = new Map[temp.size()];
                            oldShots = temp.toArray(arr);
                        }
                    }
                }
            }
            karte.getMonsterList().removeIf(monster -> monster.getHealth() <= 0);

            if(building_update){
                graphic.repaint(building_update_place.x * space, building_update_place.y * space + titelbalken, building_update_place.width, building_update_place.height);
                building_update = false;
            }
            time++;
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        graphic.setVisible(false);
        graphic.dispose();
        System.exit(0);
    }
}
