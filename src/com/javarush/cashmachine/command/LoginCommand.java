package com.javarush.cashmachine.command;


import com.javarush.cashmachine.CashMachine;
import com.javarush.cashmachine.ConsoleHelper;
import com.javarush.cashmachine.exception.InterruptOperationException;

import java.util.ResourceBundle;

public class LoginCommand implements Command {
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".login_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.data"));
            String PAN = ConsoleHelper.readString();
            String PIN = ConsoleHelper.readString();
            if (!PAN.matches("^\\d{12}$") || !PIN.matches("^\\d{4}$")) {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), PAN));
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
                continue;
            }
            if (validCreditCards.containsKey(PAN) && validCreditCards.getString(PAN).equals(PIN)) {
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), PAN));
                break;
            } else {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), PAN));
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                continue;
            }
        }
    }
}
