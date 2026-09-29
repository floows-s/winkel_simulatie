package core;

import core.logger.Logger;
import core.logger.TagLogger;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game extends javafx.application.Application {
    // CONSTANTS
    public final int WIDTH = 1280;
    public final int HEIGHT = 720;

    // LOGGER
    private final Logger log = new TagLogger(this.getClass().getSimpleName());

    // OBJECTS
    private final Queue<Object> removalQueue = new ArrayDeque<>();

    private final List<Renderable> renderables = new ArrayList<>();
    private final List<Updatable> updatables = new ArrayList<>();

    // GRAPHICS
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    // SIMULATION
    private Simulation simulation;

    @Override
    public void start(Stage stage) {
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

                update(dt, now, simulation);
                handleRemovalQueue(removalQueue);
                render(graphicsContext);
            }
        }.start();
    }

    public void setup(Stage stage){
        canvas = new Canvas(WIDTH, HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();

        canvas.setOnMouseClicked(event -> {
            String mouse_cords_json = "\n\"x\": " + event.getX() + ", \n\"y\": " + event.getY() + "\n";

            log.logInfo(mouse_cords_json);

            StringSelection stringSelection = new StringSelection(mouse_cords_json);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });

        simulation = new Simulation(this);

        Pane root = new Pane(canvas);
        stage.setScene(new Scene(root));
        stage.setTitle("Winkel Simulatie");
        stage.show();
    }

    private void update(double delta, long now, Simulation s){
        for(Updatable obj: updatables){
            obj.update(delta, now, s);
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
                    log.logError("Failed to remove Renderable object, not found. (" + obj + ")");
                }
            }

            if(obj instanceof Updatable uObj){
                if(!updatables.remove(uObj)) {
                    log.logError("Failed to remove Updatable object, not found. (" + obj + ")");
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
            log.logWarning("An object without the appropriate type was attempted to be added (" + obj + ")");
        }
    }

    public void removeObject(Object obj){
        removalQueue.add(obj);
    }

}
