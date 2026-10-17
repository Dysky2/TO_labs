import data.*;
import models.Currency;
import models.UserRequest;
import view.InputReader;
import view.ResultPresenter;
import view.ResultPrinter;
import view.UserInputReader;

import java.math.BigDecimal;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String URL = "https://static.nbp.pl/dane/kursy/xml/lastA.xml";

        RateProvider dataProvider = new DataProvider(URL);
        String data = dataProvider.getData();

        RateParser nbpXmlRateParser = new NbpXmlRateParser();
        Map<String, Currency> currencies = nbpXmlRateParser.parse(data);

        InputReader userInputReader = new UserInputReader();
        Converter converter = new CurrencyConverter();
        ResultPresenter resultPresenter = new ResultPrinter();

        while (true) {
            try {
                UserRequest request = userInputReader.readUserInput();

                BigDecimal result = converter.convert(currencies, request);

                resultPresenter.showResult(result, request);
                break;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
