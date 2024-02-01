package com.example.Project.Selenium.webside;

import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SeleniumDeleteCapybaraTestPage {
    WebDriver driver;

    @FindBy(id = "deleteButton")
    WebElement deleteButton;

    public static final String  URL ="http://localhost:8081/allCapybara";

    public SeleniumDeleteCapybaraTestPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void open(){
        driver.get(URL);
    }

    public void clickDelete(){
        deleteButton.click();
    }
}
