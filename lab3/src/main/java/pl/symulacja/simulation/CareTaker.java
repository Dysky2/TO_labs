package pl.symulacja.simulation;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** CareTaker: przechowuje migawki Originatora (Simulation) i zapisuje je do pliku. */
public class CareTaker {
    private final Simulation originator;

    private final List<SimulationMemento> mementos = new ArrayList<>();

    public CareTaker(Simulation originator) { this.originator = originator; }

    /** Tworzy migawkę bieżącego stanu Originatora i zapamiętuje ją na liście. */
    public void create() { mementos.add(originator.createMemento()); }

    /** Przywraca Originatora do migawki o podanym numerze (od 0). */
    public void restore(int number) { originator.restore(mementos.get(number)); }

    public List<SimulationMemento> getMementos() { return mementos; }

    public void saveToFile(Path path) throws IOException {
        create();
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            out.writeObject(mementos.get(mementos.size() - 1));
        }
    }

    public void loadFromFile(Path path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            originator.restore((SimulationMemento) in.readObject());
        }
    }
}
