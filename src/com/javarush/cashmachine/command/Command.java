package com.javarush.cashmachine.command;

import com.javarush.cashmachine.exception.InterruptOperationException;

interface Command {
    void execute() throws InterruptOperationException, InterruptOperationException;
}
