package core;

import objects.customer.Customer;
import objects.graph.Edge;
import objects.graph.Graph;
import objects.graph.Path;
import objects.graph.Vertex;
import objects.shelf.Shelf;
import objects.shelf.ShelfData;
import utilities.JsonFileParser;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulation implements Updatable{
    private final Game game;
    private Graph graph;

    Simulation(Game game){
        this.game = game;
        setup();
    }

    /*
     * TODO:
     *  Nadenken over: wat gaat simulatie allemaal doen? Wat is zijn verantwoordelijkheid?
     *  Bijv vakkenvullers naar juiste schap sturen die gevuld moet worden?
     *  Nieuwe customers aanmaken?
     *  Stats bijhouden?
     *
     *  Want customer stuurt zich zelf aan via een soort statemachine. Niet persee door de simulatie
     *
     * */

    private void setup(){
        Vertex v_1 = new Vertex(
                1,
                new Point(233, 143),
                Arrays.asList(new Edge(2), new Edge(3))
        );

        Vertex v_2 = new Vertex(
                2,
                new Point(463, 182),
                Arrays.asList(new Edge(1), new Edge(3))
        );

        Vertex v_3 = new Vertex(
                3,
                new Point(305, 331),
                Arrays.asList(new Edge(1), new Edge(2), new Edge(4))
        );

        Vertex v_4 = new Vertex(
                4,
                new Point(109, 335),
                Arrays.asList(new Edge(5))
        );

        Vertex v_5 = new Vertex(
                5,
                new Point(156, 485),
                Arrays.asList(new Edge(6))
        );

        Vertex v_6 = new Vertex(
                6,
                new Point(294, 421),
                Arrays.asList()
        );



        graph = new Graph(Arrays.asList(v_1, v_2, v_3, v_4, v_5, v_6));
        game.addObject(graph);

        Path p = new Path(
                Arrays.asList(v_1, v_2, v_3, v_1, v_4, v_5, v_6)
        );

        Customer c = new Customer(0, 0, p);
        game.addObject(c);
    }



    @Override
    public void update(double delta, long now) {

    }
}
