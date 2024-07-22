package com.zoopla.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;




public class ExtentUtil {
	protected ExtentReports extent;
	protected ExtentSparkReporter spark;
	protected ExtentTest testlog;
	protected static ExtentUtil extentobj;
	//An element in a stack trace, as returned by Throwable.getStackTrace(). Each element represents a single stack frame.
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
protected Logger logger=LogManager.getLogger();

private ExtentUtil() {}

public  static ExtentUtil getInstance() {
	
	if(extentobj==null) {
		
		extentobj=new ExtentUtil();
		
		
	}
	return extentobj;
	
	
}
public void startExtentReport() {

extent = new ExtentReports();
spark = new ExtentSparkReporter(Constants.EXTENT_FILE);
extent.setSystemInfo("Host Name", "Zoopla");
extent.setSystemInfo("Environment", "QA");
extent.setSystemInfo("User Name", "Lavisha");

spark.config().setDocumentTitle("Test Execution extent");
spark.config().setReportName("ZooplaRegression tests");
spark.config().setTheme(Theme.DARK);

}
public void startExtentCreateReport(String methodname) {
	// this will return methodname to the testlog
	testlog = extent.createTest(methodname);

}

public void endReport() {
	extent.flush();

}public void logTestInfo(String text) {

	logger.info("Logger->"+stackTrace[2].getMethodName());
	//System.out.println("ObjectLogger->" + testlog);// here we are trying to print methodname
	testlog.info(text);

}

public void logTestwithPassed(String text) {
	logger.info("Passed: " + stackTrace[2].getMethodName());

///testlog.pass("ObjectLogger->"+getClass().getSimpleName());//getClass().getSimpleName() retrieves the simple name of the class.

	//System.out.println("ObjectLogger->" + testlog);// here we are trying to print methodname
	testlog.pass(MarkupHelper.createLabel(text, ExtentColor.GREEN));

}

public void logTestfailwithException(Throwable e) {
	logger.error("Logger->" + stackTrace[2].getMethodName());// here we are trying to print methodname
	testlog.fail(e);

}

public void logTestwithFailed(String text) {
	logger.error("Logger->" + stackTrace[2].getMethodName());// here we are trying to print methodname
	testlog.fail(MarkupHelper.createLabel(text, ExtentColor.RED));

}

public void logTestfailwithScreenshot(String filepath) {
	System.out.println("Logger->" + testlog);// here we are trying to print methodname
	testlog.fail(MediaEntityBuilder.createScreenCaptureFromPath(filepath).build());

}}