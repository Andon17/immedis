package com.immedis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GettingABookTest {
    private static final WebDriver driver = new ChromeDriver();
    private static final String baseURL = "https://qa-task.immedis.com/";

    @BeforeEach
    void setUp() {

        driver.get(baseURL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        //valid login
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.xpath("//div[contains(text(), 'Sign In')]")).click();

        if(isElementExists(By.xpath("//button[@class='btn btn-default navbar-btn']"))) {
            driver.findElement(By.xpath("//button[@class='btn btn-default navbar-btn']")).click();
        }

        //open getting a book tab
        driver.findElement(By.xpath("//a[@href='/GetBook']")).click();
    }

    @Test
    void givenGetABookPage_whenClickOnGetABookButton_shouldSeeAllBookRequest() {
        // arrange
        WebElement element = driver.findElement(By.xpath("//a[@href='/GetBook']"));

        // act
        element.click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//table[@class='table']")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenRequestPage_whenAddANewRequest_shouldDecreaseBookQuantity() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/GetBook']")).click();

        // act
        driver.findElement(By.xpath("//a[@href='/GetBook/Create']")).click();
        Select s = new Select(driver.findElement(By.id("UserId")));
        s.selectByValue("50");
        Select s1 = new Select(driver.findElement(By.id("BookId")));
        s.selectByValue("50");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // assert

    }

    @Test
    void givenEditBookRequest_whenClickOnEdit_shouldBeAbleToEditBookRequest() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/GetBook']")).click();

        // act
        driver.findElement(By.xpath("//a[@href='/GetBook/Edit/113']")).click();
        Select s = new Select(driver.findElement(By.id("UserId")));
        s.selectByValue("60");
        Select s1 = new Select(driver.findElement(By.id("BookId")));
        s.selectByValue("60");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // assert

    }

    @Test
    void givenDetailsRecord_whenClickOnDetails_shouldBeAbleToSeeRequestsDetails() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/GetBook']")).click();

        // act
        driver.findElement(By.xpath("//a[@href='/GetBook/Details/113']")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//h2[contains(text(), 'Details')]")).isDisplayed();
        assertThat(displayed).isTrue();
    }


    @Test
    void givenDeletingRecord_whenClickOnDelete_shouldAskForConfirmation() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/GetBook']")).click();

        // act
        driver.findElement(By.xpath("//a[@href='/GetBook/Delete/113']")).click();
        driver.findElement(By.xpath("//input[@value='Delete']")).click();

        // assert
        // boolean displayed = driver.findElement(By.xpath("TODO").isDisplayed();
        // assertThat(displayed).isTrue();
    }

    public boolean isElementExists(By by) {
        boolean isExists = true;
        try {
            driver.findElement(by);
        } catch (NoSuchElementException e) {
            isExists = false;
        }
        return isExists;
    }

}