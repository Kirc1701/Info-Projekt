package project;

import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import project.Level.Level;
import project.Level.Level1;
import project.Objekte.Basis.Basis;
import project.Objekte.Basis.DefaultBasis;
import project.Objekte.Monster.Monster;
import project.Objekte.Objekt;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static project.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;

//Karte des Spiels
public class Karte {
    //Der Graph der Karte
    private AbstractBaseGraph<Coords, DefaultWeightedEdge> graphOfMap;
    //Liste an Gebäuden
    private Map<Coords, Objekt> buildings;
    //Width and Height
    private int width;
    private int height;
    //Basis
    private Basis basis;
    //Liste aller existierenden Monster
    private List<Monster> monsterList;
    //aktuelles Level
    private Level level;
    //spawnpoint
    private final Coords spawnpoint;

    public Karte(int height, int width, Coords basisPosition, Coords spawnpoint){
        //Initialisiert den Graphen und die Liste der Gebäude
        this.width = width;
        this.height = height;
        graphOfMap = createMap(height, width);
        buildings = new HashMap<>();
        basis = new DefaultBasis(basisPosition);
        addBuilding(basisPosition, basis);
        monsterList = new ArrayList<>();
        level = new Level1();
        this.spawnpoint = spawnpoint;
    }

    //Methode um Gebäude sicher zur Liste hinzuzufügen
    public boolean addBuilding(Coords coords, Objekt name){
        //Wenn Building bereits in der Liste enthalten ist return false
        if(buildings.containsKey(coords)){
            return false;
        }
        //füge Building zur Liste hinzu
        buildings.put(coords, name);
        //Aktualisiere die Karte
        updateMap();
        //Hat alles funktioniert gib true zurück
        return true;
    }

    //Methode um gebäude sicher aus der Liste zu entfernen
    public boolean removeBuilding(Coords coords){
        //Wenn Building nicht in der Liste enthalten ist, return false
        if(!buildings.containsKey(coords)){
            return false;
        }
        //entferne building aus der Liste
        buildings.remove(coords);
        //Aktualisiere die Karte
        updateMap();
        //Hat alles funktioniert gib true zurück
        return true;
    }

    //Methode zum Updaten der Karte
    public void updateMap(){
        //Rücksetzung der Edges
        for(DefaultWeightedEdge edge : graphOfMap.edgeSet()){
            graphOfMap.setEdgeWeight(edge, 1);
        }
        //Edges an Gebäuden kriegen hohe Weights
        for(Coords building : buildings.keySet()){
            for(DefaultWeightedEdge edge : graphOfMap.edgesOf(building)){
                graphOfMap.setEdgeWeight(edge, 10000);
            }
        }
    }


    //Getter- und setter-Methoden
    public AbstractBaseGraph<Coords, DefaultWeightedEdge> getGraphOfMap() {
        return graphOfMap;
    }
    public Map<Coords, Objekt> getBuildings() {
        return buildings;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setBuildings(Map<Coords, Objekt> buildings) {
        this.buildings = buildings;
    }
    public void setGraphOfMap(AbstractBaseGraph<Coords, DefaultWeightedEdge> graphOfMap) {
        this.graphOfMap = graphOfMap;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    // Baut den Graphen einer leeren height*width großen Karte
    private AbstractBaseGraph<Coords, DefaultWeightedEdge> createMap(int height, int width){
        //Neuer leerer Graph
        AbstractBaseGraph<Coords, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        //Erstellung der Nodes
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Coords newCoords = new Coords(j, i);
//                System.out.println(newCoords.toString());
                graph.addVertex(newCoords);
            }
        }

        //Erstellung der Edges
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(i < height - 1){
                    DefaultWeightedEdge edge = graph.addEdge(new Coords(j, i), new Coords(j, i + 1));
                    graph.setEdgeWeight(edge, 1);
//                    System.out.println(new Coords(i, j).toString() + " -> " + new Coords(i + 1, j).toString());
                }
                if(j < width - 1){
                    DefaultWeightedEdge edge = graph.addEdge(new Coords(j, i), new Coords(j + 1, i));
                    graph.setEdgeWeight(edge, 1);
//                    System.out.println(new Coords(i, j).toString() + " -> " + new Coords(i, j + 1).toString());
                }
            }
        }

        //Rückgabe des Graphen
        return graph;
    }

    public Basis getBasis() {
        return basis;
    }

    public void setBasis(Basis basis) {
        this.basis = basis;
    }

    public List<Monster> getMonsterList() {
        return monsterList;
    }

    public void setMonsterList(List<Monster> monsterList) {
        this.monsterList = monsterList;
    }

    public boolean playerWins() {
        return level.getMonstersToSpawn().isEmpty() && monsterList.isEmpty();
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean gameOver() {
        return playerWins() || basis.getHealth() <= 0;
    }

    public Rectangle spawnMonster() {
        Monster monster = level.getMonstersToSpawn().remove(0);
        monster.setPosition(spawnpoint);
        monsterList.add(monster);
        return new Rectangle(monster.getPosition().getX(), monster.getPosition().getY(), spaceBetweenLinesPixels, spaceBetweenLinesPixels);
    }
}
