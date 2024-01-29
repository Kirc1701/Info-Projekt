package project;

import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.HashMap;
import java.util.Map;

//Karte des Spiels
public class Karte {
    //Der Graph der Karte
    private AbstractBaseGraph<Coords, DefaultWeightedEdge> graphOfMap;
    //Liste an Gebäuden
    private Map<Coords, String> buildings;
    //Width and Height
    private int width;
    private int height;

    public Karte(int height, int width){
        //Initialisiert den Graphen und die Liste der Gebäude
        this.width = width;
        this.height = height;
        graphOfMap = createMap(height, width);
        buildings = new HashMap<>();
    }

    //Methode um Gebäude sicher zur Liste hinzuzufügen
    public boolean addBuilding(Coords coords, String name){
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
    public Map<Coords, String> getBuildings() {
        return buildings;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public void setBuildings(Map<Coords, String> buildings) {
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
}
