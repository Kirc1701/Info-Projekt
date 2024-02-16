package src;

// Import necessary packages and classes
import src.Graphikcontroller.EndbildschirmGewonnen;
import src.Graphikcontroller.EndbildschirmVerloren;
import src.Graphikcontroller.HauptgrafikSpiel;
import src.Graphikcontroller.StarteSpielBildschirm;
import src.Level.Level;
import src.Level.Level1;
import src.Level.Level2;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Monster.Monster;
import src.Objekte.Objekt;
import src.Objekte.Baubar.Turm.Turm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.util.List;

// Imported these to use in calculations
import static java.lang.Math.abs;
import static org.apache.commons.lang3.math.NumberUtils.min;
import static src.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;
import static src.Graphikcontroller.HauptgrafikSpiel.titelbalkenSizePixels;

/**
 * Main class that serves as the entry point for the program.
 * Contains the main method which starts the game.
 */
// Main class
public class Main {
    public static boolean building_update = false;  // Flag to indicate if the building needs to be updated
    public static Rectangle building_update_place = new Rectangle();  // The place in the GUI where the building update should be placed
    public static Rectangle monster_update_place;  // The place in the GUI where the monster update should be placed
    public static Map<String, Integer>[] shotMonster = new HashMap[0];  // An array of maps to keep track of shots at Monsters
    public static Map<String, Integer>[] oldShots = new Map[0];  // An array of maps to keep track of old shots
    public static JFrame aktuelleGrafik;  // The current graphics element
    public static Karte karte;  // A map of our game world
    public static boolean gameHasStarted = false;  // A flag to indicate if the game has started
    public static double money = 0;  // The amount of money a player has
    public static double laufendeKosten;  // Running costs variable
    public static int time = 0;

    /**
     * The main method of the program. It runs the game loop and controls the flow of the game.
     *
     * @param args the command line arguments
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    public static void main(String[] args) throws InterruptedException {
        // Array of game levels
        Level[] LEVELS = new Level[2];
        LEVELS[0] = new Level1();
        LEVELS[1] = new Level2(new Level1().getBasis());
        int aktuellesLevel = 0;


        // Player's starting balance is set according to the level's starting capital
        money = LEVELS[aktuellesLevel].getStartKapital();
        // A map is created based on the current level configuration
        karte = new Karte(LEVELS[aktuellesLevel]);

        // Creation of game windows
        aktuelleGrafik = new StarteSpielBildschirm(); // Initial game startup screen
        aktuelleGrafik = new HauptgrafikSpiel(karte); // Main game window with map
        TimeUnit.MILLISECONDS.sleep(500);
        aktuelleGrafik.repaint();
        while(true) {
            laufendeKosten = 0;
            time = 0;
            // The while loop below forms part of the game loop. It waits for the game to start.
            while (!gameHasStarted) {

                // Calls the method updateBuildings() which updates the state of the buildings in the game.
                updateBuildings();

                // Sleeps the current thread for 500 milliseconds.
                // This can be used to control the pace of the game, reducing the processing load on the CPU.
                TimeUnit.MILLISECONDS.sleep(500);
            }

            while (!karte.gameOver()) {
                shotMonster = new HashMap[0];

                if (!karte.getMonsterList().isEmpty()) {
                    for (Monster monster : karte.getMonsterList()) {
                        if (monster.getSchritteBisZiel() > 1) {
                            if (time % monster.getMovingSpeed() == monster.getSpawntime() % monster.getMovingSpeed()) {
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
                    monster_update_place = karte.spawnMonster(time);
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
                        if (time % turm.getSpeed() == turm.getSpawntime() % turm.getSpeed()) {
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
                                Monster[] tempList = karte.getMonsterList().toArray(new Monster[karte.getMonsterList().size()]);
                                karte.getMonsterList().removeIf(monster -> monster.getHealth() <= 0);
                                for(Monster monster : tempList){
                                    if(!karte.getMonsterList().contains(monster)){
                                        money += monster.getKopfgeld();
                                        System.out.println("Money "+money);
                                    }
                                }
                            }
                        }
                    }
                }

                for (Objekt building : karte.getBuildings().values().stream().toList()) {
                    if (building.getHealth() <= 0) {
                        int x = building.getPosition().getX();
                        int y = building.getPosition().getY();
                        karte.getBuildings().remove(new Coords(x, y));
                    }
                }

                updateBuildings();
                time++;
                TimeUnit.MILLISECONDS.sleep(500);
            }

            aktuelleGrafik.setVisible(false);
            aktuelleGrafik.dispose();
            if (karte.playerWins() && LEVELS.length > aktuellesLevel + 1) {
                LEVELS[aktuellesLevel + 1].setBasis(LEVELS[aktuellesLevel].getBasis());
                aktuellesLevel++;
                LEVELS[aktuellesLevel].getBasis().setHealth(LEVELS[aktuellesLevel].getBasis().getMaxHealth());
                karte = new Karte(LEVELS[aktuellesLevel]);
                gameHasStarted = false;
                money += LEVELS[aktuellesLevel].getStartKapital();
                System.out.println("Money "+ money);
                aktuelleGrafik = new StarteSpielBildschirm();
                aktuelleGrafik = new HauptgrafikSpiel(karte);
                TimeUnit.MILLISECONDS.sleep(500);
                aktuelleGrafik.repaint();
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

    /**
     * Updates the buildings in the game.
     * This method repaints the graphics of the building being updated and resets the update flag.
     * It also deducts the ongoing costs from the available money if any.
     * Prints the updated money amount to the console.
     */
    private static void updateBuildings() {
        if (building_update) {
            aktuelleGrafik.repaint(building_update_place.x * spaceBetweenLinesPixels, building_update_place.y * spaceBetweenLinesPixels + titelbalkenSizePixels, building_update_place.width, building_update_place.height);
            building_update = false;
            for(Monster monster : karte.getMonsterList()){
                monster.updateMonsterPath(karte);
            }
        }
        if(laufendeKosten > 0){
            money -= laufendeKosten;
            laufendeKosten = 0;
            System.out.println("Money "+money);
        }
    }
}
