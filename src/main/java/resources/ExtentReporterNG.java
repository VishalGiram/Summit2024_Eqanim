package resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReporterNG {
	public static ExtentReports extent;
	public static ExtentSparkReporter reporter;

	public static ExtentReports getReportObject() {

		String path = System.getProperty("user.dir") + "\\Reports\\index.html";
		reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Auction Platform Automation Results");
		reporter.config().setDocumentTitle("Test Automation Results");
		reporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Eqanim QA");
		extent.setSystemInfo("Enviornment", "QA");
		extent.setSystemInfo("OS", "Windows11");
		extent.setSystemInfo("Browser", "Chrome");
		return extent;

	}

}
