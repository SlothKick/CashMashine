package com.javarush.cashmachine.command;


import com.javarush.cashmachine.CashMachine;
import com.javarush.cashmachine.ConsoleHelper;
import com.javarush.cashmachine.CurrencyManipulator;
import com.javarush.cashmachine.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.ResourceBundle;

class InfoCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".info_en");

    @Override
    public void execute() {
        boolean hasMoney = false;
        ConsoleHelper.writeMessage(res.getString("before"));
        Collection<CurrencyManipulator> manipulators = CurrencyManipulatorFactory.getAllCurrencyManipulators();

        for(CurrencyManipulator manipulator : manipulators) {
            if (manipulator.hasMoney()) {
                if(manipulator.getTotalAmount() > 0) {
                    hasMoney = true;
                    ConsoleHelper.writeMessage(manipulator.getCurrencyCode().toUpperCase() + " - " + manipulator.getTotalAmount());
                }
            }
        }

        if (!hasMoney) {
            ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }
}
