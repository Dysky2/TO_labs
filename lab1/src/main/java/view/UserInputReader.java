package view;

import models.UserRequest;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserInputReader implements InputReader{

    public UserRequest readUserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj kod aktualnej waluty");
        String sourceCode = scanner.next();

        System.out.println("Podaj kod docelowej waluty");
        String targetCode = scanner.next();

        System.out.println("Podaj ilosc");
        int amount = scanner.nextInt();

        return new UserRequest(sourceCode, BigDecimal.valueOf(amount), targetCode);
    }

}
