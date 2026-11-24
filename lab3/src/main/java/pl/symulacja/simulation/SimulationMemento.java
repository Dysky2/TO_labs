package pl.symulacja.simulation;

import pl.symulacja.entity.EntityMemento;

import java.io.Serializable;
import java.util.List;

/** Pamiątka: niezmienna migawka całej symulacji w kroku {@code tick}. */
public record SimulationMemento(long tick, double immuneFraction, List<EntityMemento> entities) implements Serializable {
    public double timeSeconds() { return tick / (double) Simulation.STEPS_PER_SECOND; }
}
