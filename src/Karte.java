package src;

import org.javatuples.Pair;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import src.Level.Level;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Baubar.Baubar;
import src.Objekte.Monster.Monster;

import java.awt.*;
import java.util.*;
import java.util.List;

import static src.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;

//Karte des Spiels
public class Karte {
    //Der Graph der Karte
    private final AbstractBaseGraph<Coords, DefaultWeightedEdge> mapGraph;
    //Liste an Gebäuden
    private final Map<Coords, Baubar> buildings;
    //Width and Height
    private final int width;
    private final int height;
    //Basis
    private final Basis basis;
    //Liste aller existierenden Monster
    private final List<Monster> monsterList;
    //aktuelles Level
    private final Level level;
    //spawnpoint
    private final Coords spawnpoint;

    public Karte(Level level){
        //Initialisiert den Graphen und die Liste der Gebäude
        this.level = level;
        width = level.getWidth();
        height = level.getHeight();
        mapGraph = createMap(height, width);
        buildings = new HashMap<>();
        basis = level.getBasis();
        basis.setPosition(level.getBasisPosition());
        addBuilding(basis.getPosition(), basis);
        monsterList = new ArrayList<>();
        spawnpoint = level.getSpawnPoint();
    }

    public Baubar addBuilding(Coords coords, Baubar building) {
        return modifyBuildings(coords, building, true);
    }

    public Baubar removeBuilding(Coords coords) {
        return modifyBuildings(coords, null, false);
    }

    private Baubar modifyBuildings(Coords coords, Baubar building, boolean shouldAdd) {
        if (shouldAdd == buildings.containsKey(coords)) {
            return null;
        }
        if (shouldAdd) {
            buildings.put(coords, building);
        } else {
            building = buildings.remove(coords);
        }
        updateMap();
        return building;
    }

    public void updateMap() {
        for (DefaultWeightedEdge edge : mapGraph.edgeSet()) {
            mapGraph.setEdgeWeight(edge, 1);
        }
        for (Coords building : buildings.keySet()) {
            for (DefaultWeightedEdge edge : mapGraph.edgesOf(building)) {
                mapGraph.setEdgeWeight(edge, 10000);
            }
        }
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

    // Der Spieler hat gewonnen?
    public boolean playerWins() {
        return level.getMonstersToSpawn().isEmpty() && monsterList.isEmpty();
    }

    // Das Spiel ist vorbei?
    public boolean gameOver() {
        return playerWins() || basis.getHealth() <= 0;
    }

    // Es wird ein Monster gespawnt
    public Rectangle spawnMonster(int time) {
        Monster monster = createAndSetupMonster(time);
        monster.updateMonsterPath(this);
        return new Rectangle(monster.getPosition().x(), monster.getPosition().y(), spaceBetweenLinesPixels, spaceBetweenLinesPixels);
    }

    private Monster createAndSetupMonster(int time) {
        Monster monster = level.getMonstersToSpawn().remove(0);
        if(level.spawnAtPoint()) {
            monster.setPosition(spawnpoint);
        }else{
            Pair<Coords, Coords> specificSpawnArea = level.getSpawnArea().get(new Random().nextInt(level.getSpawnArea().size()));
            int x;
            int y;
            try {
                x = new Random().nextInt(specificSpawnArea.getValue0().x(), specificSpawnArea.getValue1().x());
            }catch(IllegalArgumentException e){
                x = specificSpawnArea.getValue1().x();
            }
            try {
                y = new Random().nextInt(specificSpawnArea.getValue0().y(), specificSpawnArea.getValue1().y());
            } catch(IllegalArgumentException e){
                y = specificSpawnArea.getValue0().y();
            }
            monster.setPosition(new Coords(x, y));
        }
        monster.setSpawntime(time);
        monsterList.add(monster);
        return monster;
    }

    //Getter- und setter-Methoden
    public AbstractBaseGraph<Coords, DefaultWeightedEdge> getGraphOfMap() {
        return mapGraph;
    }
    public Map<Coords, Baubar> getBuildings() {
        return buildings;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Basis getBasis() {
        return basis;
    }
    public List<Monster> getMonsterList() {
        return monsterList;
    }
    public Level getLevel() {
        return level;
    }
}
