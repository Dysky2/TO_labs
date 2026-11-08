package pl.symulacja;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.symulacja.ui.Ui;

public class Main extends Application {
    private static final double N = 100;
    private static final double M = 60;

    private static final double SCALE = 10;

    private static final double DOT_SIZE = 20;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(N * SCALE, M * SCALE);
        Ui ui = new Ui(N, M, SCALE, DOT_SIZE);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        ui.draw(gc);

        StackPane root = new StackPane(canvas);
        root.setStyle("-fx-background-color: black;");
        Scene scene = new Scene(root);

        stage.setTitle("Symulacja zakażania");
        stage.setScene(scene);
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}