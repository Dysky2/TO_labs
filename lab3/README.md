# Lab 3 – Symulacja epidemii (Techniki Obiektowe)

Prosta symulacja rozprzestrzeniania się choroby w JavaFX. Osobniki poruszają się po planszy 60×40,
zarażają się przy dłuższym kontakcie, a stan symulacji można zapamiętywać, zapisywać do pliku i przywracać.

## Wymagania

- Java 21
- Maven (JavaFX 21 pobiera się automatycznie z `pom.xml`)

## Uruchomienie

```bash
mvn javafx:run
```

Albo otwórz projekt w IntelliJ IDEA i uruchom klasę `pl.symulacja.Main`.

## Obsługa

| Przycisk | Działanie |
|---|---|
| Pauza / Start | wstrzymuje i wznawia symulację |
| Start: bez odporności | nowa symulacja, nikt nie jest odporny |
| Start: część odporna (40%) | nowa symulacja, 40% osobników odpornych |
| Zapamiętaj stan | dodaje bieżący stan (pamiątkę) do listy |
| Przywróć wybrany | wraca do stanu wybranego z listy |
| Zapisz do pliku / Wczytaj z pliku | zapis i odczyt pamiątek z `simulation.sav` |

Kolory: niebieski – podatny, czerwony – zakażony z objawami, pomarańczowy – zakażony bez objawów, zielony – odporny.
Nad planszą wyświetlane są czas oraz liczba osobników w każdym stanie.

## Zasady symulacji

- Osobniki poruszają się z losowym kierunkiem i prędkością (wektor z losowego kąta i modułu), a na krawędzi planszy zawracają do środka.
- Zdrowy osobnik zaraża się, gdy przez 3 s przebywa w odległości do 2 jednostek od zakażonego.
  Zakażony z objawami zaraża z prawdopodobieństwem 1.0, bez objawów – 0.5.
- Choroba trwa 20–30 s, potem osobnik staje się odporny.
- Nowe osobniki pojawiają się na brzegu planszy (do docelowej liczby 60). Każdy ma 10% szans, że wejdzie już zakażony.
- Symulacja liczy 25 kroków na sekundę.

## Wzorce projektowe

- **Memento** – `Simulation` (Originator) tworzy `SimulationMemento` (z `EntityMemento` i `StateMemento`), a `CareTaker` przechowuje ich listę, przywraca je i zapisuje do pliku.
- **State** – `EntityState` z implementacjami `SusceptibleState`, `InfectedState`, `ImmuneState`; `InfectedState` po upływie czasu zmienia stan osobnika na odporny.
- **Adapter** – `Polar2DAdapter` dopasowuje `Vector2D` do interfejsu `IPolar2D` (kąt i moduł).
- **Decorator** – `Vector3DDecorator` dodaje do `IVector` trzecią współrzędną i iloczyn wektorowy. Wersja z dziedziczeniem: `Vector3DInheritance` (analogicznie `PolarInheritance2D`).

## Struktura projektu

```
src/main/java/pl/symulacja/
├── Main.java            – okno JavaFX, przyciski, pętla animacji
├── entity/              – Entity, stany (State), pamiątki encji
├── simulation/          – Simulation, SimulationMemento, CareTaker
├── ui/                  – Ui (rysowanie osobników na planszy)
└── vector/              – Vector2D, adapter, dekorator, warianty z dziedziczeniem
```