package com.javarush.cashmachine;


import com.javarush.cashmachine.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + ".common_en");
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String message = null;
        try {
            message = bis.readLine();
            if (message.equalsIgnoreCase("exit")) {
                throw new InterruptOperationException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  message;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String currencyCode;
        while (true) {
            currencyCode = readString();
            if (currencyCode.length() != 3) {
                writeMessage(res.getString("invalid.data"));
            } else {
                break;
            }
        }
        return currencyCode.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        String[] data;
        while (true) {
            writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
            data = readString().split(" ");

            if (data.length != 2) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }

            int nominal = 0;
            int amount = 0;

            try {
                nominal = Integer.parseInt(data[0]);
                amount = Integer.parseInt(data[1]);
            } catch (Exception e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }

            if (nominal <= 0 || amount <= 0) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return data;
    }

    public static Operation askOperation() throws InterruptOperationException {
        Operation operation = null;
        int operationCode = 0;
        while (true) {
            writeMessage(res.getString("choose.operation"));
            writeMessage("1 - " + res.getString("operation.INFO"));
            writeMessage("2 - " + res.getString("operation.DEPOSIT"));
            writeMessage("3 - " + res.getString("operation.WITHDRAW"));
            writeMessage("4 - " + res.getString("operation.EXIT"));
            try {
                operationCode = Integer.parseInt(readString());
                operation = Operation.getAllowableOperationByOrdinal(operationCode);
            } catch (IllegalArgumentException e) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return operation;
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }
}
