package data;

import models.Currency;
import models.UserRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class CurrencyConverter implements Converter {
    public BigDecimal convert(HashMap<String, Currency> map, UserRequest request) {

        Currency pln = map.get("PLN");
        Currency sourceCurrency;
        Currency targetCurrency;

        if(map.containsKey(request.getSourceCode())) {
            sourceCurrency = map.get(request.getSourceCode());
        } else {
            throw new RuntimeException("This code does not exist");
        }

        if(!map.containsKey(request.getTargetCode())) {
            targetCurrency = map.get(request.getTargetCode());
        } else {
            throw new RuntimeException("This code does not exist");
        }

        BigDecimal amountInSourceCurrency = sourceCurrency.getAveragePrice().multiply(BigDecimal.valueOf(sourceCurrency.getUnit()));

        BigDecimal valueInPln = amountInSourceCurrency.multiply(request.getAmount());

        return valueInPln.divide(targetCurrency.getAveragePrice().multiply(BigDecimal.valueOf(targetCurrency.getUnit())), 4, RoundingMode.HALF_EVEN);
    }
}
