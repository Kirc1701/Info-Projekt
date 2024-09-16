package src;

import lombok.Getter;
import lombok.Setter;
import src.drawables.objects.buildings.basis.Basis;
import src.drawables.objects.buildings.basis.DefaultBasis;
import src.level.Level;
import src.util.CoordsInt;
import src.visuals.*;

import javax.swing.*;

import java.io.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static src.util.SoundUtils.playMusic;
import static src.util.SoundUtils.stopMusic;
import static src.visuals.GameScreen.paused;
import static src.LoopType.*;

public class UpdateLoop implements Runnable {
    private JFrame[] current_graphic = new JFrame[2];

    @Getter
    private List<Drawable> drawables = new CopyOnWriteArrayList<>();
    @Getter
    private List<Tickable> tickables = new CopyOnWriteArrayList<>();

    @Getter
    private LogicRepresentation logic_representation;

    private LoopType current_loop;
    //Level Access-Methods
    @Getter
    @Setter
    private int current_level = 1;

    //Basis Access-Methods
    @Setter
    @Getter
    private Basis basis;
    //Money Access-Methods
    @Getter
    @Setter
    private double money = 0;
    //Wall-Count Access-Methods
    @Setter
    private int wall_count;

    private boolean game_loaded = false;
    private boolean music_playing = false;

    public void run() {
        current_loop = main_menu;
        try{
            InputStream stream = MainMenu.class.getClassLoader().getResourceAsStream("Save.txt");
            if(stream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                char[] input = new char[6];
                reader.read(input);
                if(input[0] == '\u0000') throw new IOException("Invalid save file");
                String str = String.copyValueOf(input, 0, 5);
                if(str.equals("Level")) SetupMethods.selectLevel(input[5] - '0');
                reader.close();
            }
        } catch (IOException _) {
        }
        main_menu_loop();
    }

    private void main_menu_loop(){
        current_graphic[0] = new MainMenu();
        if(!music_playing) {
            playMusic(3);
            music_playing = true;
        }
        try {
            check_for_updates();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void game_loop_pending(boolean continue_game) {
        stopMusic();
        music_playing = false;
        SetupMethods.selectLevel(current_level);
        if(continue_game){
            LogicRepresentation lr;
            try (FileInputStream fileInputStream = (FileInputStream) UpdateLoop.class.getClassLoader().getResourceAsStream("Save.txt");
                 ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                lr = (LogicRepresentation) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            logic_representation = lr;
        }else{
            File save_file = new File("Save.txt");
            try{
                if(save_file.createNewFile()){
                    if(save_file.canWrite()){
                        FileWriter fileWriter = new FileWriter(save_file, true);
                        fileWriter.write("");
                        fileWriter.flush();
                        fileWriter.close();
                    }
                }else{
                    if(save_file.canWrite()){
                        FileWriter fileWriter = new FileWriter(save_file, false);
                        fileWriter.write("");
                        fileWriter.flush();
                        fileWriter.close();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            try (PrintWriter printWriter = new PrintWriter("Save.txt")) {
//                printWriter.print("");
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
        }
        playMusic(2);
        music_playing = true;
        current_graphic = SetupMethods.setUpGameWindow();
        game_loaded = true;
        try {
            check_for_updates();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void game_loop_started() throws InterruptedException {
        paused = false;
        check_for_updates();
    }

    private void game_over() throws InterruptedException {
        resetDrawables();
        resetTickables();
        current_graphic[0].setVisible(false);
        paused = true;
        if(current_level == 4){
            current_level = 1;
        } else if(logic_representation.playerWins()) current_level++;
        try (PrintWriter printWriter = new PrintWriter("Save.txt")) {
            printWriter.print("Level" + current_level);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }        int gameOverScreenX = current_graphic[0].getX() + (current_graphic[0].getWidth() / 2) - 100;
        int gameOverScreenY = current_graphic[0].getY() + (current_graphic[0].getHeight() / 2) - 50;

        stopMusic();
        music_playing = false;
        if(logic_representation.playerWins()){
            current_graphic[0] = new WinningScreen(gameOverScreenX, gameOverScreenY);
        }else{
            current_graphic[0] = new LosingScreen(gameOverScreenX, gameOverScreenY);
        }
        game_loaded = false;
        check_for_updates();
    }

    private void level_selection() throws InterruptedException {
        current_graphic[0] = new LevelSelectionScreen();
        check_for_updates();
    }

    private void settings() throws InterruptedException {
        try (FileOutputStream fileOutputStream = new FileOutputStream("Save.txt");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(logic_representation);
            objectOutputStream.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        new Settings(game_loaded, !paused);
        if(!paused) paused = true;
        check_for_updates();
    }

    private synchronized void check_for_updates() throws InterruptedException {
        wait();
        switch (current_loop) {
            case main_menu:
                main_menu_loop();
                break;
            case game_loop_pending:
                game_loop_pending(false);
                break;
            case game_loop_started:
                game_loop_started();
                break;
            case game_over:
                game_over();
                break;
            case level_selection:
                level_selection();
                break;
            case settings:
                settings();
                break;
            case forward:
                check_for_updates();
                break;
            case main_menu_during_game:
                current_graphic[0].setVisible(false);
                current_graphic[0].dispose();
                current_graphic[1].setVisible(false);
                current_graphic[1].dispose();
                stopMusic();
                main_menu_loop();
                break;
            case continue_game:
                game_loop_pending(true);
                break;
        }
    }

    public synchronized void update(LoopType update_info){
        current_loop = update_info;
        notify();
    }

    public synchronized void update(int level){
        current_loop = game_loop_pending;
        current_level = level;
        notify();
    }

    //Access-Methods

    //Tickables Access-Methods
    public void registerTickable(Tickable t){
        tickables.add(t);
    }
    public void unregisterTickable(Tickable t){
        tickables.remove(t);
    }
    public void resetTickables(){tickables = new CopyOnWriteArrayList<>();}

    //Drawables Access-Methods
    public void registerDrawable(Drawable d){
        drawables.add(d);
    }
    public void registerDrawable(Drawable d, int index) {
        drawables.add(index, d);
    }
    public void unregisterDrawable(Drawable d){
        drawables.remove(d);
    }
    public void resetDrawables(){drawables = new CopyOnWriteArrayList<>();}

    public void resetBasis(){basis = new DefaultBasis(new CoordsInt(0,0));}

    //Logic-Representation Access-Methods
    public void resetLogicRepresentation(Level level){
        logic_representation = new LogicRepresentation(level);
    }
}
