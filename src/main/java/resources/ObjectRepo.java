package resources;

import org.testng.annotations.Listeners;

import com.aventstack.extentreports.ExtentTest;

@Listeners(resources.listeners.class)
public class ObjectRepo {

	public static ExtentTest test;

}
