package com.emaunzpa.webapp.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestAddUser {

	private static WebDriver driver;
	private static Properties properties;
	private static FileInputStream inputStream;
	
	@BeforeClass
	public static void setUp() throws InterruptedException {
		System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
		driver = new FirefoxDriver();
		properties = new Properties();
		try {
			inputStream = new FileInputStream("resources/users.properties");
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		driver.get("http://localhost:8080/computer-database-webapp/login");
		Thread.sleep(1000);
		
		WebElement usernameInput = driver.findElement(By.id("username"));
		usernameInput.sendKeys(properties.getProperty("admin.username"));
		WebElement userpassword = driver.findElement(By.id("password"));
		userpassword.sendKeys(properties.getProperty("admin.pwd"));
		Thread.sleep(1000);
		WebElement loginButton = driver.findElement(By.className("btn"));
		loginButton.click();
		Thread.sleep(1000);
		
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database-webapp/"));
	}
	
	@Test
	public void test() throws InterruptedException {

		driver.get("http://localhost:8080/computer-database-webapp/addUser");
		
		WebElement username = driver.findElement(By.id("userName"));
		username.sendKeys("Username generated with selenium");
		WebElement password = driver.findElement(By.id("password"));
		WebElement passwordConfirm = driver.findElement(By.id("passwordConfirm"));
		password.sendKeys("password");
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.id("passwordError")).getText().equals("Password must contains at least : One special character, one upper case, one lower case, one number"));
		password.clear();
		password.sendKeys("p");
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.id("passwordError")).getText().equals("Minimal length : 8"));
		password.clear();
		password.sendKeys("Password@123");
		Thread.sleep(1000);
		passwordConfirm.sendKeys("password");
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.id("passwordConfirmError")).getText().equals("Passwords must be same"));
		passwordConfirm.clear();
		passwordConfirm.sendKeys("Password@123");
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.id("passwordConfirmError")).getText().equals("OK !"));
	}
	
	@AfterClass
	public static void quitDriver() {
		driver.quit();
	}

}
