package pl.symulacja;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private static final double N = 100;
    private static final double M = 60;

    private static final double SCALE = 10;

    private static final double DOT_SIZE = 10;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(N * SCALE, M * SCALE);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        draw(gc);

        StackPane root = new StackPane(canvas);
        root.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(root);

        stage.setTitle("Symulacja zakażania");
        stage.setScene(scene);
        stage.show();
    }

    private void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, N * SCALE, M * SCALE);

        gc.setStroke(Color.web("#444444"));
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, N * SCALE, M * SCALE);

        drawPerson(gc, 10, 10, Color.DODGERBLUE);   // zdrowy
        drawPerson(gc, 50, 30, Color.RED);          // zakażony objawowy
        drawPerson(gc, 70, 20, Color.ORANGE);       // zakażony bezobjawowy
        drawPerson(gc, 30, 45, Color.LIMEGREEN);
    }

    private void drawPerson(GraphicsContext gc, double xMeters, double yMeters, Color color) {
        double px = xMeters * SCALE - DOT_SIZE / 2;
        double py = yMeters * SCALE - DOT_SIZE / 2;

        gc.setFill(color);
        gc.fillOval(px, py, DOT_SIZE, DOT_SIZE);
    }

    public static void main(String[] args) {
        launch(args);
    }

}