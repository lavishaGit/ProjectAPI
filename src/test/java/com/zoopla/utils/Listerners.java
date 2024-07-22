package com.zoopla.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.zoopla.base.BasePage;
import com.zoopla.base.Hooks;



public class Listerners   implements ITestListener {
	Logger ListenerLog = LogManager.getLogger();
	private static ExtentUtil report = ExtentUtil.getInstance();
	public void onTestStart(ITestResult result) {
		ListenerLog.info(result.getName()+".......<test> execution started........");
		report.startExtentCreateReport(result.getMethod().getMethodName()+" is report created") ;//
		// TODO Auto-generated method stub
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		report.logTestwithPassed(result.getMethod().getMethodName()+" is passed");
	}

	public void onTestFailure(ITestResult result) {
		ListenerLog.error(result.getMethod().getMethodName() + ".......test execution completed with failure........");
		report.logTestwithFailed(
				result.getMethod().getMethodName() + ".......test execution completed with failure........");
		String filename = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		String path = Constants.screenshotsFilepath + filename + ".png";
		BasePage.takescreenshot(path);
		report.logTestfailwithScreenshot(path);
		report.logTestfailwithException(result.getThrowable());

	}

	public void onStart(ITestContext context) {
		ListenerLog.info(".......<test> execution started........");
		report.startExtentReport();
	}
	public void onFinish(ITestContext context) {
		ListenerLog.info(".......<test> execution completed........");
		report.endReport();
		// TODO Auto-generated method stub
	}
	
	

}
