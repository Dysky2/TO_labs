package data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import models.Currency;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NbpXmlRateProvider implements RateParser {

    public Map<String, Currency> parse(String data) {

        HashMap<String, Currency> rates = new HashMap<>();
        rates.put("PLN", new Currency("Polski zl", 1, "PLN", BigDecimal.valueOf(1)));

        XmlMapper mapper =  XmlMapper.builder().defaultUseWrapper(false).build();

        try {
            JsonNode rootNode = mapper.readTree(data);

            JsonNode positionNode = rootNode.get("pozycja");

            List<Currency> currencies = mapper.convertValue(positionNode, new TypeReference<List<Currency>>(){});

            for(Currency currency : currencies) {
                rates.put(currency.getCode(), currency);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return rates;
    }
}
