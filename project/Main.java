package project;

import project.Graphikcontroller.EndbildschirmGewonnen;
import project.Graphikcontroller.EndbildschirmVerloren;
import project.Graphikcontroller.HauptgrafikSpiel;
import project.Graphikcontroller.Startbildschirm;
import project.Level.Level;
import project.Level.Level1;
import project.Level.Level2;
import project.Objekte.Basis.Basis;
import project.Objekte.Monster.Monster;
import project.Objekte.Objekt;
import project.Objekte.Turm.Turm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.util.List;

import static java.lang.Math.abs;
import static org.apache.commons.lang3.math.NumberUtils.min;
import static project.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;
import static project.Graphikcontroller.HauptgrafikSpiel.titelbalkenSizePixels;


public class Main {
    public static boolean building_update = false;
    public static Rectangle building_update_place = new Rectangle();
    public static Rectangle monster_update_place;
    public static Map<String, Integer>[] shotMonster = new HashMap[0];
    public static Map<String, Integer>[] oldShots = new Map[0];
    public static JFrame aktuelleGrafik;
    public static Karte karte;
    public static boolean gameHasStarted = false;

    public static void main(String[] args) throws InterruptedException {
        Level[] LEVELS = new Level[2];
        LEVELS[0] = new Level1();
        LEVELS[1] = new Level2(new Level1().getBasis());
        int aktuellesLevel = 0;
        karte = new Karte(LEVELS[aktuellesLevel]);
        aktuelleGrafik = new Startbildschirm();
        aktuelleGrafik = new HauptgrafikSpiel(karte);
        TimeUnit.MILLISECONDS.sleep(500);
        aktuelleGrafik.repaint();
        while(true) {
            int time = 0;
            while (!gameHasStarted) {
                if (building_update) {
                    aktuelleGrafik.repaint(building_update_place.x * spaceBetweenLinesPixels, building_update_place.y * spaceBetweenLinesPixels + titelbalkenSizePixels, building_update_place.width, building_update_place.height);
                    building_update = false;
                }
                TimeUnit.MILLISECONDS.sleep(500);
            }

            while (!karte.gameOver()) {
                shotMonster = new HashMap[0];

                if (!karte.getMonsterList().isEmpty()) {
                    for (Monster monster : karte.getMonsterList()) {
                        if (monster.getSchritteBisZiel() > 1) {
                            if (time % monster.getMovingSpeed() == 0) {
                                monster_update_place = monster.makeMove(karte);
                                aktuelleGrafik.repaint(monster_update_place.x * spaceBetweenLinesPixels, monster_update_place.y * spaceBetweenLinesPixels + titelbalkenSizePixels, monster_update_place.width, monster_update_place.height);
                            }
                        } else {
                            if (time % monster.getAttackSpeed() == 0) {
                                Basis basis = karte.getBasis();
                                monster.attack(basis);
                                aktuelleGrafik.repaint(basis.getPosition().getX() * spaceBetweenLinesPixels, basis.getPosition().getY() * spaceBetweenLinesPixels + titelbalkenSizePixels, spaceBetweenLinesPixels, spaceBetweenLinesPixels);
                            }
                        }
                    }
                }
                if ((time % karte.getLevel().getSpawnTime() == 0) && !karte.getLevel().getMonstersToSpawn().isEmpty()) {
                    monster_update_place = karte.spawnMonster();
                    aktuelleGrafik.repaint(monster_update_place.x * spaceBetweenLinesPixels, monster_update_place.y * spaceBetweenLinesPixels + titelbalkenSizePixels, monster_update_place.width, monster_update_place.height);
                }

                for (Map<String, Integer> shot : oldShots) {
                    int timeFired = shot.get("TimeFired");
                    if (time == timeFired + 2) {
                        int monsterX = shot.get("MonsterX");
                        int monsterY = shot.get("MonsterY");
                        int turmX = shot.get("TurmX");
                        int turmY = shot.get("TurmY");

                        int x = min(turmX, monsterX) * spaceBetweenLinesPixels;
                        int y = min(turmY, monsterY) * spaceBetweenLinesPixels + titelbalkenSizePixels;
                        int width = spaceBetweenLinesPixels * abs(turmX - monsterX);
                        int height = spaceBetweenLinesPixels * abs(turmY - monsterY);

                        aktuelleGrafik.repaint(x + spaceBetweenLinesPixels / 2, y + spaceBetweenLinesPixels / 2, width + 2, height + 2);
                        List<Map<String, Integer>> temp = new ArrayList<>(List.of(oldShots));
                        temp.remove(shot);
                        Map<String, Integer>[] arr = new Map[temp.size()];
                        oldShots = temp.toArray(arr);
                    }
                }

                int shotCounter = 0;
                for (Objekt building : karte.getBuildings().values()) {
                    if (building.getType().equals("Turm")) {
                        Turm turm = (Turm) building;
                        if (time % turm.getSpeed() == 0) {
                            List<Map<String, Integer>> tempShot = new ArrayList<>(List.of(shotMonster));
                            tempShot.add(turm.shoot(karte.getMonsterList()));
                            Map<String, Integer>[] arr = new Map[tempShot.size()];
                            shotMonster = tempShot.toArray(arr);
                            if (!shotMonster[shotCounter].isEmpty()) {
                                int monsterX = shotMonster[shotCounter].get("MonsterX");
                                int monsterY = shotMonster[shotCounter].get("MonsterY");
                                int turmX = shotMonster[shotCounter].get("TurmX");
                                int turmY = shotMonster[shotCounter].get("TurmY");

                                int x = min(turmX, monsterX) * spaceBetweenLinesPixels;
                                int y = min(turmY, monsterY) * spaceBetweenLinesPixels + titelbalkenSizePixels;
                                int width = spaceBetweenLinesPixels * abs(turmX - monsterX);
                                int height = spaceBetweenLinesPixels * abs(turmY - monsterY);

                                aktuelleGrafik.repaint(x + spaceBetweenLinesPixels / 2 - 1, y + spaceBetweenLinesPixels / 2 - 1, width + 2, height + 2);
                                aktuelleGrafik.repaint(monsterX * spaceBetweenLinesPixels, monsterY * spaceBetweenLinesPixels + titelbalkenSizePixels, spaceBetweenLinesPixels, spaceBetweenLinesPixels);
                                shotMonster[shotCounter].put("TimeFired", time);
                                List<Map<String, Integer>> temp = new ArrayList<>(List.of(oldShots));
                                temp.add(shotMonster[shotCounter]);
                                arr = new Map[temp.size()];
                                oldShots = temp.toArray(arr);
                                shotCounter++;
                            }
                        }
                    }
                }
                karte.getMonsterList().removeIf(monster -> monster.getHealth() <= 0);
                for (Objekt building : karte.getBuildings().values().stream().toList()) {
                    if (building.getHealth() <= 0) {
                        int x = building.getPosition().getX();
                        int y = building.getPosition().getY();
                        karte.getBuildings().remove(new Coords(x, y));
                    }
                }

                if (building_update) {
                    aktuelleGrafik.repaint(building_update_place.x * spaceBetweenLinesPixels, building_update_place.y * spaceBetweenLinesPixels + titelbalkenSizePixels, building_update_place.width, building_update_place.height);
                    building_update = false;
                }
                time++;
                TimeUnit.MILLISECONDS.sleep(500);
            }

            aktuelleGrafik.setVisible(false);
            aktuelleGrafik.dispose();
            if (karte.playerWins() && LEVELS.length > aktuellesLevel + 1) {
                LEVELS[aktuellesLevel + 1].setBasis(LEVELS[aktuellesLevel].getBasis());
                aktuellesLevel++;
                karte = new Karte(LEVELS[aktuellesLevel]);
                gameHasStarted = false;
                aktuelleGrafik = new Startbildschirm();
                aktuelleGrafik = new HauptgrafikSpiel(karte);
            } else {
                break;
            }
        }
        int endeX = aktuelleGrafik.getX() + (aktuelleGrafik.getWidth() / 2) - 100;
        int endeY = aktuelleGrafik.getY() + (aktuelleGrafik.getHeight() / 2) - 50;
        if(karte.playerWins()){
            aktuelleGrafik = new EndbildschirmGewonnen(endeX, endeY);
        }else {
            aktuelleGrafik = new EndbildschirmVerloren(endeX, endeY);
        }
    }
}
