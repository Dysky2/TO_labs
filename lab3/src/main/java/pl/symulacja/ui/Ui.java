package pl.symulacja.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ui {
    private final double n;
    private final double m;
    private final double scale;
    private final double dotSize;

    public Ui(double n, double m, double scale, double dotSize) {
        this.n = n;;
        this.m = m;
        this.scale = scale;
        this.dotSize = dotSize;
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, n * scale, m * scale);

        gc.setStroke(Color.web("#444444"));
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, n * scale, m * scale);

        drawPerson(gc, 10, 10, Color.DODGERBLUE);   // zdrowy
        drawPerson(gc, 1, 1, Color.YELLOW);   // zdrowy
        drawPerson(gc, 50, 30, Color.RED);          // zakażony objawowy
        drawPerson(gc, 70, 20, Color.ORANGE);       // zakażony bezobjawowy
        drawPerson(gc, 30, 45, Color.LIMEGREEN);
    }

    public void drawPerson(GraphicsContext gc, double xMeters, double yMeters, Color color) {
        double px = xMeters * scale - dotSize / 2;
        double py = yMeters * scale - dotSize / 2;

        gc.setFill(color);
        gc.fillOval(px, py, dotSize, dotSize);
    }
}
