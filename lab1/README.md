# Lab 1 - Kalkulator walut NBP

Konsolowy program w Javie przeliczający kwotę z jednej waluty na inną według aktualnych średnich kursów NBP.

## Wykonanie

- pobranie tabeli kursów NBP w formacie XML,
- sparsowanie danych i zapisanie walut w mapie,
- pobranie od użytkownika waluty źródłowej, docelowej oraz kwoty,
- przeliczenie wartości przez PLN i wyświetlenie wyniku,
- walidacja danych wejściowych oraz podział programu na klasy zgodnie z SOLID.

## Klasy

- `DataProvider` - pobiera dane z serwisu NBP,
- `NbpXmlRateParser` - parsuje dane XML,
- `CurrencyConverter` - wykonuje przeliczenia walut,
- `UserInputReader` i `ResultPrinter` - obsługują komunikację z użytkownikiem,
- `Currency` i `UserRequest` - przechowują dane aplikacji.
