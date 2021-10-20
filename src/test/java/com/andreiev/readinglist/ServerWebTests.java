package com.andreiev.readinglist;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReadingListApplication.class)
@SpringBootTest(classes = ReadingListApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServerWebTests {

    private static FirefoxDriver browser;

    @Value("${local.server.port}")
    private int port;

    @BeforeAll
    public static void openBrowser() {
        System.setProperty("webdriver.gecko.driver", "/home/oleksandrandreiev/bin/geckodriver");
        browser = new FirefoxDriver();
        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // sets up Firefox driver
    }

    @AfterAll
    public static void closeBrowser() {
        browser.quit(); // shuts down browser
    }

    @Test
    public void addBookToEmptyList() {
        String baseUrl = "http://localhost:" + port + "/readers/testReader";

        browser.get(baseUrl); // fetches the home page
        var text = browser.getTitle();
        text = browser.findElementByTagName("div").getText();
     //   browser.
        assertEquals("You have no books in your book list",
                browser.findElementByTagName("div").getText()); // asserts an empty book list

        browser.findElementByName("title")
                .sendKeys("BOOK TITLE");

        browser.findElementByName("author")
                .sendKeys("BOOK AUTHOR");

        browser.findElementByName("isbn")
                .sendKeys("1234567890");

        browser.findElementByName("description")
                .sendKeys("DESCRIPTION");

        browser.findElementByTagName("form").submit(); // fills in and submits form

        var dl = browser.findElementByCssSelector("dt.bookHeadline");
        assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)", dl.getText());

        var dt = browser.findElementByCssSelector("dd.bookDescription");
        assertEquals("DESCRIPTION", dt.getText()); // asserts new book list

    }
}
