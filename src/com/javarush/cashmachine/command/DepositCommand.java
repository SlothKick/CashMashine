package com.javarush.cashmachine.command;

import com.javarush.cashmachine.CashMachine;
import com.javarush.cashmachine.ConsoleHelper;
import com.javarush.cashmachine.CurrencyManipulator;
import com.javarush.cashmachine.CurrencyManipulatorFactory;
import com.javarush.cashmachine.exception.InterruptOperationException;

import java.util.ResourceBundle;

class DepositCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".deposit_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        String[] digits = ConsoleHelper.getValidTwoDigits(code);
        CurrencyManipulator cm = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
        try {
            int k = Integer.parseInt(digits[0]);
            int l = Integer.parseInt(digits[1]);
            cm.addAmount(k , l);
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), k*l, code));

        } catch (Exception e) {
            e.printStackTrace();
            ConsoleHelper.writeMessage(res.getString("invalid.data"));
        }
    }
}
