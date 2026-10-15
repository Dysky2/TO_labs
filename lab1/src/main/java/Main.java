import data.CurrencyConverter;
import data.DataProvider;
import data.NbpXmlRateProvider;
import models.Currency;
import models.UserRequest;
import view.ResultPrinter;
import view.UserInputReader;

import java.math.BigDecimal;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        DataProvider dataProvider = new DataProvider();

        String data = dataProvider.getData();

        NbpXmlRateProvider nbpXmlRateProvider = new NbpXmlRateProvider();

        HashMap<String, Currency> currencies = nbpXmlRateProvider.parse(data);

        CurrencyConverter converter = new CurrencyConverter();

        UserInputReader userInputReader = new UserInputReader();

        UserRequest request = userInputReader.readUserInput();

        BigDecimal result = converter.convert(currencies, request);

        ResultPrinter resultPresenter = new ResultPrinter();

        resultPresenter.showResult(result, request);
    }
}
