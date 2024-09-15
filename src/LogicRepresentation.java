package src;

import lombok.Getter;
import org.javatuples.Pair;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import src.drawables.objects.ObjectType;
import src.drawables.objects.buildings.Building;
import src.drawables.objects.buildings.basis.Basis;
import src.drawables.objects.monster.Monster;
import src.level.Level;
import src.util.CoordsInt;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static src.Main.loop;
import static src.util.SoundUtils.playMusic;
import static src.util.SoundUtils.stopMusic;

//LogicRepresentation des Spiels
public class LogicRepresentation implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Getter
    private final SimpleWeightedGraph<CoordsInt, DefaultWeightedEdge> mapGraph;
    //Liste an Gebäuden
    @Getter
    private final Map<CoordsInt, Building> buildings;
    //Width and Height
    @Getter
    private final int width;
    @Getter
    private final int height;
    //basis
    @Getter
    private final Basis basis;
    //Liste aller existierenden monster
    @Getter
    private final List<Monster> monsterList;
    private final List<Monster> monstersToSpawn;
    //aktuelles level
    @Getter
    private final Level level;
    //spawnpoint
    private final CoordsInt spawnpoint;

    public LogicRepresentation(Level level) {
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
        monstersToSpawn = level.getMonstersToSpawn();
        spawnpoint = level.getSpawnPoint();
    }

    public void addBuilding(CoordsInt coordsInt, Building building) {
        modifyBuildings(coordsInt, building, true);
    }

    public void removeBuilding(CoordsInt coordsInt) {
        modifyBuildings(coordsInt, null, false);
    }

    private void modifyBuildings(CoordsInt coordsInt, Building building, boolean shouldAdd) {
        if (shouldAdd == buildings.containsKey(coordsInt)) {
            return;
        }
        if (shouldAdd) {
            buildings.put(coordsInt, building);
        } else {
            buildings.remove(coordsInt);
        }
        updateMap();
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

    // Baut den Graphen einer leeren height*width großen LogicRepresentation
    private SimpleWeightedGraph<CoordsInt, DefaultWeightedEdge> createMap(int height, int width){
        //Neuer leerer Graph
        SimpleWeightedGraph<CoordsInt, DefaultWeightedEdge> graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

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

    // Es wird ein monster gespawnt
    public void spawnMonster() {
        Monster monster = createAndSetupMonster();
        if(monster.getType().equals(ObjectType.Boss1)){
            stopMusic();
            playMusic(5);
        }
        monster.updateMonsterPath(this);
    }

    private Monster createAndSetupMonster() {
        Monster monster = monstersToSpawn.removeFirst();
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

    public void addToDrawablesAndTickables(){
        for(Building building : buildings.values()){
            if(building.getType().equals(ObjectType.DefaultTower)||building.getType().equals(ObjectType.Sniper) || building.getType().equals(ObjectType.Minigun)){
                loop.registerTickable((Tickable) building);
            }
            loop.registerDrawable(building);
        }
        for(Monster monster : monsterList){
            loop.registerDrawable(monster);
            loop.registerTickable(monster);
        }
        for(Monster monster : monstersToSpawn){
            loop.registerDrawable(monster);
            loop.registerTickable(monster);
        }
    }

    public Building getBuilding(CoordsInt position) {
        return buildings.getOrDefault(position, null);
    }
}
