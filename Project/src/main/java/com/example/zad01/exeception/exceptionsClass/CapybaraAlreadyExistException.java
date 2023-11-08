package com.example.zad01.exeception.exceptionsClass;

public class CapybaraAlreadyExistException extends RuntimeException{
    public CapybaraAlreadyExistException(){
        super("This Capybara Already Exist");
    }
}
