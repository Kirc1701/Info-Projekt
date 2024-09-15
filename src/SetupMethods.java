package src;

import src.drawables.objects.buildings.basis.Basis;
import src.level.Level;
import src.visuals.GameScreen;
import src.visuals.StartGamePopUp;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import static src.Main.loop;

public class SetupMethods {
    public static void selectLevel(int chosenLevel) {
        loop.setCurrent_level(chosenLevel);
        Level level = getLevel(chosenLevel);
        loop.setMoney(level.getStartKapital());
        loop.resetLogicRepresentation(level);
        loop.setWall_count(level.getAnzahlMauern());
    }

    private static Level getLevel(int currentLevel) {
        loop.resetDrawables();
        loop.resetTickables();
        loop.resetBasis();
        try {
            Class<?> levelToLoad = Class.forName("src.level.Level" + currentLevel);
            return (Level) levelToLoad.getDeclaredConstructor(Basis.class).newInstance(loop.getBasis());
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            System.out.println("Couldn't load class file for level " + currentLevel);
            throw new RuntimeException(e);
        }
    }

    public static JFrame[] setUpGameWindow(){
        JFrame game_frame = new JFrame();
        game_frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                game_frame.setVisible(false);
                game_frame.dispose();
                System.exit(0);
            }
        });
        loop.resetTickables();
        loop.resetDrawables();
        loop.getLogic_representation().addToDrawablesAndTickables();
        loop.setBasis(loop.getLogic_representation().getBasis());
        GameScreen gameScreen = new GameScreen(loop.getLogic_representation());
        game_frame.setContentPane(gameScreen);
        game_frame.setSize(gameScreen.getSize());
        game_frame.setVisible(true);
        game_frame.setResizable(false);
        JFrame start_frame = new StartGamePopUp(gameScreen.getWidth());
        JFrame[] frames = new JFrame[2];
        frames[1] = start_frame;
        frames[0] = game_frame;
        return frames;
    }

    //Load-Design needs to be implemented
}
