package com.emaunzpa.computerdatabase.test;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
	
	@BeforeClass
	public static void setUp() {
		// Geckodriver.exe has to be added to resource folder
		System.setProperty("webdriver.gecko.driver", "resources/geckodriver");
		driver = new FirefoxDriver();
	}
	
	/**
	 * Test dashboard display and elements
	 * @throws InterruptedException
	 */
	@Test
	public void dashboardvalidation() throws InterruptedException {
		
		// Connect to dashboard url
		driver.get("http://localhost:8080/computer-database/views/listComputers");
		Thread.sleep(1000);
		
		// Find first computer of the list and test its name
		WebElement computer1 = driver.findElement(By.id("computerName1"));
		assertTrue(computer1.getText().equals("MacBook Pro 15.4 inch"));
		Thread.sleep(1000);
		
	}
	
	/**
	 *  Test various buttons of pagination
	 * @throws InterruptedException 
	 */
	@Test
	public void dashboardNavigation() throws InterruptedException {
		
		driver.get("http://localhost:8080/computer-database/views/listComputers");
		Thread.sleep(1000);
		
		// End Button
		WebElement endButton = driver.findElement(By.id("endPaginationButton"));
		endButton.click();
		Thread.sleep(1000);
		// Start Button
		WebElement startButton = driver.findElement(By.id("startPaginationButton"));
		startButton.click();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/listComputers?startIndex=0&endIndex=10&search=&sorted="));
		// Next Button
		WebElement nextButton = driver.findElement(By.id("next50PaginationButton"));
		nextButton.click();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/listComputers?startIndex=50&endIndex=60&search=&sorted="));
		// Previous Button
		WebElement previousButton = driver.findElement(By.id("previous50PaginationButton"));
		previousButton.click();
		Thread.sleep(1000);
		previousButton = driver.findElement(By.id("previous10PaginationButton"));
		previousButton.click();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/listComputers?startIndex=0&endIndex=10&search=&sorted="));
		//Searchbox
		WebElement searchBox = driver.findElement(By.id("searchbox"));
		searchBox.sendKeys("Thinking");
		WebElement searchSubmit = driver.findElement(By.id("searchsubmit"));
		searchSubmit.click();
		Thread.sleep(1000);
		WebElement homeTitle = driver.findElement(By.id("homeTitle"));
		assertTrue(homeTitle.getText().equals("6 Computers found ( 6 printed )"));
		//Sort by name
		WebElement sortByName = driver.findElement(By.className("fa-sort"));
		sortByName.click();
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.id("computerName1")).getText().equals("CM-2"));
		sortByName = driver.findElement(By.className("fa-sort"));
		sortByName.click();
		Thread.sleep(1000);
		assertTrue(driver.findElement(By.id("computerName1")).getText().equals("Connection Machine"));
		// Add Computer link
		WebElement addComputerButton = driver.findElement(By.id("addComputer"));
		addComputerButton.click();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/addComputer"));
		
	}
	
	/**
	 * Validation for edit mode and computer delete
	 * @throws InterruptedException
	 */
	@Test
	public void editModeAndDeleteValidation() throws InterruptedException {
		
		driver.get("http://localhost:8080/computer-database/views/listComputers");
		Thread.sleep(1000);
		
		WebElement editButton = driver.findElement(By.id("editComputer"));
		editButton.click();
		Thread.sleep(1000);

		WebElement selectAll = driver.findElement(By.id("selectall"));
		selectAll.click();
		Thread.sleep(1000);

		WebElement deleteButton = driver.findElement(By.id("deleteComputer"));
		deleteButton.click();
		Thread.sleep(1000);

		WebElement numberOfComputers = driver.findElement(By.id("numberOfSelectedComputers"));
		assertTrue(numberOfComputers.getText().equals("10"));
		
		WebElement deleteCancel = driver.findElement(By.id("deleteCancel"));
		deleteCancel.click();
		
	}
	
	/**
	 * Validation for the adding computer Web-UI
	 * @throws InterruptedException
	 */
	@Test
	public void addComputerValidation() throws InterruptedException {
		
		driver.get("http://localhost:8080/computer-database/views/addComputer");
		Thread.sleep(1000);
		
		WebElement addButton = driver.findElement(By.id("addButton"));
		addButton.click();
		Thread.sleep(1000);
		
		WebElement computerNameError = driver.findElement(By.id("computerName-error"));
		assertTrue(computerNameError.getText().equals("This field is required."));
		
		WebElement computerName = driver.findElement(By.id("computerName"));
		computerName.sendKeys("ComputerName generated with selenium");
		Thread.sleep(1000);
		
		WebElement introducedDate = driver.findElement(By.id("introduced"));
		introducedDate.sendKeys("2019-03-21");
		System.out.println(introducedDate.getText());
		Thread.sleep(1000);
		
		WebElement discontinuedDate = driver.findElement(By.id("discontinued"));
		discontinuedDate.sendKeys("2019-03-19");
		System.out.println(discontinuedDate.getText());
		Thread.sleep(1000);
		
		addButton.click();
		WebElement discontinuedError = driver.findElement(By.id("discontinued-error"));
		assertTrue(discontinuedError.getText().equals("Must be coherent with introduced date"));
		Thread.sleep(1000);
		
		WebElement companyIdSelect = driver.findElement(By.id("companyId"));
		Thread.sleep(1000);
		
		List<WebElement> companyIdOptions = driver.findElements(By.className("companyId-option"));
		assertTrue(companyIdOptions.size() == 42);
		Thread.sleep(1000);
		
		companyIdSelect.click();
		Thread.sleep(1000);
		companyIdOptions.get(4).click();
		Thread.sleep(1000);
		assertTrue(companyIdOptions.get(4).getText().equals("Tandy Corporation"));
		Thread.sleep(1000);
		
		WebElement cancelButton = driver.findElement(By.id("cancelButton"));
		cancelButton.click();
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/listComputers"));
		Thread.sleep(1000);
		
	}
	
	@Test
	public void editComputerValidation() throws InterruptedException {
		
		driver.get("http://localhost:8080/computer-database/views/editComputer?computerID=2");
		Thread.sleep(1000);
		
		String computerName = driver.findElement(By.id("computerName")).getAttribute("value");
		assertTrue(computerName.equals("CM-2a"));
		Thread.sleep(1000);
		
		String companyName = driver.findElement(By.id("companyId")).getAttribute("value");
		assertTrue(companyName.equals("2"));
		
		driver.findElement(By.id("computerName")).clear();
		WebElement editButton = driver.findElement(By.id("editButton"));
		editButton.click();
		Thread.sleep(1000);
		
		WebElement computerNameError = driver.findElement(By.id("computerName-error"));
		assertTrue(computerNameError.getText().equals("This field is required."));
		Thread.sleep(1000);
		
		WebElement discontinuedDate = driver.findElement(By.id("discontinued"));
		discontinuedDate.sendKeys("2019-03-19");
		System.out.println(discontinuedDate.getText());
		Thread.sleep(1000);
		
		editButton.click();
		WebElement discontinuedError = driver.findElement(By.id("discontinued-error"));
		assertTrue(discontinuedError.getText().equals("Must be coherent with introduced date"));
		Thread.sleep(1000);
		
		WebElement companyIdSelect = driver.findElement(By.id("companyId"));
		Thread.sleep(1000);
		
		List<WebElement> companyIdOptions = driver.findElements(By.className("companyId-option"));
		assertTrue(companyIdOptions.size() == 42);
		Thread.sleep(1000);
		
		companyIdSelect.click();
		Thread.sleep(1000);
		companyIdOptions.get(4).click();
		Thread.sleep(1000);
		assertTrue(companyIdOptions.get(4).getText().equals("Tandy Corporation"));
		Thread.sleep(1000);
		
		WebElement cancelButton = driver.findElement(By.id("cancelButton"));
		cancelButton.click();
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database/views/listComputers"));
		Thread.sleep(1000);
		
	}
	
	@AfterClass
	public static void quitDriver() {
		driver.quit();
	}
	
}
