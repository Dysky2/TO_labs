package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import utils.PolishBigDecimalDeserializer;

import java.math.BigDecimal;

@JacksonXmlRootElement(localName = "pozycja")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency {

    @JacksonXmlProperty(localName = "nazwa_waluty")
    private String name;

    @JacksonXmlProperty(localName = "przelicznik")
    private int unit;

    @JacksonXmlProperty(localName = "kod_waluty")
    private String code;

    @JsonDeserialize(using = PolishBigDecimalDeserializer.class)
    @JacksonXmlProperty(localName = "kurs_sredni")
    private BigDecimal averagePrice;

    public Currency() {}

    public Currency(String name,  int unit, String code, BigDecimal averagePrice) {
        this.name = name;
        this.unit = unit;
        this.code = code;
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

    public BigDecimal  getAveragePrice() {
        return averagePrice;
    }
}
