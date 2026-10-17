package data;

import models.Currency;
import models.UserRequest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public interface Converter {
    public BigDecimal convert(Map<String, Currency> map, UserRequest request);
}
