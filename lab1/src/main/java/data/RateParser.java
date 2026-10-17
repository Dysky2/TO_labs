package data;

import models.Currency;

import java.util.HashMap;
import java.util.Map;

public interface RateParser {
    public Map<String, Currency> parse(String data);
}
