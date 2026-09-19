package core;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class Game extends javafx.application.Application {
    // CONSTANTS
    public final int WIDTH = 1280;
    public final int HEIGHT = 720;

    // OBJECTS
    private Queue<Object> removalQueue = new ArrayDeque<Object>();

    private List<Renderable> renderables = new ArrayList<Renderable>();
    private List<Updatable> updatables = new ArrayList<Updatable>();

    // GRAPHICS
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    // SIMULATION
    private Simulation simulation;

    @Override
    public void start(Stage stage) throws IOException {
        setup(stage);

        new AnimationTimer() {
            private long previousTime = 0;

            @Override
            public void handle(long now) {
                if (previousTime == 0) {
                    previousTime = now;
                    return;
                }

                double dt = (now - previousTime) / 1e9;
                previousTime = now;

                update(dt, now);
                handleRemovalQueue(removalQueue);
                render(graphicsContext);
            }
        }.start();
    }

    public void setup(Stage stage){
        canvas = new Canvas(WIDTH, HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();

        simulation = new Simulation(this);
        addObject(simulation);

        Pane root = new Pane(canvas);
        stage.setScene(new Scene(root));
        stage.setTitle("Winkel Simulatie");
        stage.show();
    }

    private void update(double delta, long now){
        for(Updatable obj: updatables){
            obj.update(delta, now);
        }
    }

    private void render(GraphicsContext g){
        g.clearRect(0, 0, WIDTH, HEIGHT);

        for(Renderable obj: renderables){
            obj.render(g);
        }
    }

    private void handleRemovalQueue(Queue<Object> queue){
        while(!queue.isEmpty()){
            Object obj = queue.poll();

            if(obj instanceof Renderable rObj){
                if(!renderables.remove(rObj)) {
                    System.out.println("Error: Failed to remove Renderable object, not found. (" + obj + ")");
                }
            }

            if(obj instanceof Updatable uObj){
                if(!updatables.remove(uObj)) {
                    System.out.println("Error: Failed to remove Updatable object, not found. (" + obj + ")");
                }
            }
        }
    }

    public void addObject(Object obj){
        boolean validInterface = false;

        if(obj instanceof Renderable){
            validInterface = true;
            renderables.add((Renderable) obj);
        }

        if(obj instanceof Updatable){
            validInterface = true;
            updatables.add((Updatable) obj);
        }

        if(!validInterface){
            System.out.println("Warning: a object without the appropriate type was attempted to be added (" + obj + ")");
        }
    }

    public void removeObject(Object obj){
        removalQueue.add(obj);
    }

}
