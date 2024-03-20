package src.objekte.monster;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;
import src.Direction;
import src.Karte;
import src.Main;
import src.objekte.Objekt;
import src.Tickable;
import src.util.CoordsDouble;
import src.util.CoordsInt;

import java.util.List;

import static src.Main.karte;
import static src.Main.playSFX;
import static src.util.Math.getDirectionDifference;

public abstract class Monster extends Objekt implements Tickable {
    protected final float movingSpeed;
    @SuppressWarnings("CanBeFinal")
    protected int attackSpeed;
    protected int schritteBisZiel;
    protected final double kopfgeld;
    protected double monsterPathWeight;
    protected List<CoordsInt> monsterPathNodes;
    protected CoordsDouble drawnPosition;
    protected double attackCooldown = attackSpeed;
    protected boolean flying;

    public Monster(int strength, int health, CoordsInt position, float movingSpeed, int attackSpeed, double kopfgeld, String type, boolean flying){
        super(strength, health, position, type);
        this.attackSpeed = attackSpeed;
        this.movingSpeed = movingSpeed;
        this.kopfgeld = kopfgeld;
        schritteBisZiel = 250;
        this.drawnPosition = position.toCoordsDouble();
        this.flying = flying;
        Main.registerDrawable(this);
        Main.registerTickable(this);
    }

    public void updateFlyingMonsterPath(Karte karte){
        SimpleWeightedGraph<CoordsInt, DefaultWeightedEdge> graph = karte.getGraphOfMap();
        for (DefaultWeightedEdge edge : graph.edgeSet()){
            graph.setEdgeWeight(edge, 1);
        }
        CoordsInt positionBasis = karte.getBasis().getPosition();
        for (DefaultWeightedEdge edge : graph.edgesOf(positionBasis)){
            graph.setEdgeWeight(edge, 10000);
        }
        DijkstraShortestPath<CoordsInt, DefaultWeightedEdge> pathfinder = new DijkstraShortestPath<>(graph);
        GraphPath<CoordsInt, DefaultWeightedEdge> monsterPath = pathfinder.getPath(position, karte.getBasis().getPosition());
        monsterPathWeight = monsterPath.getWeight();
        monsterPathNodes = monsterPath.getVertexList();
    }

    public void updateWalkingMonsterPath(Karte karte){
        DijkstraShortestPath<CoordsInt, DefaultWeightedEdge> pathfinder = new DijkstraShortestPath<>(karte.getGraphOfMap());
        GraphPath<CoordsInt, DefaultWeightedEdge> monsterPath = pathfinder.getPath(position, karte.getBasis().getPosition());
        monsterPathWeight = monsterPath.getWeight();
        monsterPathNodes = monsterPath.getVertexList();
    }

    public void tick(double timeDelta, Karte karte) {
        if(position.equals(new CoordsInt(-1, -1))) return;
        moveTowardsPosition(timeDelta, karte);
    }

    public void moveTowardsPosition(double timeDelta, Karte karte) {
        Direction directionToMove = getDirectionDifference(drawnPosition, position.toCoordsDouble());

        if (directionToMove == null) {
            makeMove(timeDelta, karte);
            return;
        }

        CoordsDouble neededShiftage = position.toCoordsDouble().add(drawnPosition.scale(-1));
        CoordsDouble moved = CoordsDouble.getNormalized(directionToMove).scale((float) timeDelta * movingSpeed);
        CoordsDouble leftShiftage = neededShiftage.add(moved.scale(-1));
        if (CoordsDouble.getNormalized(directionToMove).min(new CoordsDouble(0, 0)).equals(new CoordsDouble(0, 0))) {
            if (leftShiftage.max(new CoordsDouble(0, 0)).equals(new CoordsDouble(0, 0))) {
                this.drawnPosition = position.toCoordsDouble();
                return;
            }
        } else {
            if (leftShiftage.min(new CoordsDouble(0, 0)).equals(new CoordsDouble(0, 0))) {
                this.drawnPosition = position.toCoordsDouble();
                return;
            }
        }

        this.drawnPosition = drawnPosition.add(moved);
    }

    public void makeMove(double timeDelta, Karte karte){
        CoordsInt nextPosition = monsterPathNodes.get(1);
        if (!flying) {
            if (attackingWalking(karte)) {
                attack(timeDelta, karte.getBuildings().get(nextPosition));
            } else {
                schritteBisZiel = monsterPathNodes.size() - 2;
                position = nextPosition;
            }
        } else {
            if (attackingFlying(karte)) {
                attack(timeDelta, karte.getBuildings().get(nextPosition));
            } else {
                schritteBisZiel = monsterPathNodes.size() - 2;
                position = nextPosition;
            }
        }
    }

    public boolean attackingWalking(Karte karte){
        return monsterPathWeight >= 10000 && karte.getBuildings().containsKey(monsterPathNodes.get(1));
    }
    public boolean attackingFlying(Karte karte){
        return monsterPathNodes.get(1).equals(karte.getBasis().getPosition());
    }

    public abstract void updateMonsterPath(Karte karte);

    public void attack(double timeDelta, Objekt objekt){
        attackCooldown -= timeDelta;
        if(attackCooldown <= 0) {
            objekt.setHealth(objekt.getHealth() - strength);
            if (objekt.getType().equals("DefaultBasis")) {
                playSFX(10);
            }
            attackCooldown = attackSpeed;
        }
    }

    public int getSchritteBisZiel() {
        return schritteBisZiel;
    }

    public Direction getDirection(){
        Direction direction = getDirectionDifference((monsterPathNodes.get(1).equals(position) ? position : monsterPathNodes.get(1)).toCoordsDouble(), drawnPosition);
        if(direction == null){
            monsterPathNodes.remove(1);
            return getDirection();
        }
        return direction;
    }

    public CoordsDouble getDrawnPosition(){
        return drawnPosition;
    }

    public float getOpacity() {
        return 1;
    }

    public boolean hasArrived(){
        return position.equals(drawnPosition);
    }

    @Override
    public void die() {
        super.die();
        Main.unregisterTickable(this);
        Main.money += kopfgeld;
        if (type.equals("Boss1")) {
            playSFX(9);
        } else playSFX(1);

        if (karte.getMonsterList().isEmpty() && karte.getLevel().getMonstersToSpawn().isEmpty()) {
            Main.onGameOver();
        }
    }

    @Override
    public void setPosition(CoordsInt position) {
        super.setPosition(position);
        this.drawnPosition = position.toCoordsDouble();
    }
}
