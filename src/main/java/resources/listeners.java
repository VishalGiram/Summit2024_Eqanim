package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import java.net.URL;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;

import static constants.ThreadConstants.driver;

public class listeners implements ITestListener {
	ExtentReports extent = ExtentReporterNG.getReportObject();
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();
	public static commonLib commonLibRef;
	public static String folderPath;
	File parentDirectory;
	String browserName = "Chrome";
	private static String useGrid;

	public static Properties prop;
	static String os;
	public static String url;
	public static WebDriverWait wait;
	public static JavascriptExecutor js;

	String configFilePath = System.getProperty("user.dir") + "/config.properties";

	public void onExecutionStart() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(configFilePath);
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		readProperties();
	}

	public void readProperties() {
		url = prop.getProperty("QABaseUrl");
		System.out.println("url : " + url);
		useGrid = prop.getProperty("useGrid");
		commonLibRef = new commonLib();
	}

	public void onTestStart(ITestResult result) {
		onExecutionStart();
		System.out.println(browserName);
		if (browserName.equals("Chrome")) {
			initializeWebDriver();
		} else if (browserName.equals("Safari")) {
			initializeWebDriverForSafari();
		}
		initializeExtentTest(result);
	}

	private void initializeFolder() {
		String parentDirectoryPath = System.getProperty("user.dir") + File.separator + "directory";
		System.out.println(parentDirectoryPath);
		parentDirectory = new File(parentDirectoryPath);
		if (!parentDirectory.exists()) {
			System.out.println("Parent directory does not exist.");
		} else {
			String newFolderName = UUID.randomUUID().toString();
			File folder = new File(parentDirectory, newFolderName);
			if (folder.mkdir()) {
				System.out.println("New folder created successfully as: " + folder);
				folderPath = folder.getAbsolutePath();
			} else {
				System.out.println("Failed to create new folder.");
			}
		}
	}

	private void initializeWebDriverForSafari() {
		initializeFolder(); // Ensure folder is created before initializing WebDriver
		Reporter.log("Safari launched", true);
		SafariOptions options = new SafariOptions();
		options.setCapability("safari.cleanSession", true);
		options.setCapability("safari.popups", false);
		options.setCapability("safari.download.dir", folderPath); // Set the download directory here
		WebDriverManager.getInstance(SafariDriver.class).setup();
		driver.set(new SafariDriver(options));
		driver.get().get(url);
		driver.get().manage().window().maximize();
		driver.get().manage().deleteAllCookies();
		driver.get().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver.get(), 90);
		js = (JavascriptExecutor) driver.get();
		waitUntilPageLoaded(js);
		Reporter.log("Navigated to BaseUrl: " + url, true);
	}

	private void initializeWebDriver() {
		initializeFolder(); // Ensure folder is created before initializing WebDriver
		Reporter.log("Chrome launched", true);
		Map<String, Object> prefs = new HashMap<>();
		prefs.put("profile.default_content_settings.popups", 0);
		prefs.put("download.default_directory", folderPath); // Set the download directory here
		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("prefs", prefs);
		if (useGrid.contains("true")) {
			String hubURL = "http://localhost:4444/wd/hub";
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setBrowserName("chrome");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			try {
				driver.set(new RemoteWebDriver(new URL(hubURL), capabilities));
			} catch (MalformedURLException e) {
				e.printStackTrace();
				throw new RuntimeException("Failed to initialize RemoteWebDriver", e);
			}
		} else {
			WebDriverManager.chromedriver().setup();
			driver.set(new ChromeDriver(options));
		}
		driver.get().get(url);
		driver.get().manage().window().maximize();
		driver.get().manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get().manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver.get(), 60);
		js = (JavascriptExecutor) driver.get();
		waitUntilPageLoaded(js);
	}

	private void initializeExtentTest(ITestResult result) {
		ObjectRepo.test = extent
				.createTest(result.getTestClass().getName() + "==" + result.getMethod().getMethodName());
		extentTest.set(ObjectRepo.test);
	}

	public void onTestSuccess(ITestResult result) {
		extentTest.get().log(Status.PASS, "Test Passed");
		extentTest.get().addScreenCaptureFromBase64String(getScreen(), "ScreenShot Pass");
		String testMethodName = result.getMethod().getMethodName();
		System.out.println(testMethodName + "  Test Passed");
		ObjectRepo.test.log(Status.INFO, "ClassName : " + result.getTestClass().getName() + "==" + "MethodName : "
				+ result.getMethod().getMethodName());
		System.out.println("ClassName : " + result.getTestClass().getName() + "==" + "MethodName : "
				+ result.getMethod().getMethodName());
//        TestRailManager.addResultsForTestCase(testCaseId.get(), TestRailManager.TEST_CASE_PASS_STATUS,
//                ", testcase passed through script" + "  Method Name :  " + result.getName() + "  is passed");
		deleteFolder();
//		driver.get().quit();
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		extentTest.get().log(Status.SKIP, "Test Skipped");
		driver.get().quit();
	}

	public void getAllLinks() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.tagName("a")));
		int totalLinks = elements.size();
		for (int i = 0; i < totalLinks - 1; i++) {
			String linkPresent = elements.get(i).getText();
			System.out.println(linkPresent);
		}
	}

	public static void waitUntilPageLoaded(JavascriptExecutor js) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		boolean pageLoaded = false;
		int attempts = 0;
		while (!pageLoaded && attempts < 10) {
			try {
				pageLoaded = (Boolean) js.executeScript("return document.readyState").equals("complete");
				Thread.sleep(1000); // Wait for 1 second before checking again
				attempts++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public String getScreen() {
		return ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.BASE64);
	}

	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable());
		String testMethodName = result.getMethod().getMethodName();
		try {
			System.out.println(testMethodName + "  Test Failed");
		} catch (Exception e) {
			System.out.println(testMethodName + "  Test Failed");
		}
		try {
			extentTest.get().addScreenCaptureFromPath(getScreenShotPath(testMethodName),
					result.getMethod().getMethodName());
		} catch (Exception e) {
			e.printStackTrace();
		}
//		TestRailManager.addResultsForTestCase(testCaseId.get(), TestRailManager.TEST_CASE_FAIL_STATUS,
//				", testcase failed through script" + "   Method Name :  " + result.getName() + "is Failed");
		deleteFolder();
//		driver.get().quit();

	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}

	protected static boolean deleteFolder(File folder) {
		if (!folder.exists()) {
			return true;
		}
		File[] contents = folder.listFiles();
		if (contents != null) {
			for (File file : contents) {
				if (file.isDirectory()) {
					deleteFolder(file);
				} else {
					file.delete();
				}
			}
		}
		return folder.delete();
	}

	public void deleteFolder() {
		if (!parentDirectory.exists()) {
			System.out.println("Parent directory does not exist.");
			return;
		}
		File[] folders = parentDirectory.listFiles(File::isDirectory);
		if (folders != null) {
			for (File folder : folders) {
				if (deleteFolder(folder)) {
					System.out.println("Folder deleted successfully: " + folder.getName());
				} else {
					System.out.println("Failed to delete folder: " + folder.getName());
				}
			}
		} else {
			System.out.println("No folders found in the directory.");
		}
	}

	public String getScreenShotPath(String testCaseName) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver.get();
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir") + "\\Screenshots\\" + testCaseName + ".png";
		FileUtils.copyFile(source, new File(destinationFile));
		return destinationFile;
	}
}
