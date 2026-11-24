package pl.symulacja.entity;

import javafx.scene.paint.Color;

/** Wzorzec Stan: zachowanie osobnika zależy od bieżącego stanu (podatny / zakażony / odporny). */
public interface EntityState {
    Color getColor();
    boolean canInfect();
    double getInfectionProbability(); // 1.0 objawowy, 0.5 bezobjawowy, 0 pozostali
    boolean isSusceptible();          // true tylko dla zdrowego, wrażliwego
    void step(Entity entity);         // upływ czasu (odliczanie trwania choroby)

    StateMemento save();

    static EntityState restore(StateMemento m) {
        return switch (m.type()) {
            case SUSCEPTIBLE -> new SusceptibleState();
            case IMMUNE -> new ImmuneState();
            case INFECTED -> new InfectedState(m.symptomatic(), m.remainingTicks());
        };
    }
}
