package com.emaunzpa.webapp.test;

import static org.junit.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestDashboard {
	
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
	public void dashboardValidation() throws InterruptedException {
		
		// Connect to dashboard url
		driver.get("http://localhost:8080/computer-database-webapp/listComputers");
		Thread.sleep(1000);
		
		// Find first computer of the list and test its name
		WebElement computer1 = driver.findElement(By.id("computerName1"));
		assertTrue(computer1.getText().equals("MacBook Pro 15.4 inch"));
		Thread.sleep(1000);
		
	}
	
	@Test
	public void dashboardNavigation() throws InterruptedException {
		
		driver.get("http://localhost:8080/computer-database-webapp/listComputers");
		Thread.sleep(1000);
		
		WebElement endButton = driver.findElement(By.id("endPaginationButton"));
		endButton.click();
		Thread.sleep(1000);
		WebElement startButton = driver.findElement(By.id("startPaginationButton"));
		startButton.click();
		Thread.sleep(1000);
		WebElement nextButton = driver.findElement(By.id("next50PaginationButton"));
		nextButton.click();
		Thread.sleep(1000);
		WebElement previousButton = driver.findElement(By.id("previous50PaginationButton"));
		previousButton.click();
		Thread.sleep(1000);
		previousButton = driver.findElement(By.id("previous10PaginationButton"));
		previousButton.click();
		Thread.sleep(1000);
		WebElement searchBox = driver.findElement(By.id("searchbox"));
		searchBox.sendKeys("Thinking");
		WebElement searchSubmit = driver.findElement(By.id("searchsubmit"));
		searchSubmit.click();
		Thread.sleep(1000);
		WebElement homeTitle = driver.findElement(By.id("homeTitle"));
		assertTrue(homeTitle.getText().equals("6 Computers found ( 6 printed )"));
		WebElement sortByName = driver.findElement(By.className("fa-sort"));
		sortByName.click();
		Thread.sleep(1000);
		sortByName = driver.findElement(By.className("fa-sort"));
		WebElement dropDown = driver.findElement(By.id("navbarDropdown"));
		dropDown.click();
		WebElement frenchFlag = driver.findElement(By.id("frenchFlag"));
		frenchFlag.click();
		dropDown = driver.findElement(By.id("navbarDropdown"));
		dropDown.click();
		WebElement englishFlag = driver.findElement(By.id("englishFlag"));
		englishFlag.click();
		WebElement addComputerButton = driver.findElement(By.id("addComputer"));
		addComputerButton.click();
		Thread.sleep(1000);
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database-webapp/addComputer"));
		
	}
	
	@Test
	public void editModeAndDeleteValidation() throws InterruptedException {
		
		driver.get("http://localhost:8080/computer-database-webapp/listComputers");
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
	
	@Test
	public void addComputerValidation() throws InterruptedException {
		
		driver.get("http://localhost:8080/computer-database-webapp/addComputer");
		Thread.sleep(1000);
		
		WebElement addButton = driver.findElement(By.id("addButton"));
		addButton.click();
		Thread.sleep(1000);
		
		WebElement computerNameError = driver.findElement(By.id("computerName-error"));
		assertTrue(computerNameError.getText().equals("This field is required"));
		
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
		assertTrue(companyIdOptions.size() == 41);
		Thread.sleep(1000);
		
		companyIdSelect.click();
		Thread.sleep(1000);
		companyIdOptions.get(4).click();
		Thread.sleep(1000);
		System.out.println(companyIdOptions.get(4).getText());
		assertTrue(companyIdOptions.get(4).getText().equals("Commodore International"));
		Thread.sleep(1000);
		
		WebElement cancelButton = driver.findElement(By.id("cancelButton"));
		cancelButton.click();
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database-webapp/listComputers"));
		Thread.sleep(1000);
		
	}
	
	@Test
	public void editComputerValidation() throws InterruptedException {
		
		driver.get("http://localhost:8080/computer-database-webapp/editComputer?computerID=2");
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
		assertTrue(computerNameError.getText().equals("This field is required"));
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
		assertTrue(companyIdOptions.size() == 41);
		Thread.sleep(1000);
		
		companyIdSelect.click();
		Thread.sleep(1000);
		companyIdOptions.get(4).click();
		Thread.sleep(1000);
		assertTrue(companyIdOptions.get(3).getText().equals("Tandy Corporation"));
		Thread.sleep(1000);
		
		WebElement cancelButton = driver.findElement(By.id("cancelButton"));
		cancelButton.click();
		assertTrue(driver.getCurrentUrl().equals("http://localhost:8080/computer-database-webapp/listComputers"));
		Thread.sleep(1000);
		
	}
	
	@AfterClass
	public static void quitDriver() {
		driver.quit();
	}

}
