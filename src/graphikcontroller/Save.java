package src.graphikcontroller;

import src.objekte.building.basis.Basis;
import src.objekte.building.Building;
import src.util.CoordsInt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import static src.Main.karte;

public class Save extends JFrame {
    public Save(){
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                        HauptgrafikSpiel.pressed[1] = false;
                        dispose();
                    }
                }
        );
        //Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setBackground(Color.white);
        JTextField textField = new JTextField("", 8);
        JButton acceptText = new JButton("Save Design");
        acceptText.addActionListener(
                e -> {
                    Map<CoordsInt, Building> listToSave = karte.getBuildings();
                    File saveFile = new File("savedDesigns/"+textField.getText()+".txt");
                    try {
                        if(saveFile.createNewFile()){
                            if(saveFile.canWrite()){
                                FileWriter writer = new FileWriter(saveFile, true);
                                toFile(listToSave, writer);
                            }
                        }else{
                            if(saveFile.canWrite()){
                                FileWriter writer = new FileWriter(saveFile, false);
                                toFile(listToSave, writer);
                            }
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    setVisible(false);
                    HauptgrafikSpiel.pressed[1] = false;
                    dispose();
                }
        );
        add(textField);
        add(acceptText);
        setSize(200, 200);
        setLocation(0, 0);
        setLayout(new FlowLayout());
        setVisible(true);
    }
    private void toFile(Map<CoordsInt, Building> listToSave, FileWriter writer) throws IOException {
        for(CoordsInt key : listToSave.keySet()){
            Building building = listToSave.get(key);
            if(!(building instanceof Basis)) {
                writer.write("");
                writer.flush();
                writer
                        .append(key.toString())
                        .append("_")
                        .append(building.getType())
                        .append("_")
                ;
            }
        }
        writer.close();
    }
}
