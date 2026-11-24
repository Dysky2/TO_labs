package pl.symulacja.simulation;

import pl.symulacja.entity.*;
import pl.symulacja.vector.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;

/** Originator wzorca Pamiątka: logika symulacji (25 kroków = 1 s). */
public class Simulation {
    public static final int STEPS_PER_SECOND = 25;
    public static final double DT = 1.0 / STEPS_PER_SECOND;
    private static final double INFECTION_RADIUS = 2.0;
    private static final int CONTACT_STEPS_REQUIRED = 3 * STEPS_PER_SECOND;
    private static final double ENTRY_INFECTED_PROBABILITY = 0.10;
    private static final double SPAWN_CHANCE_PER_STEP = 0.2;

    private final double width, height;
    private final int targetPopulation;
    private double immuneFraction; // udział odpornych wśród niezakażonych (0 = scenariusz bez odporności)

    private final List<Entity> entities = new ArrayList<>();
    private long tick = 0;

    public Simulation(double width, double height, int targetPopulation, double immuneFraction) {
        this.width = width;
        this.height = height;
        this.targetPopulation = targetPopulation;
        this.immuneFraction = immuneFraction;
        initPopulation();
    }

    private static ThreadLocalRandom rnd() { return ThreadLocalRandom.current(); }

    private EntityState newcomerState() {
        return rnd().nextDouble() < immuneFraction ? new ImmuneState() : new SusceptibleState();
    }

    private void initPopulation() {
        for (int i = 0; i < targetPopulation; i++) {
            Vector2D pos = new Vector2D(rnd().nextDouble(2, width - 2), rnd().nextDouble(2, height - 2));
            EntityState state = i < 2 ? new InfectedState(i == 0) : newcomerState();
            entities.add(new Entity(pos, state));
        }
    }

    public void step() {
        entities.removeIf(e -> !e.isAlive());
        if (entities.size() < targetPopulation && rnd().nextDouble() < SPAWN_CHANCE_PER_STEP) {
            spawnAtBorder();
        }
        for (Entity e : entities) e.move(DT, width, height);
        checkInfections();
        tick++;
    }

    private void spawnAtBorder() {
        double x, y;
        switch (rnd().nextInt(4)) {
            case 0 -> { x = rnd().nextDouble(0, width); y = 0.2; }
            case 1 -> { x = rnd().nextDouble(0, width); y = height - 0.2; }
            case 2 -> { x = 0.2; y = rnd().nextDouble(0, height); }
            default -> { x = width - 0.2; y = rnd().nextDouble(0, height); }
        }
        // najpierw odporność; 10% zakażenia dotyczy tylko osobników wrażliwych
        EntityState state = newcomerState();
        if (state.isSusceptible() && rnd().nextDouble() < ENTRY_INFECTED_PROBABILITY) {
            state = new InfectedState(rnd().nextBoolean());
        }
        Entity e = new Entity(new Vector2D(x, y), state);
        e.aimInto(width, height); // prędkość skierowana do wnętrza obszaru
        entities.add(e);
    }

    /** Zakażenie: dystans <= 2 m utrzymany >= 3 s; szansa 50% (bezobjawowy) / 100% (objawowy). */
    private void checkInfections() {
        for (Entity healthy : entities) {
            if (!healthy.getState().isSusceptible()) continue;

            double bestChance = -1;
            for (Entity other : entities) {
                if (other == healthy || !other.getState().canInfect()) continue;
                if (healthy.getPosition().distanceTo(other.getPosition()) <= INFECTION_RADIUS) {
                    bestChance = Math.max(bestChance, other.getState().getInfectionProbability());
                }
            }

            if (bestChance < 0) {
                healthy.setContactTicks(0);
                continue;
            }
            healthy.setContactTicks(healthy.getContactTicks() + 1);
            if (healthy.getContactTicks() >= CONTACT_STEPS_REQUIRED) {
                if (rnd().nextDouble() < bestChance) {
                    healthy.setState(new InfectedState(rnd().nextBoolean()));
                }
                healthy.setContactTicks(0);
            }
        }
    }

    // --- Pamiątka ---
    public SimulationMemento createMemento() {
        List<EntityMemento> list = new ArrayList<>();
        for (Entity e : entities) list.add(e.save());
        return new SimulationMemento(tick, immuneFraction, list);
    }

    public void restore(SimulationMemento m) {
        entities.clear();
        for (EntityMemento em : m.entities()) entities.add(Entity.restore(em));
        tick = m.tick();
        immuneFraction = m.immuneFraction();
    }

    public double getImmuneFraction() { return immuneFraction; }

    public List<Entity> getEntities() { return entities; }
    public long getTick() { return tick; }
    public double getTimeSeconds() { return tick / (double) STEPS_PER_SECOND; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }

    public long count(Predicate<EntityState> p) {
        return entities.stream().filter(e -> p.test(e.getState())).count();
    }
}
