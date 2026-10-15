package view;

import models.UserRequest;

import java.math.BigDecimal;

public class ResultPrinter implements ResultPresenter {
    public void showResult(BigDecimal result, UserRequest request) {
        System.out.println("System convert " + request.getSourceCode() + " into " + request.getTargetCode() + " in amount of " + request.getAmount());
        System.out.println(request.getAmount() + " " + request.getSourceCode() + " -> ? " + request.getTargetCode());

        System.out.println("Result is: " + result + " " +  request.getTargetCode());
    }
}
