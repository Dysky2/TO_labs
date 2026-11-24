package pl.symulacja.entity;

import javafx.scene.paint.Color;

public class SusceptibleState implements EntityState {
    @Override
    public Color getColor() { return Color.DODGERBLUE; }

    @Override
    public boolean canInfect() { return false; }

    @Override
    public double getInfectionProbability() { return 0.0; }

    @Override
    public boolean isSusceptible() { return true; }

    @Override
    public void step(Entity entity) {}

    @Override
    public StateMemento save() { return new StateMemento(StateMemento.Type.SUSCEPTIBLE, false, 0); }
}
