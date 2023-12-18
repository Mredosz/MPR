package com.example.Project.Selenium;

import com.example.Project.service.MyRestService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumAddCapybaraPageTest {
    WebDriver driver;
    MyRestService service;

    @FindBy(id = "name")
    WebElement nameInput;

    @FindBy(id = "age")
    WebElement ageInput;

    @FindBy(id = "send")
    WebElement sendSubmit;

    public static final String  URL ="http://localhost:8080/addCapybara";
    public static final String  URLALL ="http://localhost:8080/allCapybara";

    public SeleniumAddCapybaraPageTest(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void open(){
        driver.get(URL);
        driver.get(URLALL);
    }

    public void fillInInput(){
        nameInput.sendKeys("Adam");
        ageInput.clear();
        ageInput.sendKeys("5");
    }

    public void sendSubmit(){
        sendSubmit.click();
    }

    public void checkThatCapybaraExist(){
        service.getCapybaraByName("Adam");

    }

}
