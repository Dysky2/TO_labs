package pl.symulacja.entity;

import javafx.scene.paint.Color;

public class ImmuneState implements EntityState {
    @Override
    public Color getColor() { return Color.LIMEGREEN; }

    @Override
    public boolean canInfect() { return false; }

    @Override
    public double getInfectionProbability() { return 0.0; }

    @Override
    public boolean isSusceptible() { return false; }

    @Override
    public void step(Entity entity) {}

    @Override
    public StateMemento save() { return new StateMemento(StateMemento.Type.IMMUNE, false, 0); }
}
