package pl.symulacja.entity;

import java.io.Serializable;

/** Migawka stanu osobnika (część wzorca Pamiątka). */
public record StateMemento(Type type, boolean symptomatic, int remainingTicks) implements Serializable {
    public enum Type { SUSCEPTIBLE, INFECTED, IMMUNE }
}
