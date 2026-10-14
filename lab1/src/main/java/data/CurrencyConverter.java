package data;

import models.Currency;
import models.UserRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class CurrencyConverter implements Converter {
    public BigDecimal convert(HashMap<String, Currency> map, UserRequest request) {
        Currency pln = map.get("PLN");

        Currency sourceCurrency = map.get(request.getSourceCode());
        Currency targetCurrency = map.get(request.getTargetCode());

        BigDecimal amountInSourceCurrency = sourceCurrency.getAveragePrice().multiply(BigDecimal.valueOf(sourceCurrency.getUnit()));

        System.out.println("Source " + amountInSourceCurrency);

        BigDecimal valueInPln = amountInSourceCurrency.multiply(request.getAmount());

        System.out.println("PLN: " + valueInPln);

        BigDecimal valueInTargetCurrency = valueInPln.divide(targetCurrency.getAveragePrice().multiply(BigDecimal.valueOf(targetCurrency.getUnit())), 4, RoundingMode.HALF_EVEN);

        System.out.println("CAD: " + valueInTargetCurrency);

        return valueInPln;
    }
}
