package com.immedis;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


import org.openqa.selenium.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class BooksModuleTest {
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

        //open books tab
        driver.findElement(By.xpath("//a[@href='/Books']")).click();
    }

    @Test
    void givenBooksPage_whenClickOnBooksButton_shouldSeeAllAvailableBooks() {
        // arrange
        WebElement element = driver.findElement(By.xpath("//a[@href='/Books']"));

        // act
        element.click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//table[@class='table']")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenNewBook_whenNameIsValid_shouldCreateNewBook() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Books']")).click();
        driver.findElement(By.xpath("//a[@href='/Books/Create']")).click();

        // act
        driver.findElement(By.id("Name")).sendKeys("TestName");
        driver.findElement(By.id("Author")).sendKeys("TestAuthor");
        driver.findElement(By.id("Genre")).sendKeys("TestGenre");
        driver.findElement(By.id("Quontity")).sendKeys(Keys.DELETE);
        driver.findElement(By.id("Quontity")).sendKeys("1");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//*[contains(text(), 'TestName')]")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenNewBook_whenQuantityIsNegative_shouldNotCreateNewBook() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Books']")).click();
        driver.findElement(By.xpath("//a[@href='/Books/Create']")).click();

        // act
        driver.findElement(By.id("Name")).sendKeys("TestName");
        driver.findElement(By.id("Author")).sendKeys("TestAuthor");
        driver.findElement(By.id("Genre")).sendKeys("TestGenre");
        driver.findElement(By.id("Quontity")).sendKeys(Keys.DELETE);
        driver.findElement(By.id("Quontity")).sendKeys("-1");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//*[contains(text(), 'TestName')]")).isDisplayed();
        assertThat(displayed).isFalse();
    }

    @Test
    void givenNewBook_whenEmptyFields_shouldNotCreateNewBook() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Books']")).click();
        driver.findElement(By.xpath("//a[@href='/Books/Create']")).click();

        // act
        driver.findElement(By.id("Name")).sendKeys("");
        driver.findElement(By.id("Author")).sendKeys("");
        driver.findElement(By.id("Genre")).sendKeys("");
        driver.findElement(By.id("Quontity")).sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // assert
        boolean displayed = driver.findElement(By.id("Quontity-error")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenNewBook_whenMoreThanExpectedSymbols_shouldNotCreateNewBook() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Books']")).click();
        driver.findElement(By.xpath("//a[@href='/Books/Create']")).click();

        // act
        driver.findElement(By.id("Name")).sendKeys("k".repeat(251));
        driver.findElement(By.id("Author")).sendKeys("k".repeat(101));
        driver.findElement(By.id("Genre")).sendKeys("k".repeat(51));
        driver.findElement(By.id("Quontity")).sendKeys(Keys.DELETE);
        driver.findElement(By.id("Quontity")).sendKeys("1");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//h1[@class='text-danger']")).isDisplayed();
        assertThat(displayed).isFalse();
    }

    @Test
    void givenEditBook_whenClickOnEdit_shouldBeAbleToEditBook() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Books']")).click();

        // act
        driver.findElement(By.xpath("//a[@href='/Books/Edit/44']")).click();
        driver.findElement(By.id("Name")).sendKeys("Test");
        driver.findElement(By.xpath("//input[@value='Save']")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//*[contains(text(), 'Test')]")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenBookDetails_whenClickOnDetails_shouldBeAbleToSeeDetails() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Books']")).click();

        // act
        driver.findElement(By.xpath("//a[@href='/Books/Details/44']")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//h2[contains(text(), 'Details')]")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenDeletingBook_whenClickOnDelete_shouldAskForConfirmation() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Books']")).click();

        // act
        driver.findElement(By.xpath("//a[@href='/Books/Delete/2']")).click();
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