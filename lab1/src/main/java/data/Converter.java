package data;

import models.Currency;
import models.UserRequest;

import java.math.BigDecimal;
import java.util.HashMap;

public interface Converter {
    public BigDecimal convert(HashMap<String, Currency> map, UserRequest request);
}
