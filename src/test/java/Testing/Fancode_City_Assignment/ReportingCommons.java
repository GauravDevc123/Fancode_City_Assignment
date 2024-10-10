package Testing.Fancode_City_Assignment;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportingCommons {
	ExtentReports extent;
	ExtentSparkReporter report;
	ExtentTest test;
	public ReportingCommons() {
		extent = new ExtentReports();
		report = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/extentReport.html");
		extent.attachReporter(report);
	}
	
	public ExtentTest createReportTest(String testName, String Description) {
		test = extent.createTest(testName, Description);
		return test;
	}
	
	public void logReport() {
		extent.flush();
	}
	
	public void passTestStep(String messageText) {
		test.pass(messageText);
	}
	
	public void failTestStep(String messageText) {
		test.fail(messageText);
	}
	
	public void informativeStep(String messageText) {
		test.info(messageText);
	}
	
	public void skipTestStep(String messageText) {
		test.skip(messageText);
	}

}