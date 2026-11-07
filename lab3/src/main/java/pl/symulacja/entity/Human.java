package pl.symulacja.entity;

public class Human extends Entity {
    private String name;
    private EntityStatus status;
    private EntityPopulationStatus populationStatus;

    public Human() {
        populationStatus = EntityPopulationStatus.MUTABLE;
    }

    public Human(String name) {
        this.name = name;
        populationStatus = EntityPopulationStatus.MUTABLE;
    }

    public Human(String name, EntityStatus status) {
        this.name = name;
        this.status = status;
        populationStatus = EntityPopulationStatus.MUTABLE;
    }

}
