import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;

public class Currency {

    @JacksonXmlProperty(localName = "nazwa_waluty")
    private String name;

    @JacksonXmlProperty(localName = "przelicznik")
    private int unit;

    @JacksonXmlProperty(localName = "kod_waluty")
    private String code;

    @JacksonXmlProperty(localName = "kurs_sredni")
    private String averagePrice;

    public Currency(String code, int unit, String name, String averagePrice) {
        this.code = code;
        this.unit = unit;
        this.name = name;
        this.averagePrice = averagePrice;
    }

    public String getName() {
        return name;
    }

    public int getUnit() {
        return unit;
    }

    public String getCode() {
        return code;
    }

    public String  getAveragePrice() {
        return averagePrice;
    }
}
