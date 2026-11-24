package pl.symulacja.entity;

import pl.symulacja.vector.Vector2D;

import java.util.concurrent.ThreadLocalRandom;

public class Entity implements Moveable {
    // Gettery i settery
    private Vector2D position;
    private Vector2D velocity;
    private EntityState state;
    private boolean alive = true; // czy nadal przebywa na planszy
    private int contactTicks = 0; // ile kroków z rzędu jest w odległości <= 2m od chorego

    public Entity(Vector2D position, EntityState state) {
        this.position = position;
        this.state = state;
        randomizeVelocity();
    }

    // Losowanie wektora prędkości (maksymalnie 2.5 m/s)
    public void randomizeVelocity() {
        double speed = ThreadLocalRandom.current().nextDouble(0.5, 2.5); // do 2.5 m/s
        double angle = ThreadLocalRandom.current().nextDouble(0, 2 * Math.PI);

        // Zamiana współrzędnych biegunowych (kąt, długość) na współrzędne kartezjańskie (x, y)
        double vx = speed * Math.cos(angle);
        double vy = speed * Math.sin(angle);
        this.velocity = new Vector2D(vx, vy);
    }

    @Override
    public void move(double dt, double width, double height) {
        if (!alive) return;

        // 1. Upływ czasu w stanie (np. zdrowienie)
        state.step(this);

        // 2. Losowa zmiana kierunku/prędkości (np. 4% szansy w każdym kroku)
        if (ThreadLocalRandom.current().nextDouble() < 0.04) {
            randomizeVelocity();
        }

        // 3. Sprawdzenie, czy prędkość nie przekracza 2.5 m/s (wykorzystujemy abs() z wektora!)
        if (velocity.abs() > 2.5) {
            velocity = velocity.multiply(2.5 / velocity.abs());
        }

        // 4. Przemieszczenie: pos = pos + v * dt
        this.position = this.position.add(this.velocity.multiply(dt));

        // 5. Obsługa granic obszaru
        handleBoundaries(width, height);
    }

    private void handleBoundaries(double width, double height) {
        double x = position.getX();
        double y = position.getY();

        if (x <= 0 || x >= width || y <= 0 || y >= height) {
            // "W momencie dotarcia do dowolnej granicy obszaru każdy osobnik może:
            //  - zawrócić do wewnątrz (50%)
            //  - opuścić obszar (50%)"
            if (ThreadLocalRandom.current().nextBoolean()) {
                this.alive = false; // opuszcza obszar
            } else {
                // Zawraca do wnętrza (odbicie wektora prędkości)
                double vx = velocity.getX();
                double vy = velocity.getY();

                if (x <= 0 || x >= width) vx = -vx;
                if (y <= 0 || y >= height) vy = -vy;

                this.velocity = new Vector2D(vx, vy);

                // Zabezpieczenie, żeby nie wyleciał za planszę
                double clampedX = Math.max(0.1, Math.min(width - 0.1, x));
                double clampedY = Math.max(0.1, Math.min(height - 0.1, y));
                this.position = new Vector2D(clampedX, clampedY);
            }
        }
    }

    /** Kieruje prędkość do wnętrza obszaru (dla osobnika wkraczającego na granicy). */
    public void aimInto(double width, double height) {
        double speed = velocity.abs();
        double dx = width / 2 - position.getX(), dy = height / 2 - position.getY();
        double angle = Math.atan2(dy, dx) + ThreadLocalRandom.current().nextDouble(-Math.PI / 3, Math.PI / 3);
        this.velocity = new Vector2D(speed * Math.cos(angle), speed * Math.sin(angle));
    }

    // --- Pamiątka ---
    public EntityMemento save() {
        return new EntityMemento(position.getX(), position.getY(), velocity.getX(), velocity.getY(),
                state.save(), contactTicks);
    }

    public static Entity restore(EntityMemento m) {
        Entity e = new Entity(new Vector2D(m.x(), m.y()), EntityState.restore(m.state()));
        e.velocity = new Vector2D(m.vx(), m.vy());
        e.contactTicks = m.contactTicks();
        return e;
    }

    public Vector2D getPosition() { return position; }
    public EntityState getState() { return state; }
    public void setState(EntityState state) { this.state = state; }
    public boolean isAlive() { return alive; }
    public int getContactTicks() { return contactTicks; }
    public void setContactTicks(int contactTicks) { this.contactTicks = contactTicks; }
}
