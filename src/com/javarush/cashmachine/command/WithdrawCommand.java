package com.javarush.cashmachine.command;


import com.javarush.cashmachine.CashMachine;
import com.javarush.cashmachine.ConsoleHelper;
import com.javarush.cashmachine.CurrencyManipulator;
import com.javarush.cashmachine.CurrencyManipulatorFactory;
import com.javarush.cashmachine.exception.InterruptOperationException;
import com.javarush.cashmachine.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".withdraw_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
        Map<Integer, Integer> withdrawMap;

        int amount;
        while (true) {
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            String string = ConsoleHelper.readString();
            try {
                amount = Integer.parseInt(string);
            } catch (NumberFormatException e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            if(amount <= 0) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
            if(!manipulator.isAmountAvailable(amount)) {
                ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                continue;
            }
            try {
                withdrawMap = manipulator.withdrawAmount(amount);
            } catch (NotEnoughMoneyException e ) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
                continue;
            }
            for (Map.Entry<Integer, Integer> pair : withdrawMap.entrySet())
                ConsoleHelper.writeMessage("\t" + pair.getKey() + " - " + pair.getValue());
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), amount, code));
            break;
        }
    }
}
