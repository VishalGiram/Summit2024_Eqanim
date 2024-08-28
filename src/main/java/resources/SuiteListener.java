package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.Reporter;

public class SuiteListener implements ISuiteListener {

	public static Properties prop;
	static String os;
	public static String url;
	
	String configFilePath  = System.getProperty("user.dir") + "/config.properties";


	@Override
	public void onStart(ISuite suite) {
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
	}

	public static void checkUrl(String url) {
		if (url == null) {
			Reporter.log("Url is empty", true);
			Assert.fail("Url is empty");
		}
	}
}
