package com.emaunzpa.computerdatabase.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/*
 * Tests of the various Web-UI actions with Selenium
 */
public class TestWebUI {

	private static WebDriver driver;
	
	@Test
	public void dashboardalidation() throws InterruptedException {
		
		// Launch selenium firefox driver and connect to dashboard url
		System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
		driver = new FirefoxDriver();
		driver.get("http://localhost:8080/computer-database/views/listComputers");
		Thread.sleep(1000);
		
		// Find first computer of the list and test its name
		WebElement computer1 = driver.findElement(By.id("computerName1"));
		assertTrue(computer1.getText().equals("MacBook Pro 15.4 inch"));
		Thread.sleep(1000);
		
		// Test various buttons of pagination
		
		// End Button
		WebElement endButton = driver.findElement(By.id("endPaginationButton"));
		endButton.click();
		Thread.sleep(1000);
		// Start Button
		WebElement startButton = driver.findElement(By.id("startPaginationButton"));
		startButton.click();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/listComputers?startIndex=0&endIndex=10"));
		// Next Button
		WebElement nextButton = driver.findElement(By.id("nextPaginationButton"));
		nextButton.click();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/listComputers?startIndex=10&endIndex=20"));
		// Previous Button
		WebElement previousButton = driver.findElement(By.id("previousPaginationButton"));
		previousButton.click();
		Thread.sleep(1000);
		previousButton = driver.findElement(By.id("previousPaginationButton"));
		previousButton.click();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/listComputers?startIndex=0&endIndex=10"));
		
		// Add Computer link
		WebElement addComputerButton = driver.findElement(By.id("addComputer"));
		addComputerButton.click();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/addComputer"));

		// Quit driver
		driver.quit();
		
	}
	
}
