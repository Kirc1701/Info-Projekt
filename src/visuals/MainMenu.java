package src.visuals;

import src.SetupMethods;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

import static src.LoopType.*;
import static src.Main.loop;

public class MainMenu extends JFrame implements MouseListener {
    private BufferedImage bufferedImage = null;

    private final boolean old_game_active;
    private int start_new_game_y = 156;
    private int level_selection_y = 242;
    private int settings_y = 328;
    private int credits_y = 414;

    public MainMenu(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );
        try{
            InputStream stream = MainMenu.class.getClassLoader().getResourceAsStream("Save.txt");
            if(stream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                char[] input = new char[6];
                reader.read(input);
                String str = String.copyValueOf(input, 0, 5);
                old_game_active = !str.equals("Level");
                SetupMethods.selectLevel(input[5] - '0');
                reader.close();
            }else{
                old_game_active = false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("images/BackgroundMenu.png")));
        } catch (IOException ignored) {}

        if(old_game_active){
            int margin = 86;
            start_new_game_y += margin;
            level_selection_y += margin;
            settings_y += margin;
            credits_y += margin;
        }

        setSize(900, 600);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 450,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 300
        );
        addMouseListener(this);
        setLayout(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(
                bufferedImage,
                0,
                0,
                900,
                600,
                null
        );
        g.setFont(new Font("Helvetica", Font.BOLD, 36));
        if(old_game_active) g.drawString("Continue old game?", 280, 156);
        g.drawString("Starte Spiel | Gewähltes Level: Level " + loop.getCurrent_level(), 118, start_new_game_y);
        g.drawString("-> Levelauswahl", 304, level_selection_y);
        g.drawString("Settings", 340, settings_y);
        g.drawString("Credits/Quellen",320, credits_y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(old_game_active){
            if(e.getX() >= 104 && e.getX() <= 798 && e.getY() >= 104 && e.getY() <= 180){
                setVisible(false);
                dispose();
                loop.update(continue_game);
            }
        }
        if(e.getX() >= 104 && e.getX() <= 798 && e.getY() >= start_new_game_y - 52 && e.getY() <= start_new_game_y + 24){
            setVisible(false);
            dispose();
            loop.update(loop.getCurrent_level());
        }else if(e.getX() >= 294 && e.getX() <= 598 && e.getY() >= level_selection_y - 52 && e.getY() <= level_selection_y + 24){
            loop.update(level_selection);
            setVisible(false);
            dispose();
        }else if(e.getX() >= 330 && e.getX() <= 598 && e.getY() >= settings_y - 52 && e.getY() <= settings_y + 24){
            loop.update(settings);
            setVisible(false);
            dispose();
        }else if(e.getX() >= 310 && e.getX() <= 598 && e.getY() >= credits_y - 52 && e.getY() <= credits_y + 24){
            new QuellenCredits();
            setVisible(false);
            dispose();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
