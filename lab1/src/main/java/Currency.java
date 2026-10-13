import java.math.BigDecimal;

public class Currency {
    private String name;
    private int unit;
    private String code;
    private BigDecimal averagePrice;

    Currency(String code, int unit, String name, BigDecimal averagePrice) {
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

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }
}
