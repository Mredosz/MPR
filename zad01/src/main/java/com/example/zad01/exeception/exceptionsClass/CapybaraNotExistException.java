package com.example.zad01.exeception.exceptionsClass;

public class CapybaraNotExistException extends NullPointerException{
    public CapybaraNotExistException() {
        super("Capybara is not exist yet.");
    }
}
