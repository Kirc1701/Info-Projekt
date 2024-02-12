package project.Objekte.Monster;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import project.Coords;
import project.Karte;

import java.util.List;

public class Lakai extends Monster{
    public Lakai(Coords position){
        super(10, 80, position, 1, 3);
    }
    @Override
    protected List<Coords> getPath(Karte karte) {
        DijkstraShortestPath<Coords, DefaultWeightedEdge> pathfinder = new DijkstraShortestPath<>(karte.getGraphOfMap());
        return pathfinder.getPath(position, karte.getBasis().getPosition()).getVertexList();
    }
}
