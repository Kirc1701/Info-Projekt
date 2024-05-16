package src.visuals;

// Import anderer Klassen innerhalb des Projekts

import src.Main;
import src.drawables.objects.buildings.Building;
import src.util.CoordsInt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;

import static src.Main.logicRepresentation;
import static src.visuals.GameScreen.pressed;
import static src.visuals.GameScreen.spaceBetweenLinesPixels;

// Ein Fenster mit dem der User ein Gebäude entfernen kann
public class RemoveBuildingPopUp extends JFrame implements ActionListener {
    // Koordinaten des Gebäudes
    private final int targetXCoordinate;
    private final int targetYCoordinate;

    // Konstruktor des Frames + Initialisierung
    // targetXCoordinate: x-Koordinate des Gebäudes NICHT in Pixeln
    // targetYCoordinate: y-Koordinate des Gebäudes NICHT in Pixeln
    // locationX: x-Koordinate der Mausposition in Pixeln
    // locationY: y-Koordinate der Mausposition in Pixeln
    public RemoveBuildingPopUp(int targetXCoordinate, int targetYCoordinate, int mouseLocationX, int mouseLocationY) throws IOException {
        System.out.println("PopupRemove started");
        // Übergabe der Gebäudekoordinaten
        this.targetXCoordinate = targetXCoordinate;
        this.targetYCoordinate = targetYCoordinate;

        // Laden der Bilddatei in die Variable trashcanImage
        Image trashcanImage = ImageIO.read(Objects.requireNonNull(RemoveBuildingPopUp.class.getClassLoader().getResourceAsStream("images/Trashcan.png"))).getScaledInstance(spaceBetweenLinesPixels - 2, spaceBetweenLinesPixels - 2, Image.SCALE_SMOOTH);

        // WindowListener, der bei Betätigung des Schließen-Buttons das Popup beendet
        addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        // Popup wird nicht mehr dargestellt
                        setVisible(false);
                        // Es kann ein neues Popup erzeugt werden
                        pressed[0] = false;
                        // Alle weiteren Prozesse im Popup werden unterbrochen
                        dispose();
                    }
                }
        );
        // Erstellung der Buttons
        // Erstellung des TrashButtons, der aus einer skalierten Version des trashcanImage besteht
        JButton trashButton = new JButton(new ImageIcon(trashcanImage));
        // Wird der TrashButton gedrückt, gibt er als ActionCommand "Delete" zurück
        // und die Methode actionPerformed() wird aufgerufen
        trashButton.setActionCommand("Delete");
        trashButton.addActionListener(this);

        // Erstellung des CancelButtons, der mit "Cancel" beschriftet ist
        JButton cancelButton = new JButton("Cancel");
        // Wird der CancelButton gedrückt, gibt er als ActionCommand "" zurück
        // und die Methode actionPerformed() wird aufgerufen
        cancelButton.setActionCommand("");
        cancelButton.addActionListener(this);

        // Hinzufügen der Buttons zum Frame
        add(trashButton);
        add(cancelButton);

        // Grundlegende Initialisierung des Fensters, anschließende Darstellung des Fensters
        setBackground(Color.white);
        setSize(150, 100);
        // Positionierung des Popups an der Stelle, an der gedrückt wurde.
        setLocation(mouseLocationX - 75, mouseLocationY - 50);
        setLayout(new FlowLayout());
        setVisible(true);
    }

    // actionPerformed()-Methode wird aufgerufen, wenn einer der beiden Buttons gedrückt wurde
    @Override
    public void actionPerformed(ActionEvent e1) {
        // Wenn der TrashButton gedrückt wurde, wird dieser Teil ausgeführt
        if (e1.getActionCommand().equals("Delete")) {
            // Das Gebäude wird von der LogicRepresentation entfernt.
            // Sollte hierbei irgendetwas schiefgehen, wie dass das Gebäude nicht vorhanden ist,
            // wird eine Fehlermeldung in die Konsole ausgegeben
            Building building = logicRepresentation.getBuildings().get(new CoordsInt(targetXCoordinate, targetYCoordinate));
            if(building == null){
                System.out.println("Something went wrong");
            }
            assert building != null;
            building.die();
            Main.money += building.getKosten();
        }
        setVisible(false);
        pressed[0] = false;
        dispose();
    }
}
