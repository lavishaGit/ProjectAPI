package com.zoopla.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.constant.Constable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.zoopla.utils.Constants;
import com.zoopla.utils.EnvironmentDetails;
import com.zoopla.utils.ExtentUtil;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;


public class Hooks {
   // private static WebDriver driver;


	public static WebDriver driver=null;

	protected static  ExtentUtil reportlog = ExtentUtil.getInstance();

			protected static Logger myBaseTestLog = LogManager.getLogger();
			//WebDriver	driver=BaseTest.getDriver();

	
public static  void intializeSetup(String browser) {
		
		String name=browser.toLowerCase();
		switch(name) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
	
			break;
		case "safari":
			WebDriverManager.safaridriver().setup();
			driver=new SafariDriver();
			break;
		default:
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
			break;
		}
	driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(4,TimeUnit.SECONDS);

		driver.manage().timeouts().pageLoadTimeout(4,TimeUnit.SECONDS);
		
		
	}
	
	
	public static void baseUrl() {
		
	driver.get(EnvironmentDetails.getProperty("baseUrl"));
		
	}
	


	

	@BeforeAll
	public static void before_or_after_all() throws FileNotFoundException, IOException {
		EnvironmentDetails.loadProperties();

		
		
	}
	@Before
	public void setup() throws FileNotFoundException, IOException {
System.out.println("this is called");	
		intializeSetup("safari");
		 baseUrl();
	}
	
	@After()
	public void tearDownAfterEachScenario(){
		driver.manage().deleteAllCookies();
		reportlog.endReport();
		if(driver!=null) {
			driver.close();
		}
		myBaseTestLog.info("******tearDown_LastScenario executed***********");
		reportlog.logTestInfo("******tearDown_LastScenario executed***********");
	}

	
	@AfterStep
	//Before or After Hooks that declare a parameter of this type will receive an instance of this class.
	//It allows writing text and embedding media into reports, as well as inspecting results 
	//(in an After block).


	public void AddScreenshotAfterEachScenario(Scenario  scenario) throws IOException {
		if(scenario.isFailed())
		
		{
			File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			byte[] fileContent= FileUtils.readFileToByteArray(file);
			scenario.attach(fileContent, "image/png", "image");	
		String filepath=new SimpleDateFormat("d:MMM:yy HH:MM:SS").format(new Date());
		String path=Constants.screenshotsFilepath+filepath+".png";
		if(file!=null && file.exists()) {
			File fileDesc=new File(path);
			FileUtils.copyFile(file, fileDesc);
			
			reportlog.logTestfailwithScreenshot(path);

			myBaseTestLog.info("Failed with Screenshot");

		}
		}
		
		
	}
	
	public static WebDriver getDriver() {
		return driver;
	}
	
}
