package models;

import java.math.BigDecimal;

public class UserRequest {
    private String sourceCode;
    private BigDecimal amount;
    private String targetCode;

    public UserRequest(String sourceCode, BigDecimal amount, String targetCode) {
        this.sourceCode = sourceCode;
        this.amount = amount;
        this.targetCode = targetCode;
    }

    public String getSourceCode() {
        return sourceCode;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public String getTargetCode() {
        return targetCode;
    }
}
