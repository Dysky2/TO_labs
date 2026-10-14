import data.CurrencyConverter;
import data.DataProvider;
import data.NbpXmlRateProvider;
import models.Currency;
import models.UserRequest;

import java.math.BigDecimal;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        DataProvider dataProvider = new DataProvider();

        String data = dataProvider.getData();

        NbpXmlRateProvider nbpXmlRateProvider = new NbpXmlRateProvider();

        HashMap<String, Currency> currencies = nbpXmlRateProvider.parse(data);

        CurrencyConverter converter = new CurrencyConverter();

        converter.convert(currencies, new UserRequest("THB", BigDecimal.valueOf(1000),"HKD"));
    }
}
