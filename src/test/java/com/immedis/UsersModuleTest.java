package com.immedis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UsersModuleTest {

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
    }

    @Test
    void givenUserPage_whenClickOnUserButton_shouldSeeAllAvailableUsers() {
        // arrange
        WebElement element =  driver.findElement(By.xpath("//a[@href='/Users']"));

        // act
        driver.findElement(By.xpath("//button[@class='btn btn-default navbar-btn']")).click();
        element.click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//table[@class='table']")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenNewUser_whenNameIsValid_shouldCreateNewUser() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Users/Create']")).click();

        // act
        driver.findElement(By.id("Name")).sendKeys("newUser");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//*[contains(text(), 'newUser')]")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenNewUser_whenNameIsEmpty_shouldNotCreateNewUser() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Users/Create']")).click();

        // act
        driver.findElement(By.id("Name")).sendKeys("");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//h1[@class='text-danger']")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenNewUser_whenNameIsMoreThan100Characters_shouldNotCreateNewUser() {
        // arrange
        driver.findElement(By.xpath("//a[@href='/Users/Create']")).click();

        // act
        driver.findElement(By.id("Name")).sendKeys("k".repeat(101));
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//*[contains(text(), 'k.repeat(101)')]")).isDisplayed();
        assertThat(displayed).isFalse();
    }

    @Test
    void givenTryToEdit_whenClickOnEdit_shouldEditUser() {

        driver.findElement(By.xpath("//a[@href='/Home/Edit/9']")).click();

        // boolean editUser = driver.findElement(By.xpath("TODO")).isDisplayed();
        // assertThat(editUser);
    }

    @Test
    void givenTryToDelete_whenClickOnDelete_shouldDelete() {

        driver.findElement(By.xpath("//a[@href='/api/Users/9']")).click();

        // boolean editUser = driver.findElement(By.xpath("TODO")).isDisplayed();
        // assertThat(editUser);
    }

    @Test
    void givenTrySeeDetails_whenClickOnDetails_shouldSeeDetails() {

        driver.findElement(By.xpath("//a[@href='/Users/Details/9']")).click();

        boolean userDetails = driver.findElement(By.xpath("//dt[contains(text(), 'Name')]")).isDisplayed();
        assertThat(userDetails).isTrue();
    }
}