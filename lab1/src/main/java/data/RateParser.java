package data;

import models.Currency;

import java.util.HashMap;

public interface RateParser {
    public HashMap<String, Currency> parse(String data);
}
