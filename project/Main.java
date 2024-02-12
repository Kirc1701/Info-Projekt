package project;

import project.Objekte.Monster.Monster;
import project.Objekte.Objekt;
import project.Objekte.Turm.Turm;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static project.Graphic.space;


public class Main {
    public static boolean building_update = false;
    public static Rectangle building_update_place = new Rectangle();
    public static void main(String[] args) throws InterruptedException {
        Rectangle monster_update_place;
        Karte karte = new Karte(25, 40, new Coords(39, 12), new Coords(0, 12));
        Graphic graphic = new Graphic(karte);
        long time = 1;
        while(!karte.gameOver()){
            if (time % karte.getLevel().getSpawntime() == 0 && !karte.getLevel().getMonstersToSpawn().isEmpty()) {
                monster_update_place = karte.spawnMonster(graphic);
                graphic.repaint(monster_update_place.x*space, monster_update_place.y*space, monster_update_place.width, monster_update_place.height);
            }
            if (!karte.getMonsterList().isEmpty()) {
                for (Monster monster : karte.getMonsterList()) {
                    if (time % monster.getMovingSpeed() == 0) {
                        monster_update_place = monster.makeMove(karte, graphic);
                        graphic.repaint(monster_update_place.x*space, monster_update_place.y*space, monster_update_place.width, monster_update_place.height);
                    }
                }
            }
            for(Objekt building : karte.getBuildings().values()){
                if(building.getType().equals("Turm")){
                    Turm turm = (Turm) building;
                    if(time % turm.getSpeed() == 0){
                        turm.shoot(karte.getMonsterList());
                        graphic.animateShoot();
                    }
                }
            }
            if(building_update){
                graphic.repaint(building_update_place.x*space, building_update_place.y*space, building_update_place.width, building_update_place.height);
                building_update = false;
            }
            time++;
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }
}
