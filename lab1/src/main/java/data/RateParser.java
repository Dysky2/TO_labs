package data;

import java.util.Currency;
import java.util.HashMap;

public interface RateParser {
    public HashMap<String, Currency> parse(String data);
}
