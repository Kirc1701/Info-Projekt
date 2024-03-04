package src;

// Import necessary packages and classes
import org.jetbrains.annotations.NotNull;
import src.Graphikcontroller.*;
import src.Level.Level;
import src.Level.Level1;
import src.Level.Level2;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Baubar.Basis.DefaultBasis;
import src.Objekte.Baubar.Baubar;
import src.Objekte.Baubar.Mauer.DefaultMauer;
import src.Objekte.Baubar.Turm.DefaultTurm;
import src.Objekte.Monster.Monster;
import src.Objekte.Objekt;
import src.Objekte.Baubar.Turm.Turm;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import java.util.List;

// Imported these to use in calculations
import static java.lang.Math.abs;
import static java.lang.Math.min;
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
    public static boolean waitForStart = true;
    public static String loadDesign = "";
    public static int screenSelection;

    /**
     * The main method of the program. It runs the game loop and controls the flow of the game.
     *
     * @param args the command line arguments
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        aktuelleGrafik = new Hauptmenü();
        Main.screenSelection = 0;
        int aktuellesLevel;
        while(screenSelection == 0) {
            TimeUnit.MILLISECONDS.sleep(500);
        }
        aktuellesLevel = Hauptmenü.chosenLevel - 1;
        //Neue Basis
        Basis newBasis = new DefaultBasis(new Coords(0,0));
        // Array of game levels
        Level[] LEVELS = new Level[2];
        LEVELS[0] = new Level1(newBasis);
        LEVELS[1] = new Level2(LEVELS[0].getBasis());

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
            while (screenSelection == 1) {
                // Calls the method updateBuildings() which updates the state of the buildings in the game.
                updateBuildings();
                loadDesign();

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
                                aktuelleGrafik.repaint(basis.getPosition().x() * spaceBetweenLinesPixels, basis.getPosition().y() * spaceBetweenLinesPixels + titelbalkenSizePixels, spaceBetweenLinesPixels, spaceBetweenLinesPixels);
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
                    if (time == timeFired + 1) {
                        int monsterX = shot.get("MonsterX");
                        int monsterY = shot.get("MonsterY");
                        int turmX = shot.get("TurmX");
                        int turmY = shot.get("TurmY");

                        paintShot(monsterX, monsterY, turmX, turmY);
                        List<Map<String, Integer>> temp = new ArrayList<>(List.of(oldShots));
                        temp.remove(shot);
                        Map<String, Integer>[] arr = new Map[temp.size()];
                        oldShots = temp.toArray(arr);
                    }
                }

                int shotCounter = 0;
                for (Objekt building : karte.getBuildings().values()) {
                    if (building.getType().equals("DefaultTurm")) {
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

                                paintShot(monsterX, monsterY, turmX, turmY);
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
                                        laufendeKosten -= monster.getKopfgeld();
                                        System.out.println("Money "+money);
                                    }
                                }
                            }
                        }
                    }
                }

                for (Objekt building : karte.getBuildings().values().stream().toList()) {
                    if (building.getHealth() <= 0) {
                        int x = building.getPosition().x();
                        int y = building.getPosition().y();
                        karte.getBuildings().remove(new Coords(x, y));
                    }
                }
                updateBuildings();
                loadDesign();
                time++;
                TimeUnit.MILLISECONDS.sleep(500);
            }
            aktuelleGrafik.setVisible(false);
            aktuelleGrafik.dispose();
            if(aktuellesLevel == LEVELS.length - 1){
                Hauptmenü.chosenLevel = 1;
            }else{
                aktuellesLevel ++;
                Hauptmenü.chosenLevel = aktuellesLevel + 1;
            }

            int endeX = aktuelleGrafik.getX() + (aktuelleGrafik.getWidth() / 2) - 100;
            int endeY = aktuelleGrafik.getY() + (aktuelleGrafik.getHeight() / 2) - 50;
            screenSelection = 0;
            if(karte.playerWins()){
                aktuelleGrafik = new EndbildschirmGewonnen(endeX, endeY);
            }else {
                aktuelleGrafik = new EndbildschirmVerloren(endeX, endeY);
            }
            while(screenSelection == 0){
                TimeUnit.MILLISECONDS.sleep(500);
            }
//            if (karte.playerWins() && LEVELS.length > aktuellesLevel + 1) {
//                LEVELS[aktuellesLevel + 1].setBasis(LEVELS[aktuellesLevel].getBasis());
//                aktuellesLevel++;
//                LEVELS[aktuellesLevel].getBasis().setHealth(LEVELS[aktuellesLevel].getBasis().getMaxHealth());
//                karte = new Karte(LEVELS[aktuellesLevel]);
//                gameHasStarted = false;
//                money += LEVELS[aktuellesLevel].getStartKapital();
//                System.out.println("Money "+ money);
//                shotMonster = new HashMap[0];
//                oldShots = new HashMap[0];
//                aktuelleGrafik = new StarteSpielBildschirm();
//                aktuelleGrafik = new HauptgrafikSpiel(karte);
//                TimeUnit.MILLISECONDS.sleep(500);
//                aktuelleGrafik.repaint();
//            } else {
//                break;
//            }
        }
    }

    private static void loadDesign() throws IOException {
        if(!loadDesign.isEmpty()){
            File file = new File("savedDesigns/"+ loadDesign +".txt");
            if(file.canRead()){
                String[] arguments = getArguments(file);

                for(int i = 0; i < arguments.length-2; i += 3){
                    Baubar building = null;
                    Coords position = new Coords(Integer.parseInt(String.valueOf(arguments[i])), Integer.parseInt(String.valueOf(arguments[i + 1])));
                    if(arguments[i + 2].equals("DefaultTurm")){
                        building = new DefaultTurm(position);
                    }else if(arguments[i + 2].equals("DefaultMauer")){
                        building = new DefaultMauer(position);
                    }
                    if(building != null){
                        if(money - building.getKosten() >= 0) {
                            karte.addBuilding(position, building);
                            aktuelleGrafik.repaint(position.x() * spaceBetweenLinesPixels, position.y() * spaceBetweenLinesPixels + titelbalkenSizePixels, spaceBetweenLinesPixels, spaceBetweenLinesPixels);
                            for(Monster monster : karte.getMonsterList()){
                                monster.updateMonsterPath(karte);
                            }
                            money -= building.getKosten();
                            aktuelleGrafik.repaint(50, titelbalkenSizePixels / 2, 100, 30);
                        }
                    }
                }
            }
            loadDesign = "";
        }
    }

    @NotNull
    private static String[] getArguments(File file) throws IOException {
        FileReader reader = new FileReader(file);
        char[] input = new char[500];
        reader.read(input);
        String inputString = String.copyValueOf(input);
        for(int i = 0; i < input.length; i++){
            if(input[i] == '\u0000'){
                inputString = inputString.substring(0, i);
                break;
            }
        }
        return inputString.split("_");
    }

    /**
     * This method is used to paint a shot on the graphics.
     *
     * @param monsterX The x-coordinate of the monster.
     * @param monsterY The y-coordinate of the monster.
     * @param turmX The x-coordinate of the tower.
     * @param turmY The y-coordinate of the tower.
     */
    private static void paintShot(int monsterX, int monsterY, int turmX, int turmY) {
        int x = min(turmX, monsterX) * spaceBetweenLinesPixels;
        int y = min(turmY, monsterY) * spaceBetweenLinesPixels + titelbalkenSizePixels;
        int width = spaceBetweenLinesPixels * abs(turmX - monsterX);
        int height = spaceBetweenLinesPixels * abs(turmY - monsterY);

        aktuelleGrafik.repaint(x + spaceBetweenLinesPixels / 2 - 2, y + spaceBetweenLinesPixels / 2 - 2, width + 4, height + 4);
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
        if(laufendeKosten != 0){
            money -= laufendeKosten;
            laufendeKosten = 0;
            aktuelleGrafik.repaint(50, titelbalkenSizePixels / 2, 100, 30);
            System.out.println("Money "+money);
        }
    }
}
