package project.Objekte.Monster;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import project.Coords;
import project.Karte;

import java.util.List;

public class DefaultMonster extends Monster{
    public DefaultMonster(Coords position) {
        super(10, 20, position, 5, 2);
    }

    @Override
    protected List<Coords> getPath(Karte karte) {
        DijkstraShortestPath<Coords, DefaultWeightedEdge> pathfinder = new DijkstraShortestPath<>(karte.getGraphOfMap());
        return pathfinder.getPath(position, karte.getBasis().getPosition()).getVertexList();
    }
}
