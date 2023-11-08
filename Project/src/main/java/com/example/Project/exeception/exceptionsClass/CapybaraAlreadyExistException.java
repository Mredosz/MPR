package com.example.Project.exeception.exceptionsClass;

public class CapybaraAlreadyExistException extends RuntimeException{
    public CapybaraAlreadyExistException(){
        super("This Capybara Already Exist");
    }
}
