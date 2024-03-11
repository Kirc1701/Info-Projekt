package src.Graphikcontroller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LevelAuswahl extends JFrame {
    public LevelAuswahl(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        dispose();
                        System.exit(0);
                    }
                }
        );
        int levelWidth = 60;
        int levelHeight = 60;
        int margin = 10;
        int width = 700;
        int height = 700;
        int systemWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int systemHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int leftCornerUpX = systemWidth / 2 - (width / 2);
        int leftCornerUpY = systemHeight / 2 - (height / 2);

        int level1CornerX = 10 + margin;
        int level1CornerY = 10 + margin;
        JButton level1 = new JButton("Level 1");
        level1.setBounds(level1CornerX, level1CornerY, levelWidth, levelHeight);
        level1.addActionListener(
                e -> {
                    Hauptmenue.chosenLevel = 1;
                    new Hauptmenue();
                    setVisible(false);
                    dispose();
                }
        );

        int level2CornerX = level1CornerX + levelWidth + margin;
        JButton level2 = new JButton("Level 2");
        level2.setBounds(level2CornerX, level1CornerY, levelWidth, levelHeight);
        level2.addActionListener(
                e -> {
                    Hauptmenue.chosenLevel = 2;
                    new Hauptmenue();
                    setVisible(false);
                    dispose();
                }
        );

        int level3CornerX = level2CornerX + levelWidth + margin;
        JButton level3 = new JButton("Level 3");
        level3.setBounds(level3CornerX, level1CornerY, levelWidth, levelHeight);
        level3.addActionListener(
                e -> {
                    Hauptmenue.chosenLevel = 3;
                    new Hauptmenue();
                    setVisible(false);
                    dispose();
                }
        );

        int level4CornerX = level3CornerX + levelWidth + margin;
        JButton level4 = new JButton("Level 4");
        level4.setBounds(level4CornerX, level1CornerY, levelWidth, levelHeight);
        level4.addActionListener(
                e -> {
                    Hauptmenue.chosenLevel = 4;
                    new Hauptmenue();
                    setVisible(false);
                    dispose();
                }
        );

        int level5CornerX = level4CornerX + levelWidth + margin;
        JButton level5 = new JButton("Level 5");
        level5.setBounds(level5CornerX, level1CornerY, levelWidth, levelHeight);
        level5.addActionListener(
                e -> {
                    Hauptmenue.chosenLevel = 5;
                    new Hauptmenue();
                    setVisible(false);
                    dispose();
                }
        );

        add(level1);
        add(level2);
        add(level3);
        add(level4);
        add(level5);

        setBackground(Color.white);
        setSize(width, height);
        setLocation(
                leftCornerUpX, leftCornerUpY
        );
        setLayout(null);
        setVisible(true);
    }
}
