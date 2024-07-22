package com.zoopla.base;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Parameters;

import com.google.common.io.Files;
import com.zoopla.utils.Constants;
import com.zoopla.utils.EnvironmentDetails;
import com.zoopla.utils.ExtentUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BasePage {
	protected static Logger mybasePagelog = LogManager.getLogger();
//protected ExtentUtil reportlog = ExtentUtil.getInstance();
WebDriverWait wait;

public   WebDriver driver;
	
	public BasePage(WebDriver driver) {
		
		this. driver=driver;
		PageFactory.initElements( driver,this);

		
	}
	
	public void takescreenshot(String filepath) {
		// Perform actions with the driver
		// System.out.println("WebDriver instance is null. Please initialize it
		// first.");
		// Other operations...

		TakesScreenshot takescreenshot = (TakesScreenshot)driver;
		File srcFile = takescreenshot.getScreenshotAs(OutputType.FILE);
		File descFile = new File(filepath);

		try {
			Files.copy(srcFile, descFile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
public void enterText( WebElement ele,  String name) {
	
	ele.sendKeys(name);
	
	
}



public void click(WebElement ele,String objname) {

	try {
		assertEquals(true, ele.isEnabled());
		ele.click();
		mybasePagelog.info(objname + "Is enabled and clicked");
	//reportlog.logTestwithPassed(objname + "Is enabled and clicked");
		}catch(AssertionError e) {
			mybasePagelog.error(objname + " is not clickable Please check");
		//	reportlog.logTestwithFailed(objname+ " is not clickable Please check");
}}

	public String getPagetitle() {

		
		return driver.getTitle();
		// mybasePagelog.info("title= " + actTitle);
		

		

	}

	public  String getCurrentURL() {

	return driver.getCurrentUrl();
		
}
	public void waitForclickable(WebElement ele, int time, String Objname) throws Exception {
		try {

			mybasePagelog.info(Objname + " IS WAITED FOR clickable");
			wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.elementToBeClickable(ele));
		} catch (Exception e) {
			mybasePagelog.error(Objname + " did not become visible within the specified time" + e.getMessage());
		//	reportlog.logTestwithFailed(Objname + " did not become visible within the specified time");
			throw e;
		}
	}
	
	public void fluentwait_visibilityOfElement(WebElement ele,int waittime,int polltime)
	{
		
		FluentWait<WebDriver>  fluentWait =new 	FluentWait<WebDriver>(driver);
		fluentWait.withTimeout(Duration.ofMillis(waittime)).pollingEvery(Duration.ofMillis(polltime));
		
		fluentWait.until(ExpectedConditions.visibilityOf(ele));
		
	}
	public void waitForVisibiltyofElementLocated(By ele, int time, String Objname) throws Exception {
		try {

			wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.visibilityOfElementLocated(ele));
			mybasePagelog.info(Objname + " IS WAITED FOR VISIBLITY OF ELEMENT TO BE LOCATED");
		//	reportlog.logTestInfo(Objname + " IS WAITED FOR VISIBLITY OF ELEMENT TO BE LOCATED");
		} catch (Exception e) {
			mybasePagelog.error(Objname + " timeout exception");
			throw e;
		}
	}
	
	public void waitPageToBeLoaded(int time) {
		
		driver.manage().timeouts().pageLoadTimeout(time, TimeUnit.SECONDS);
		
	}
}
