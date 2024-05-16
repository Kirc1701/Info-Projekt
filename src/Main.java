package src;

// Import necessary packages and classes
//import org.jetbrains.annotations.NotNull;

import src.drawables.objects.buildings.Building;
import src.drawables.objects.buildings.basis.Basis;
import src.drawables.objects.buildings.basis.DefaultBasis;
import src.drawables.objects.monster.Monster;
import src.level.Level;
import src.util.CoordsInt;
import src.visuals.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Main class
public class Main {
    public static JFrame aktuelleGrafik;  // The current graphics element
    public static LogicRepresentation logicRepresentation;  // A map of our game world
    public static double money = 0;  // The amount of money a player has
    public static String loadDesign = "";
    public static int screenSelection = 0;
    public static int anzahlMauern = 0;
    public static final Sound sound = new Sound();
    public static final SFX sfx = new SFX();
    public static Boolean source = false;

    public static Basis basis;

    private static List<Drawable> drawables = new CopyOnWriteArrayList<>();
    private static List<Tickable> tickables = new CopyOnWriteArrayList<>();
    private static int currentLevel = 1;


    /**
     * The main method of the program. It runs the game loop and controls the flow of the game.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(Toolkit.getDefaultToolkit().getScreenSize());

        aktuelleGrafik = new MainMenu();
        playMusic(3);
        Main.screenSelection = 0;
    }


    public static void selectLevel(int chosenLevel) {
        Level level = getLevel(chosenLevel);
        currentLevel = chosenLevel;
        money = level.getStartKapital();
        logicRepresentation = new LogicRepresentation(level);
        anzahlMauern = logicRepresentation.getLevel().getAnzahlMauern();
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

        GameScreen gameScreen = new GameScreen(logicRepresentation);
        frame.setContentPane(gameScreen);
        frame.setSize(gameScreen.getSize());
        frame.setVisible(true);
        frame.setResizable(false);
        new StartGamePopUp(gameScreen.getWidth());
        aktuelleGrafik = frame;
    }



    public static void onGameOver() {
        System.out.println("Game is over");
        drawables = new ArrayList<>();
        tickables = new ArrayList<>();
        aktuelleGrafik.setVisible(false);
        aktuelleGrafik.dispose();
        if (currentLevel == 4) {
            currentLevel = 1;
        } else if (logicRepresentation.playerWins()) currentLevel++;

        int endeX = aktuelleGrafik.getX() + (aktuelleGrafik.getWidth() / 2) - 100;
        int endeY = aktuelleGrafik.getY() + (aktuelleGrafik.getHeight() / 2) - 50;
        screenSelection = 0;
        stopMusic();
        if (logicRepresentation.playerWins()) {
            stopMusic();
            aktuelleGrafik = new WinningScreen(endeX, endeY);
        } else {
            stopMusic();
            aktuelleGrafik = new LosingScreen(endeX, endeY);
        }
        stopMusic();
    }

    private static Level getLevel(int aktuellesLevel) {
        drawables = new CopyOnWriteArrayList<>();
        tickables = new CopyOnWriteArrayList<>();
        basis = new DefaultBasis(new CoordsInt(0, 0));
        try {
            Class<?> levelToLoad = Class.forName("src.level.Level" + aktuellesLevel);
            return (Level) levelToLoad.getDeclaredConstructor(Basis.class).newInstance(basis);
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            System.out.println("Couldn't load class file for level " + aktuellesLevel);
            throw new RuntimeException(e);
        }
    }

    public static void loadDesign() throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (!loadDesign.isEmpty()) {
            InputStream stream = Main.class.getClassLoader().getResourceAsStream(loadDesign + ".txt");
            if (stream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String[] arguments = getArguments(reader);
                for (int i = 0; i < arguments.length - 2; i += 3) {
                    CoordsInt position = new CoordsInt(Integer.parseInt(String.valueOf(arguments[i])), Integer.parseInt(String.valueOf(arguments[i + 1])));
                    String build = arguments[i + 2].split(" ")[1];
                    Class<?> buildingToBuild = Class.forName(build);
                    Building building = (Building) buildingToBuild.getDeclaredConstructor(CoordsInt.class).newInstance(position);

                    if (money - building.getKosten() >= 0) {
                        logicRepresentation.addBuilding(position, building);
                        for (Monster monster : logicRepresentation.getMonsterList()) {
                            monster.updateMonsterPath(logicRepresentation);
                        }
                        playSFX(8);
                        money -= building.getKosten();
                    }
                }
            }else{
                System.out.println("Ungültige File");
            }
            loadDesign = "";
        }
    }

    //    @NotNull
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static String[] getArguments(BufferedReader reader) throws IOException {
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

    public static void playMusic(int i) {
        if (!Settings.musicMuted) {
            sound.setFile(i);
            sound.play();
            sound.loop();
        }
    }

    public static void stopMusic() {
        try {sound.stop();}
        catch(Exception e) {System.out.println("Nope");}
    }

    public static void playSFX(int i) {
        if (!Settings.soundmute) {
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

    public static void registerDrawable(Drawable d, int index) {
        drawables.add(index, d);
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
        } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }
}
