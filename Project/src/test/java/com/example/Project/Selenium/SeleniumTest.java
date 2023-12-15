package com.example.Project.Selenium;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class SeleniumTest {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new EdgeDriver();
    }

    @Test
    public void fillInForm() {
        SeleniumAddCapybaraPageTest page = new SeleniumAddCapybaraPageTest(driver);
        page.open();
        page.fillInInput();
        page.sendSubmit();
        page.checkThatCapybaraExist();
    }
}
