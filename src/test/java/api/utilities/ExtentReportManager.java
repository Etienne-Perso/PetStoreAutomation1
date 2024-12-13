package api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {
	
	public ExtentSparkReporter sparkReporter; //UI of the report
	public ExtentReports extent; //populate common info on the report
	public ExtentTest test; //creating test case in the report and update status of the test methods
	
	String repName; 
	
	public void onStart(ITestContext context) {
		
		String timeStamp= new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); //time Stamp
		repName="Test-Report-"+timeStamp+".html"; 
		
		
		sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName); //specify the location of my report generation 
		
		sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject"); //Title of report
		sparkReporter.config().setReportName("Pet Store Users API"); // Name of the report
		sparkReporter.config().setTheme(Theme.DARK); 
		
		extent=new ExtentReports();
		extent.attachReporter(sparkReporter);
		
		extent.setSystemInfo("Application", "Pet Store Users API");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("User", "Etienne");
		
	}
	
	public void onTestSuccess(ITestResult result) {
		
		test= extent.createTest(result.getName()); //Create a new entry in the report
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.PASS, "Test case PASSED is:"+result.getName()); //update status p/f/s
	}
	
	public void onTestFailure(ITestResult result) {
		
		test= extent.createTest(result.getName()); //Create a new entry in the report
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, "Test case FAILED is:"+result.getName()); //update status p/f/s
		test.log(Status.FAIL, "Test case FAILED is:"+result.getThrowable()); //getting all the failure logs in the report  
	}
	
	
	public void onTestSkipped(ITestResult result) {
		
		test= extent.createTest(result.getName()); //Create a new entry in the report
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, "Test case SKIPPED is:"+result.getName()); //update status p/f/s
	}
	
	
	public void onFinish(ITestContext context) {
		
		extent.flush();
	}
	
	
}