package src;

// Import necessary packages and classes
//import org.jetbrains.annotations.NotNull;

import src.Graphikcontroller.*;
import src.Level.*;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Baubar.Basis.DefaultBasis;
import src.Objekte.Baubar.Building;
import src.Objekte.Baubar.Mauer.DefaultMauer;
import src.Objekte.Baubar.Turm.DefaultTurm;
import src.Objekte.Monster.Monster;
import src.Objekte.Objekt;
import src.Objekte.Baubar.Turm.Turm;
import src.util.CoordsInt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import java.util.List;

// Imported these to use in calculations
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static src.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;
import static src.Graphikcontroller.HauptgrafikSpiel.titelbalkenSizePixels;

// Main class
public class Main {
    public static JFrame aktuelleGrafik;  // The current graphics element
    public static Karte karte;  // A map of our game world
    public static boolean gameHasStarted = false;  // A flag to indicate if the game has started
    public static double money = 0;  // The amount of money a player has
    public static String loadDesign = "";
    public static int screenSelection = 0;
    public static int anzahlMauern = 0;
    public static Sound sound = new Sound();
    public static SFX sfx = new SFX();
    public static Boolean source = false;

    private static List<Drawable> drawables = new CopyOnWriteArrayList<>();
    private static List<Tickable> tickables = new CopyOnWriteArrayList<>();
    private static int currentLevel = -1;


    /**
     * The main method of the program. It runs the game loop and controls the flow of the game.
     *
     * @param args the command line arguments
     * @throws InterruptedException if the thread is interrupted while sleeping
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        aktuelleGrafik = new Hauptmenue();
        playMusic(3);
        Main.screenSelection = 0;
    }


    public static void selectLevel(int chosenLevel) {
        Level level = getLevel(chosenLevel);
        currentLevel = chosenLevel;
        money = level.getStartKapital();
        karte = new Karte(level);
        anzahlMauern = karte.getLevel().getAnzahlMauern();
    }

    public static void setupGameWindow() {
        JFrame frame = new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                frame.dispose();
                System.exit(0);
            }
        });

        HauptgrafikSpiel hauptgrafikSpiel = new HauptgrafikSpiel(karte);
        frame.setContentPane(hauptgrafikSpiel);
        frame.setSize(hauptgrafikSpiel.getSize());
        frame.setVisible(true);
        frame.setResizable(false);
        new StarteSpielBildschirm(hauptgrafikSpiel.getWidth());
        aktuelleGrafik = frame;
    }



    public static void onGameOver() {
        System.out.println("Game is over");
        drawables = new ArrayList<>();
        tickables = new ArrayList<>();
        aktuelleGrafik.setVisible(false);
        aktuelleGrafik.dispose();
        if (currentLevel == 4) {
            Hauptmenue.chosenLevel = 1;
        } else {
            if (karte.playerWins()) {
                currentLevel++;
                Hauptmenue.chosenLevel = currentLevel + 1;
            }
        }

        int endeX = aktuelleGrafik.getX() + (aktuelleGrafik.getWidth() / 2) - 100;
        int endeY = aktuelleGrafik.getY() + (aktuelleGrafik.getHeight() / 2) - 50;
        screenSelection = 0;
        stopMusic();
        if (karte.playerWins()) {
            stopMusic();
            aktuelleGrafik = new EndbildschirmGewonnen(endeX, endeY);
        } else {
            stopMusic();
            aktuelleGrafik = new EndbildschirmVerloren(endeX, endeY);
        }
        stopMusic();
    }

    private static Level getLevel(int aktuellesLevel) {
        Basis newBasis = new DefaultBasis(new CoordsInt(0, 0));
        try {
            Class<?> levelToLoad = Class.forName("src.Level.Level" + aktuellesLevel);
            return (Level) levelToLoad.getDeclaredConstructor(Basis.class).newInstance(newBasis);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            System.out.println("Couldn't load class file for level " + aktuellesLevel);
            throw new RuntimeException(e);
        }
    }

    public static void loadDesign() throws IOException {
        if (!loadDesign.isEmpty()) {
            File file = new File("savedDesigns/" + loadDesign + ".txt");
            if (file.canRead()) {
                String[] arguments = getArguments(file);

                for (int i = 0; i < arguments.length - 2; i += 3) {
                    Building building = null;
                    CoordsInt position = new CoordsInt(Integer.parseInt(String.valueOf(arguments[i])), Integer.parseInt(String.valueOf(arguments[i + 1])));
                    if (arguments[i + 2].equals("DefaultTurm")) {
                        building = new DefaultTurm(position);
                    } else if (arguments[i + 2].equals("DefaultMauer")) {
                        building = new DefaultMauer(position);
                    }
                    if (building != null) {
                        if (money - building.getKosten() >= 0) {
                            karte.addBuilding(position, building);
                            for (Monster monster : karte.getMonsterList()) {
                                monster.updateMonsterPath(karte);
                            }
                            playSFX(8);
                            money -= building.getKosten();
                        }
                    }
                }
            }
            loadDesign = "";
        }
    }

    //    @NotNull
    private static String[] getArguments(File file) throws IOException {
        FileReader reader = new FileReader(file);
        char[] input = new char[15000];
        reader.read(input);
        String inputString = String.copyValueOf(input);
        for (int i = 0; i < input.length; i++) {
            if (input[i] == '\u0000') {
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
     * @param turmX    The x-coordinate of the tower.
     * @param turmY    The y-coordinate of the tower.
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

    public static void playMusic(int i) {
        if (!Einstellungen.musicmute) {
            sound.setFile(i);
            sound.play();
            sound.loop();
        }
    }

    public static void stopMusic() {
        try {sound.stop();}
        catch(Exception e) {System.out.println("bruh");}
    }

    public static void playSFX(int i) {
        if (!Einstellungen.soundmute) {
            sfx.setFile(i);
            sfx.play();
        }
    }

    public static void registerTickable(Tickable t){
        tickables.add(t);
    }

    public static void unregisterTickable(Tickable t){
        tickables.remove(t);
    }

    public static void registerDrawable(Drawable d){
        drawables.add(d);
    }

    public static void unregisterDrawable(Drawable d){
        drawables.remove(d);
    }

    public static List<Tickable> getTickables(){
        return tickables;
    }
    public static List<Drawable> getDrawables(){
        return drawables;
    }

    public static void startGame() {
        try {
            loadDesign();
            stopMusic();
            playMusic(2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
