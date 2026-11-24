package pl.symulacja.entity;

import java.io.Serializable;

/** Migawka pojedynczego osobnika. */
public record EntityMemento(double x, double y, double vx, double vy,
                            StateMemento state, int contactTicks) implements Serializable {
}
