package core;

import objects.customer.Customer;
import objects.graph.*;
import objects.path.Path;
import objects.shelf.Shelf;
import objects.shelf.ShelfData;

import java.util.Arrays;
import java.util.Set;

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

        Path p = new Path(Arrays.asList(VertexID.ENTRANCE, VertexID.SHELF_1, VertexID.SHELF_2, VertexID.SHELF_1, VertexID.SHELF_2, VertexID.CHECKOUT_1, VertexID.CHECKOUT_2, VertexID.EXIT));
        Customer c = new Customer(854, 548, p);
        game.addObject(c);
    }

    private void setupGraph(){
        Vertex v_enterance = new Vertex(
                VertexID.ENTRANCE,
                new Point(709, 454),
                Arrays.asList(new Edge(VertexID.SHELF_1))
        );

        Vertex v_shelf_1 = new Vertex(
                VertexID.SHELF_1,
                new Point(709, 337),
                Arrays.asList(new Edge(VertexID.SHELF_2), new Edge(VertexID.ENTRANCE))
        );

        Vertex v_shelf_2 = new Vertex(
                VertexID.SHELF_2,
                new Point(709, 218),
                Arrays.asList(new Edge(VertexID.CHECKOUT_1), new Edge(VertexID.SHELF_1))
        );

        Vertex v_checkout = new Vertex(
                VertexID.CHECKOUT_1,
                new Point(536, 222),
                Arrays.asList(new Edge(VertexID.SHELF_2), new Edge(VertexID.EXIT))
        );

        Vertex v_exit = new Vertex(
                VertexID.EXIT,
                new Point(546, 453),
                Arrays.asList(new Edge(VertexID.CHECKOUT_1))
        );

        graph = new Graph(Set.of(v_enterance, v_shelf_1, v_shelf_2, v_checkout, v_exit));
        game.addObject(graph);
    }
}
