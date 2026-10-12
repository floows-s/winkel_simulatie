package core;

import objects.graph.*;
import objects.path.Path;
import objects.path.pathfinder.dijkstracache.DijkstraCachePathFinderStrategy;
import objects.shelf.Shelf;
import objects.shelf.ShelfData;

public class Simulation{
    private final Game game;
    public Graph graph;

    Simulation(Game game){
        this.game = game;
        setup();
    }


    private void setup(){
        setupGraph();

        Shelf s1 = new Shelf(new ShelfData(new Point(785, 333)));
        Shelf s2 = new Shelf(new ShelfData(new Point(805, 210)));

        game.addObject(s1);
        game.addObject(s2);

//        Path p = new Path(Arrays.asList(VertexID.ENTRANCE, VertexID.SHELF_1, VertexID.SHELF_2, VertexID.SHELF_1, VertexID.SHELF_2, VertexID.CHECKOUT_1, VertexID.EXIT));
        Path p = graph.getShortestPath(VertexID.ENTRANCE, VertexID.EXIT);

//        Customer c = new Customer(854, 548, p);
//        game.addObject(c);

    }

    private void setupGraph(){
        GraphData data = GraphData.fromJson("/json/graph/graph.json");

        graph = new Graph(
                data,
                new DijkstraCachePathFinderStrategy()
        );

        game.addObject(graph);
    }
}
