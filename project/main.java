package project;

public class Main {
    public static void main(String[] args) {
        Karte karte = new Karte(25, 40);
        karte.addBuilding(new Coords(39, 12), "Basis");
        Graphic.showMap(karte);
    }
}
