package data;

import models.Currency;
import models.UserRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter implements Converter {
    public BigDecimal convert(Map<String, Currency> map, UserRequest request) {

        Currency sourceCurrency;
        Currency targetCurrency;

        if(map.containsKey(request.getSourceCode())) {
            sourceCurrency = map.get(request.getSourceCode());
        } else {
            throw new IllegalArgumentException("Code "  + request.getSourceCode() + " does not exist in dataset");
        }

        if(map.containsKey(request.getTargetCode())) {
            targetCurrency = map.get(request.getTargetCode());
        } else {
            throw new IllegalArgumentException("Code "  + request.getTargetCode() + " does not exist in dataset");
        }

        BigDecimal sourceRatePerUnit = sourceCurrency.getAveragePrice().divide(BigDecimal.valueOf(sourceCurrency.getUnit()), 8, RoundingMode.HALF_EVEN);

        BigDecimal valueInPln = sourceRatePerUnit.multiply(request.getAmount());

        return valueInPln.divide(targetCurrency.getAveragePrice().divide(BigDecimal.valueOf(targetCurrency.getUnit()), 8, RoundingMode.HALF_EVEN), 8, RoundingMode.HALF_EVEN);
    }
}
