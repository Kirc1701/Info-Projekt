package src;

import org.javatuples.Pair;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import src.Level.Level;
import src.Objekte.Baubar.Basis.Basis;
import src.Objekte.Baubar.Building;
import src.Objekte.Monster.Monster;
import src.util.CoordsInt;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static src.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;

//Karte des Spiels
public class Karte {
    //Der Graph der Karte
    private final AbstractBaseGraph<CoordsInt, DefaultWeightedEdge> mapGraph;
    //Liste an Gebäuden
    private final Map<CoordsInt, Building> buildings;
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
    private final CoordsInt spawnpoint;

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
        monsterList = new CopyOnWriteArrayList<>();
        spawnpoint = level.getSpawnPoint();
    }

    public Building addBuilding(CoordsInt coordsInt, Building building) {
        return modifyBuildings(coordsInt, building, true);
    }

    public Building removeBuilding(CoordsInt coordsInt) {
        return modifyBuildings(coordsInt, null, false);
    }

    private Building modifyBuildings(CoordsInt coordsInt, Building building, boolean shouldAdd) {
        if (shouldAdd == buildings.containsKey(coordsInt)) {
            return null;
        }
        if (shouldAdd) {
            buildings.put(coordsInt, building);
        } else {
            building = buildings.remove(coordsInt);
        }
        updateMap();
        return building;
    }

    public void updateMap() {
        for (DefaultWeightedEdge edge : mapGraph.edgeSet()) {
            mapGraph.setEdgeWeight(edge, 1);
        }
        for (CoordsInt building : buildings.keySet()) {
            for (DefaultWeightedEdge edge : mapGraph.edgesOf(building)) {
                mapGraph.setEdgeWeight(edge, 10000);
            }
        }
    }

    // Baut den Graphen einer leeren height*width großen Karte
    private AbstractBaseGraph<CoordsInt, DefaultWeightedEdge> createMap(int height, int width){
        //Neuer leerer Graph
        AbstractBaseGraph<CoordsInt, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        //Erstellung der Nodes
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                CoordsInt newCoordsInt = new CoordsInt(j, i);
//                System.out.println(newCoordsInt.toString());
                graph.addVertex(newCoordsInt);
            }
        }

        //Erstellung der Edges
        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(i < height - 1){
                    DefaultWeightedEdge edge = graph.addEdge(new CoordsInt(j, i), new CoordsInt(j, i + 1));
                    graph.setEdgeWeight(edge, 1);
//                    System.out.println(new CoordsInt(i, j).toString() + " -> " + new CoordsInt(i + 1, j).toString());
                }
                if(j < width - 1){
                    DefaultWeightedEdge edge = graph.addEdge(new CoordsInt(j, i), new CoordsInt(j + 1, i));
                    graph.setEdgeWeight(edge, 1);
//                    System.out.println(new CoordsInt(i, j).toString() + " -> " + new CoordsInt(i, j + 1).toString());
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
    public Rectangle spawnMonster() {
        Monster monster = createAndSetupMonster();
        if(monster.getType().equals("Boss1")){
            Main.stopMusic();
            Main.playMusic(5);
        }
        monster.updateMonsterPath(this);
        return new Rectangle(monster.getPosition().x(), monster.getPosition().y(), spaceBetweenLinesPixels, spaceBetweenLinesPixels);
    }

    private Monster createAndSetupMonster() {
        Monster monster = level.getMonstersToSpawn().removeFirst();
        if(level.spawnAtPoint()) {
            monster.setPosition(spawnpoint);
        }else{
            Pair<CoordsInt, CoordsInt> specificSpawnArea = level.getSpawnArea().get(new Random().nextInt(level.getSpawnArea().size()));
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
            monster.setPosition(new CoordsInt(x, y));
        }
        monsterList.add(monster);
        return monster;
    }

    //Getter- und setter-Methoden
    public AbstractBaseGraph<CoordsInt, DefaultWeightedEdge> getGraphOfMap() {
        return mapGraph;
    }
    public Map<CoordsInt, Building> getBuildings() {
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
