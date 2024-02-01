package com.example.Project.Selenium;

import com.example.Project.Selenium.webside.SeleniumAddCapybaraPageTest;
import com.example.Project.Selenium.webside.SeleniumDeleteCapybaraTestPage;
import com.example.Project.Selenium.webside.SeleniumUpdateCapybaraPageTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeleniumTest {
    WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown(){
        driver.close();
    }

    @Test
    public void fillInForm() {
        SeleniumAddCapybaraPageTest page = new SeleniumAddCapybaraPageTest(driver);
        page.open();
        page.fillInInput();
        page.sendSubmit();
//        page.checkThatCapybaraExist();
    }

    @Test
    public void editCapybara() {
        SeleniumUpdateCapybaraPageTest page = new SeleniumUpdateCapybaraPageTest(driver);
        page.open();
        page.clickEdit();
        page.editInput();
        page.clickUpdate();
    }

    @Test
    public void deleteCapybara(){
        SeleniumDeleteCapybaraTestPage page = new SeleniumDeleteCapybaraTestPage(driver);
        page.open();
        page.clickDelete();
    }
}
