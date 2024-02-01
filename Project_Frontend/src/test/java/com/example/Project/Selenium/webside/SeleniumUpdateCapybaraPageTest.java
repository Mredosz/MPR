package com.example.Project.Selenium.webside;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumUpdateCapybaraPageTest {
    WebDriver driver;

    @FindBy(id = "sendUpdateCapybara")
    WebElement updateButton;

    @FindBy(id = "age")
    WebElement ageInput;

    @FindBy(id = "editButton")
    WebElement editButton;

    public static final String  URL ="http://localhost:8081/allCapybara";

    public SeleniumUpdateCapybaraPageTest(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    public void open(){
        driver.get(URL);
    }

    public void editInput(){
        ageInput.clear();
        ageInput.sendKeys("5");
    }

    public void clickEdit(){
        editButton.click();
    }

    public void clickUpdate(){
        updateButton.click();
    }


}
