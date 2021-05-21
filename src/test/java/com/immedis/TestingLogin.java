package com.immedis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;


class TestingLogin {

    private static final WebDriver driver = new ChromeDriver();
    private static final String baseURL = "https://qa-task.immedis.com/";

    @BeforeEach
    void setUp() {
        driver.get(baseURL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    void givenLoginPage_whenClickOnUsernameInput_shouldBeEmpty() {
        // act
        driver.findElement(By.name("username")).click();

        // assert
        String value = driver.findElement(By.name("username")).getAttribute("value");
        assertThat(value).isEqualTo(""); // or null
    }

    @Test
    void givenValidCredentials_whenLogin_shouldBeSuccessful() {
        // arrange
        // driver.findElement(By.name("username")).sendKeys(Keys.DELETE);
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");

        // act
        driver.findElement(By.xpath("//div[contains(text(), 'Sign In')]")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//table[@class='table']")).isDisplayed();
        assertThat(displayed).isTrue();
    }

    @Test
    void givenLoginWithRandomCredentials_whenLogin_shouldNotBeSuccessful() {
        // arrange
        driver.findElement(By.name("username")).sendKeys(UUID.randomUUID().toString());
        driver.findElement(By.name("password")).sendKeys(UUID.randomUUID().toString());

        // act
        driver.findElement(By.xpath("//div[contains(text(), 'Sign In')]")).click();

        // assert
        boolean displayedPage = driver.findElement(By.xpath("//a[@href='/Users/Create']")).isDisplayed();
        assertThat(displayedPage).isFalse();
        // boolean displayedPopUp = driver.findElement(By.xpath("TODO")).isDisplayed();
        // assertThat(displayedPopUp);
    }

    @Test
    void givenLoginWithWrongPassword_whenLogin_shouldNotBeSuccessful() {
        // arrange
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys(UUID.randomUUID().toString());

        // act
        driver.findElement(By.xpath("//div[contains(text(), 'Sign In')]")).click();

        // assert
        boolean displayed = driver.findElement(By.xpath("//a[@href='/Users/Create']")).isDisplayed();
        assertThat(displayed).isFalse();
    }

    @Test
    void givenNoCredentialsProvided_whenOpenRestrictedURL_shouldBeUnauthorized() throws IOException {
        // Selenium does not support get Response Code

        // arrange-logOut
        HttpURLConnection c = (HttpURLConnection) new URL(baseURL + "Users").openConnection();
        c.setRequestMethod("GET");

        // act
        c.connect();

        // assert
        assertThat(c.getResponseCode()).isEqualTo(401);
    }


    @Test
    void givenNoAccount_whenClickOnSignUp_shouldCreateAccount() {
        // act
        driver.findElement(By.xpath("//*[contains(text(), 'Sign Up')]")).click();

        // assert
        // boolean signUpScreen = driver.findElement(By.xpath("TODO")).isDisplayed();
        // assertThat(signUpScreen);
    }

    @Test
    void givenUserHasForgotPassword_whenClickOnForgotPassword_shouldChangePassword() {
        // act
        driver.findElement(By.xpath("//a[@href='/Forgot']")).click();

        // assert
        // boolean forgottenPassword = driver.findElement(By.xpath("TODO")).isDisplayed();
        // assertThat(forgottenPassword);

    }

}
