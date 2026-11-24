package pl.symulacja.entity;

import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

public class InfectedState implements EntityState {
    private final boolean symptomatic;
    private int remainingTicks;

    public InfectedState(boolean symptomatic) {
        this.symptomatic = symptomatic;
        int seconds = ThreadLocalRandom.current().nextInt(20, 31); // 20-30 s
        this.remainingTicks = seconds * 25;
    }

    /** Konstruktor używany przy odtwarzaniu z pamiątki. */
    public InfectedState(boolean symptomatic, int remainingTicks) {
        this.symptomatic = symptomatic;
        this.remainingTicks = remainingTicks;
    }

    @Override
    public Color getColor() { return symptomatic ? Color.RED : Color.ORANGE; }

    @Override
    public boolean canInfect() { return true; }

    @Override
    public double getInfectionProbability() { return symptomatic ? 1.0 : 0.5; }

    @Override
    public boolean isSusceptible() { return false; }

    @Override
    public void step(Entity entity) {
        if (--remainingTicks <= 0) {
            entity.setState(new ImmuneState()); // po chorobie zyskuje odporność
        }
    }

    @Override
    public StateMemento save() { return new StateMemento(StateMemento.Type.INFECTED, symptomatic, remainingTicks); }
}
