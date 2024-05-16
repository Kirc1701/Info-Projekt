package src.drawables.objects.monster;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import src.Direction;
import src.LogicRepresentation;
import src.Main;
import src.Tickable;
import src.drawables.objects.Object;
import src.util.CoordsDouble;
import src.util.CoordsInt;

import java.util.List;

import static src.Main.logicRepresentation;
import static src.Main.playSFX;
import static src.util.Math.getDirectionDifference;

public abstract class Monster extends Object implements Tickable {
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

    public void updateFlyingMonsterPath(LogicRepresentation logicRepresentation) {
        SimpleWeightedGraph<CoordsInt, DefaultWeightedEdge> graph = logicRepresentation.getGraphOfMap();
        for (DefaultWeightedEdge edge : graph.edgeSet()){
            graph.setEdgeWeight(edge, 1);
        }
        CoordsInt positionBasis = logicRepresentation.getBasis().getPosition();
        for (DefaultWeightedEdge edge : graph.edgesOf(positionBasis)){
            graph.setEdgeWeight(edge, 10000);
        }
        DijkstraShortestPath<CoordsInt, DefaultWeightedEdge> pathfinder = new DijkstraShortestPath<>(graph);
        GraphPath<CoordsInt, DefaultWeightedEdge> monsterPath = pathfinder.getPath(position, logicRepresentation.getBasis().getPosition());
        monsterPathWeight = monsterPath.getWeight();
        monsterPathNodes = monsterPath.getVertexList();
    }

    public void updateWalkingMonsterPath(LogicRepresentation logicRepresentation) {
        DijkstraShortestPath<CoordsInt, DefaultWeightedEdge> pathfinder = new DijkstraShortestPath<>(logicRepresentation.getGraphOfMap());
        GraphPath<CoordsInt, DefaultWeightedEdge> monsterPath = pathfinder.getPath(position, logicRepresentation.getBasis().getPosition());
        monsterPathWeight = monsterPath.getWeight();
        monsterPathNodes = monsterPath.getVertexList();
    }

    public void tick(double timeDelta, LogicRepresentation logicRepresentation) {
        if(position.equals(new CoordsInt(-1, -1))) return;
        moveTowardsPosition(timeDelta, logicRepresentation);
    }

    public void moveTowardsPosition(double timeDelta, LogicRepresentation logicRepresentation) {
        Direction directionToMove = getDirectionDifference(drawnPosition, position.toCoordsDouble());

        if (directionToMove == null) {
            makeMove(timeDelta, logicRepresentation);
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

    public void makeMove(double timeDelta, LogicRepresentation logicRepresentation) {
        CoordsInt nextPosition = monsterPathNodes.get(1);
        if (!flying) {
            if (attackingWalking(logicRepresentation)) {
                attack(timeDelta, logicRepresentation.getBuildings().get(nextPosition));
            } else {
                schritteBisZiel = monsterPathNodes.size() - 2;
                position = nextPosition;
            }
        } else {
            if (attackingFlying(logicRepresentation)) {
                attack(timeDelta, logicRepresentation.getBuildings().get(nextPosition));
            } else {
                schritteBisZiel = monsterPathNodes.size() - 2;
                position = nextPosition;
            }
        }
    }

    public boolean attackingWalking(LogicRepresentation logicRepresentation) {
        return monsterPathWeight >= 10000 && logicRepresentation.getBuildings().containsKey(monsterPathNodes.get(1));
    }

    public boolean attackingFlying(LogicRepresentation logicRepresentation) {
        return monsterPathNodes.get(1).equals(logicRepresentation.getBasis().getPosition());
    }

    public abstract void updateMonsterPath(LogicRepresentation logicRepresentation);

    public void attack(double timeDelta, Object object) {
        attackCooldown -= timeDelta;
        if(attackCooldown <= 0) {
            object.setHealth(object.getHealth() - strength);
            if (object.getType().equals("DefaultBasis")) {
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

        if (logicRepresentation.getMonsterList().isEmpty() && logicRepresentation.getLevel().getMonstersToSpawn().isEmpty()) {
            Main.onGameOver();
        }
    }

    @Override
    public void setPosition(CoordsInt position) {
        super.setPosition(position);
        this.drawnPosition = position.toCoordsDouble();
    }
}
