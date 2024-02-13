package project.Objekte.Monster;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import project.Coords;
import project.Karte;

import java.awt.*;
import java.util.List;

import static project.Graphikcontroller.HauptgrafikSpiel.space;

public class Lakai extends Monster{
    public Lakai(Coords position){
        super(10, 80, position, 1, 3);
    }

    @Override
    public Rectangle makeMove(Karte karte) {
        List<Coords> path = getPath(karte);

        Coords oldPosition = position;
        if(path.isEmpty()){
            System.out.println("Something went seriously wrong.");
        }else {
            System.out.println(path);
            schritteBisZiel = path.size() - 2;
            System.out.println(schritteBisZiel);
            position = path.get(1);
        }
        //-1 if monster moves left; 0 if no movement on the x-axis happens; 1 if monster moves right
        int directionX = position.getX() - oldPosition.getX();
        //-1 if monster moves up; 0 if no movement on the y-axis happens; 1 if monster moves down
        int directionY = position.getY() - oldPosition.getY();
        int x;
        int y;
        int width = space;
        int height = space;
        if(directionX < 0){
            x = oldPosition.getX()+directionX;
            width = width - directionX * space;
        }else{
            x = oldPosition.getX();
            width = width + directionX * space;
        }
        if(directionY < 0){
            y = oldPosition.getY()+directionY;
            height = height - directionY * space;
        }else{
            y = oldPosition.getY();
            height = height + directionY * space;
        }
        return new Rectangle(x, y, width, height);
    }

    protected List<Coords> getPath(Karte karte) {
        DijkstraShortestPath<Coords, DefaultWeightedEdge> pathfinder = new DijkstraShortestPath<>(karte.getGraphOfMap());
        return pathfinder.getPath(position, karte.getBasis().getPosition()).getVertexList();
    }
}
