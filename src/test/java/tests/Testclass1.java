package tests;

import org.apache.poi.util.SystemOutLogger;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import static constants.ThreadConstants.driver;

import pages.POMCLass;

@Listeners(resources.listeners.class)
public class Testclass1 {

	@Test
	public void start() throws JsonProcessingException {
		POMCLass ref = new POMCLass();
		ref.clickOnPoliticsElement();
		ref.clickOnSlickSlideControl10();
		ref.getHeadline();
		ref.getHref();
		ref.getHeadlineelementclick();
		ref.getDate();
		driver.get().navigate().back();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ref.clickOnSlickSlideControl11();
		ref.getHeadline2();
		ref.getHref2();
		ref.getHeadlineelementclick2();
		ref.getDate();
		driver.get().navigate().back();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ref.clickOnSlickSlideControl12();
//		ref.getHeadline3();
//		ref.getHref3();
//		ref.getHeadlineelementclick3();
//		ref.getDate();
//		driver.get().navigate().back();
//		ref.createDataJson();
		ref.getIDusingAPI();
		ref.getdataUsingID();
		ref.compareJson();
	}
}
