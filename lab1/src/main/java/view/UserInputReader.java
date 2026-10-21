package view;

import models.UserRequest;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Scanner;

public class UserInputReader implements InputReader{

    public UserRequest readUserInput() {
        Scanner scanner = new Scanner(System.in);
        scanner.useLocale(Locale.US);

        System.out.println("Podaj kod aktualnej waluty");
        String sourceCode = scanner.next();

        System.out.println("Podaj kod docelowej waluty");
        String targetCode = scanner.next();

        System.out.println("Podaj ilosc");

        String rawAmount = scanner.next();
        BigDecimal amount;

        try {
            amount = new BigDecimal(rawAmount.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Given text is not correct number");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater then 0 \n");
        }

        return new UserRequest(sourceCode.toUpperCase(), amount, targetCode.toUpperCase());
    }

}
