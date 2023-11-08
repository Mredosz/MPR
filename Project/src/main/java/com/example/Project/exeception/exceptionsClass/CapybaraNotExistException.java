package com.example.Project.exeception.exceptionsClass;

public class CapybaraNotExistException extends NullPointerException{
    public CapybaraNotExistException() {
        super("Capybara is not exist yet.");
    }
}
