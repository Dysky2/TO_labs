package view;

import models.UserRequest;

import java.math.BigDecimal;

public interface ResultPresenter {
    public void showResult(BigDecimal result, UserRequest userRequest);
}
