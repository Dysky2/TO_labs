package pl.symulacja.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pl.symulacja.entity.Entity;

import java.util.List;

public class Ui {
    private final double n;
    private final double m;
    private final double scale;
    private final double dotSize;

    public Ui(double n, double m, double scale, double dotSize) {
        this.n = n;
        this.m = m;
        this.scale = scale;
        this.dotSize = dotSize;
    }

    public void draw(GraphicsContext gc, List<Entity> entities) {
        // Tło
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, n * scale, m * scale);

        // Obramowanie planszy
        gc.setStroke(Color.web("#444444"));
        gc.setLineWidth(2);
        gc.strokeRect(0, 0, n * scale, m * scale);

        // Rysowanie każdego osobnika
        for (Entity e : entities) {
            double px = e.getPosition().getX() * scale - dotSize / 2;
            double py = e.getPosition().getY() * scale - dotSize / 2;

            gc.setFill(e.getState().getColor());
            gc.fillOval(px, py, dotSize, dotSize);
        }
    }
}
