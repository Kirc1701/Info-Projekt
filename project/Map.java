package project;

import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.ArrayList;

//Karte des Spiels
public class Map {
    //Der Graph der Karte
    private AbstractBaseGraph<Coords, DefaultWeightedEdge> graphOfMap;
    //Liste an Gebäuden
    private ArrayList<Coords> buildings;
    
    public Map(int length, int width){
        //Initialisiert den Graphen und die Liste der Gebäude
        graphOfMap = createMap(length, width);
        buildings = new ArrayList<>();
    }

    //Methode um Gebäude sicher zur Liste hinzuzufügen
    public boolean addBuilding(Coords coords){
        //Wenn Building bereits in der Liste enthalten ist return false
        if(buildings.contains(coords)){
            return false;
        }
        //füge Building zur Liste hinzu
        buildings.add(coords);
        //Aktualisiere die Karte
        updateMap();
        //Hat alles funktioniert gib true zurück
        return true;
    }

    //Methode um gebäude sicher aus der Liste zu entfernen
    public boolean removeBuilding(Coords coords){
        //Wenn Building nicht in der Liste enthalten ist, return false
        if(!buildings.contains(coords)){
            return false;
        }
        //entferne building aus der Liste
        buildings.remove(coords);
        //Aktualisiere die Karte
        updateMap();
        //Hat alles funktioniert gib true zurück
        return true;
    }

    //Methode zum Updaten der Map
    public void updateMap(){
        //Rücksetzung der Edges
        for(DefaultWeightedEdge edge : graphOfMap.edgeSet()){
            graphOfMap.setEdgeWeight(edge, 1);
        }
        //Edges an Gebäuden kriegen hohe Weights
        for(Coords building : buildings){
            for(DefaultWeightedEdge edge : graphOfMap.edgesOf(building)){
                graphOfMap.setEdgeWeight(edge, 10000);
            }
        }
    }


    //Getter- und setter-Methoden
    public AbstractBaseGraph<Coords, DefaultWeightedEdge> getGraphOfMap() {
        return graphOfMap;
    }
    public ArrayList<Coords> getBuildings() {
        return buildings;
    }
    public void setBuildings(ArrayList<Coords> buildings) {
        this.buildings = buildings;
    }
    public void setGraphOfMap(AbstractBaseGraph<Coords, DefaultWeightedEdge> graphOfMap) {
        this.graphOfMap = graphOfMap;
    }

    // Baut den Graphen einer leeren length*width großen Map
    private AbstractBaseGraph<Coords, DefaultWeightedEdge> createMap(int length, int width){
        //Neuer leerer Graph
        AbstractBaseGraph<Coords, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        //Erstellung der Nodes
        for(int i = 0; i < length; i++){
            for(int j = 0; j < width; j++){
                Coords newCoords = new Coords(i, j);
//                System.out.println(newCoords.toString());
                graph.addVertex(newCoords);
            }
        }

        //Erstellung der Edges
        for(int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if(i < length - 1){
                    DefaultWeightedEdge edge = graph.addEdge(new Coords(i, j), new Coords(i + 1, j));
                    graph.setEdgeWeight(edge, 1);
//                    System.out.println(new Coords(i, j).toString() + " -> " + new Coords(i + 1, j).toString());
                }
                if(j < width - 1){
                    DefaultWeightedEdge edge = graph.addEdge(new Coords(i, j), new Coords(i, j + 1));
                    graph.setEdgeWeight(edge, 1);
//                    System.out.println(new Coords(i, j).toString() + " -> " + new Coords(i, j + 1).toString());
                }
            }
        }

        //Rückgabe des Graphen
        return graph;
    }
}
