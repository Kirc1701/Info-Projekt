package src.Objekte.Monster;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import src.Coords;
import src.Karte;

import java.awt.*;

import static src.Graphikcontroller.HauptgrafikSpiel.spaceBetweenLinesPixels;

public class DefaultMonster extends Monster{
    public DefaultMonster(Coords position) {
        super(10, 20, position, 2, 4, 10, "Default");
    }

    @Override
    public Rectangle makeMove(Karte karte) {
        Coords nextPosition = monsterPathNodes.get(1);
        if(monsterPathWeight >= 10000 && karte.getBuildings().containsKey(nextPosition)){
            attack(karte.getBuildings().get(nextPosition));
            return new Rectangle(nextPosition.getX(), nextPosition.getY(), spaceBetweenLinesPixels, spaceBetweenLinesPixels);
        }else {
            Coords oldPosition = position;
            schritteBisZiel = monsterPathNodes.size() - 2;
            position = nextPosition;
            monsterPathNodes.remove(1);
            //-1 if monster moves left; 0 if no movement on the x-axis happens; 1 if monster moves right
            int directionX = position.getX() - oldPosition.getX();
            //-1 if monster moves up; 0 if no movement on the y-axis happens; 1 if monster moves down
            int directionY = position.getY() - oldPosition.getY();
            int x;
            int y;
            int width = spaceBetweenLinesPixels;
            int height = spaceBetweenLinesPixels;
            if (directionX < 0) {
                x = oldPosition.getX() + directionX;
                width = width - directionX * spaceBetweenLinesPixels;
            } else {
                x = oldPosition.getX();
                width = width + directionX * spaceBetweenLinesPixels;
            }
            if (directionY < 0) {
                y = oldPosition.getY() + directionY;
                height = height - directionY * spaceBetweenLinesPixels;
            } else {
                y = oldPosition.getY();
                height = height + directionY * spaceBetweenLinesPixels;
            }
            return new Rectangle(x, y, width, height);
        }
    }

    @Override
    public void updateMonsterPath(Karte karte){
        DijkstraShortestPath<Coords, DefaultWeightedEdge> pathfinder = new DijkstraShortestPath<>(karte.getGraphOfMap());
        GraphPath<Coords, DefaultWeightedEdge> monsterPath = pathfinder.getPath(position, karte.getBasis().getPosition());
        monsterPathWeight = monsterPath.getWeight();
        monsterPathNodes = monsterPath.getVertexList();
    }
}
