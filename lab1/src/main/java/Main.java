import data.*;
import models.Currency;
import models.UserRequest;
import view.InputReader;
import view.ResultPresenter;
import view.ResultPrinter;
import view.UserInputReader;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String URL = "https://static.nbp.pl/dane/kursy/xml/lastA.xml";

        RateProvider dataProvider = new DataProvider(URL);
        String data = dataProvider.getData();

        RateParser nbpXmlRateProvider = new NbpXmlRateProvider();
        Map<String, Currency> currencies = nbpXmlRateProvider.parse(data);

        InputReader userInputReader = new UserInputReader();
        UserRequest request = userInputReader.readUserInput();

        Converter converter;
        BigDecimal result;
        try {
            converter = new CurrencyConverter();
            result = converter.convert(currencies, request);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        ResultPresenter resultPresenter = new ResultPrinter();
        resultPresenter.showResult(result, request);
    }
}
