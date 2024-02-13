package project.Objekte.Monster;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import project.Coords;
import project.Karte;

import java.awt.*;
import java.util.List;

import static project.Graphikcontroller.HauptgrafikSpiel.space;

public class DefaultMonster extends Monster{
    public DefaultMonster(Coords position) {
        super(10, 20, position, 2, 4);
    }

    @Override
    public Rectangle makeMove(Karte karte) {
        DijkstraShortestPath<Coords, DefaultWeightedEdge> pathfinder = new DijkstraShortestPath<>(karte.getGraphOfMap());
        GraphPath<Coords, DefaultWeightedEdge> path = pathfinder.getPath(position, karte.getBasis().getPosition());
        List<Coords> pathNodes = path.getVertexList();
        double weight = path.getWeight();
        Coords nextPosition = pathNodes.get(1);
        if(weight >= 10000 && karte.getBuildings().containsKey(nextPosition)){
            attack(karte.getBuildings().get(nextPosition));
            return new Rectangle(nextPosition.getX(), nextPosition.getY(), space, space);
        }else {
            Coords oldPosition = position;
            if (pathNodes.isEmpty()) {
                System.out.println("Something went seriously wrong.");
            } else {
                System.out.println(path);
                schritteBisZiel = pathNodes.size() - 2;
                System.out.println(schritteBisZiel);
                position = nextPosition;
            }
            //-1 if monster moves left; 0 if no movement on the x-axis happens; 1 if monster moves right
            int directionX = position.getX() - oldPosition.getX();
            //-1 if monster moves up; 0 if no movement on the y-axis happens; 1 if monster moves down
            int directionY = position.getY() - oldPosition.getY();
            int x;
            int y;
            int width = space;
            int height = space;
            if (directionX < 0) {
                x = oldPosition.getX() + directionX;
                width = width - directionX * space;
            } else {
                x = oldPosition.getX();
                width = width + directionX * space;
            }
            if (directionY < 0) {
                y = oldPosition.getY() + directionY;
                height = height - directionY * space;
            } else {
                y = oldPosition.getY();
                height = height + directionY * space;
            }
            return new Rectangle(x, y, width, height);
        }
    }
}
