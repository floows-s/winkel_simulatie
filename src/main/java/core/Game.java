package core;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game extends javafx.application.Application {
    public final int WIDTH = 1280;
    public final int HEIGHT = 720;

    private List<GameObject> gameObjects = new ArrayList<GameObject>();

    private Simulation simulation = new Simulation(this);

    @Override
    public void start(Stage stage) throws IOException {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext g = canvas.getGraphicsContext2D();

        Pane root = new Pane(canvas);

        stage.setScene(new Scene(root));
        stage.setTitle("Winkel Simulatie");
        stage.show();



        new AnimationTimer() {
            private long previousTime = 0;

            @Override
            public void handle(long now) {
                if (previousTime == 0) {
                    previousTime = now;
                    return;
                }

                double dt = (now - previousTime) / 1_000_000_000.0;
                previousTime = now;

                Update(dt);
                Render(g);
            }
        }.start();


        simulation.Start();
    }

    private void Update(double delta){
        for(GameObject obj: gameObjects){
            obj.Update(delta);
        }
    }

    private void Render(GraphicsContext g){
        for(GameObject obj: gameObjects){
            obj.Render(g);
        }

    }

    public void AddObject(GameObject obj){
        gameObjects.add(obj);
    }
}
