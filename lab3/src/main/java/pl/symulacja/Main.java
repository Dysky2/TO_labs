package pl.symulacja;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pl.symulacja.entity.*;
import pl.symulacja.simulation.CareTaker;
import pl.symulacja.simulation.Simulation;
import pl.symulacja.simulation.SimulationMemento;
import pl.symulacja.ui.Ui;

import java.nio.file.Path;

public class Main extends Application {
    private static final double N = 60;  // szerokość obszaru [m]
    private static final double M = 40;  // wysokość obszaru [m]
    private static final double SCALE = 15;
    private static final double DOT_SIZE = 16;
    private static final int POPULATION = 60;
    private static final double IMMUNE_FRACTION = 0.4; // scenariusz z odpornością
    private static final long STEP_INTERVAL_NS = 1_000_000_000L / Simulation.STEPS_PER_SECOND;
    private static final Path SAVE_FILE = Path.of("simulation.sav");

    private Simulation simulation;
    private CareTaker careTaker;
    private boolean running = true;
    private long lastUpdate = 0;

    private final Label stats = new Label();
    private final ComboBox<String> snapshots = new ComboBox<>();

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(N * SCALE, M * SCALE);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Ui ui = new Ui(N, M, SCALE, DOT_SIZE);

        newSimulation(false);

        Button pause = new Button("Pauza");
        pause.setOnAction(e -> {
            running = !running;
            pause.setText(running ? "Pauza" : "Start");
        });

        // Dwa przypadki z zadania: każdy guzik startuje nową symulację w danym scenariuszu
        Button startNoImmunity = new Button("Start: bez odporności");
        startNoImmunity.setOnAction(e -> newSimulation(false));
        Button startWithImmunity = new Button("Start: część odporna (40%)");
        startWithImmunity.setOnAction(e -> newSimulation(true));

        // Migawki w pamięci (wzorzec Pamiątka): zapamiętaj stan teraz, potem wybierz z listy i przywróć
        Button snapshot = new Button("Zapamiętaj stan");
        snapshot.setOnAction(e -> {
            careTaker.create();
            refreshSnapshots();
            snapshots.getSelectionModel().selectLast();
        });
        snapshots.setPromptText("Zapamiętane stany");
        Button restore = new Button("Przywróć wybrany");
        restore.setOnAction(e -> {
            int i = snapshots.getSelectionModel().getSelectedIndex();
            if (i >= 0) careTaker.restore(i);
        });

        Button save = new Button("Zapisz do pliku");
        save.setOnAction(e -> {
            try { careTaker.saveToFile(SAVE_FILE); refreshSnapshots(); }
            catch (Exception ex) { error("Zapis nie powiódł się: " + ex.getMessage()); }
        });
        Button load = new Button("Wczytaj z pliku");
        load.setOnAction(e -> {
            try { careTaker.loadFromFile(SAVE_FILE); }
            catch (Exception ex) { error("Odczyt nie powiódł się: " + ex.getMessage()); }
        });

        HBox bar = new HBox(8, startNoImmunity, startWithImmunity, pause,
                new Separator(javafx.geometry.Orientation.VERTICAL), snapshot, snapshots, restore,
                new Separator(javafx.geometry.Orientation.VERTICAL), save, load);
        bar.setPadding(new Insets(8));

        stats.setPadding(new Insets(6, 8, 6, 8));
        stats.setStyle("-fx-text-fill: white;");
        Label legend = new Label("niebieski: podatny | czerwony: zakażony z objawami | pomarańczowy: bez objawów | zielony: odporny");
        legend.setStyle("-fx-text-fill: #bbbbbb;");
        legend.setPadding(new Insets(0, 8, 6, 8));

        BorderPane root = new BorderPane();
        root.setTop(bar);
        root.setCenter(canvas);
        javafx.scene.layout.VBox bottom = new javafx.scene.layout.VBox(stats, legend);
        root.setBottom(bottom);
        root.setStyle("-fx-background-color: #222222;");

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lastUpdate == 0) lastUpdate = now;
                // jedna sekunda symulacji = 25 kroków
                while (now - lastUpdate >= STEP_INTERVAL_NS) {
                    if (running) simulation.step();
                    lastUpdate += STEP_INTERVAL_NS;
                }
                ui.draw(gc, simulation.getEntities());
                stats.setText(String.format("t = %.1f s | osobniki: %d | podatni: %d | zakażeni: %d | odporni: %d",
                        simulation.getTimeSeconds(), simulation.getEntities().size(),
                        simulation.count(EntityState::isSusceptible),
                        simulation.count(EntityState::canInfect),
                        simulation.count(s -> s instanceof ImmuneState)));
            }
        };
        timer.start();

        stage.setTitle("Symulacja rozwoju zakażenia");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void newSimulation(boolean withImmunity) {
        simulation = new Simulation(N, M, POPULATION, withImmunity ? IMMUNE_FRACTION : 0.0);
        careTaker = new CareTaker(simulation);
        refreshSnapshots();
    }

    private void refreshSnapshots() {
        int selected = snapshots.getSelectionModel().getSelectedIndex();
        snapshots.getItems().clear();
        int i = 1;
        for (SimulationMemento m : careTaker.getMementos()) {
            snapshots.getItems().add(String.format("#%d (t = %.1f s)", i++, m.timeSeconds()));
        }
        if (selected >= 0 && selected < snapshots.getItems().size()) {
            snapshots.getSelectionModel().select(selected);
        }
    }

    private void error(String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
