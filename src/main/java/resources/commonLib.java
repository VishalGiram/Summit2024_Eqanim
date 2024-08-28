package resources;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import static constants.ThreadConstants.driver;
public class commonLib{
	
	public void pageLoader() {
		WebDriverWait wait1 = new WebDriverWait(driver.get(),120);
		String loaderXPath = "//*[contains(@class,'MuiCircularProgress-')]";
		wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(loaderXPath)));
	}
    
	public void click(WebElement element, String clickType, String elementName) {
		try {
			switch (clickType) {
			case "seleniumClick":
				element.click();
				break;
			case "javascriptClick":
				JavascriptExecutor executor = (JavascriptExecutor) driver.get();
				executor.executeScript("arguments[0].click();", element);
				break;
			case "moveToElementClick":
				Actions actions = new Actions(driver.get());
				actions.doubleClick(element).perform();
				break;
			}
			ObjectRepo.test.log(Status.PASS, elementName + " has been clicked");
			ObjectRepo.test.log(Status.INFO, elementName + " has been clicked");
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail();
		}
	}

	public void scroll(WebElement element, String scrollType, String elementName) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) driver.get();
			switch (scrollType) {
			case "scrollToElement":
				js.executeScript("arguments[0].scrollIntoView();", element);
				break;
			case "scrollToBottomOFPage":
				js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				break;
			case "scrollToTopOFPage":
				js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
				break;
			}
			ObjectRepo.test.log(Status.INFO, elementName + " has been scrolled");
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail();
		}
	}

	public void typeText(WebElement element, String value, String elementName) {
		try {
			element.clear();
			element.sendKeys(value);
			ObjectRepo.test.log(Status.PASS, elementName + " has been entered");
			ObjectRepo.test.log(Status.INFO, elementName + " has been entered");
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail();
		}
	}

	public String getTextElement(WebElement element) {
		try {
			return (element.getText());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void isDisplayed(WebElement element, String elementName) {
		boolean actualResult = element.isDisplayed();
		if (actualResult == false) {
			ObjectRepo.test.log(Status.PASS, elementName + " has not been displayed!!!");
			ObjectRepo.test.log(Status.INFO, elementName + " has not been displayed!!!");
			Assert.assertFalse(false, elementName + " has not been displayed!!!");
		} else {
			ObjectRepo.test.log(Status.PASS, elementName + " has been displayed!!!");
			ObjectRepo.test.log(Status.INFO, elementName + " has been displayed!!!");
		}

	}

	public void isEnabled(WebElement element, String elementName) {
		boolean actualResult = element.isEnabled();
		if (actualResult == false) {
			ObjectRepo.test.log(Status.PASS, elementName + " has not been enabled!!!");
			ObjectRepo.test.log(Status.INFO, elementName + " has not been enabled!!!");
			Assert.assertFalse(false, elementName + " has not been enabled!!!");
		} else {
			ObjectRepo.test.log(Status.PASS, elementName + " has been enabled!!!");
			ObjectRepo.test.log(Status.INFO, elementName + " has been enabled!!!");
		}
	}

	public void verifyUrl(String actualUrl, String expectedUrl, String reportStep, String message) {
		Assert.assertEquals(actualUrl, expectedUrl, message);
		ObjectRepo.test.log(Status.PASS, message);
		ObjectRepo.test.log(Status.INFO, message);
	}

	public void verifyTextTypeInt(int actualValue, int expectedValue, String reportStep, String message) {
		Assert.assertEquals(actualValue, expectedValue, message);
		ObjectRepo.test.log(Status.PASS, message);
		ObjectRepo.test.log(Status.INFO, message);
	}

	public void selectValueFromSelectDropDown(WebElement element, String elementName, String value) {
		try {
			Select ele = new Select(element);
			ele.selectByValue(value);
			ObjectRepo.test.log(Status.PASS, elementName + " has been select");
			ObjectRepo.test.log(Status.INFO, elementName + " has been select");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void verifyTextTypeString(String actualString, String expectedString, String reportStep, String message) {
		Assert.assertEquals(actualString, expectedString, message);
		ObjectRepo.test.log(Status.PASS, message);
		ObjectRepo.test.log(Status.INFO, message);
	}

	public void selectIndexFromDropDown(WebElement element, String elementName, int index) {
		try {
			Select ele = new Select(element);
			ele.selectByIndex(index);
			ObjectRepo.test.log(Status.PASS, elementName + " has been select");
			ObjectRepo.test.log(Status.INFO, elementName + " has been select");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void selectVisibleTextFromDropDown(WebElement element, String elementName, String visibleText) {
		try {
			Select ele = new Select(element);
			ele.selectByVisibleText(visibleText);
			ObjectRepo.test.log(Status.PASS, elementName + " has been select");
			ObjectRepo.test.log(Status.INFO, elementName + " has been select");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSelectedTextFromDropDown(WebElement element, String elementName) {
		String dropValue = null;
		try {
			Select ele = new Select(element);
			dropValue = ele.getFirstSelectedOption().getText();
			ObjectRepo.test.log(Status.PASS, elementName + " is selected");
			ObjectRepo.test.log(Status.INFO, elementName + " is not selected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dropValue;
	}

	public void isDisabled(WebElement element, String elementName) {
		boolean actualResult = element.isEnabled();
		if (actualResult == false) {
			ObjectRepo.test.log(Status.PASS, elementName + " is disabled!!!");
			ObjectRepo.test.log(Status.INFO, elementName + " is disabled!!!");

		} else {
			ObjectRepo.test.log(Status.PASS, elementName + " is not disabled!!!");
			ObjectRepo.test.log(Status.INFO, elementName + " is not disabled!!!");
			// Assert.assertFalse(false, elementName + " is not disabled!!!");
			Assert.fail(elementName + " is not disabled!!!");
		}
	}

	public void isTextPresent(String message, WebElement element, String elementName) {
		message = element.getText();
		if (message.length() > 0 && message != null) {
			ObjectRepo.test.log(Status.PASS, "Text is present on" + elementName);
			ObjectRepo.test.log(Status.INFO, "Text is present on " + elementName);

		} else {
			ObjectRepo.test.log(Status.PASS, "Text is not present on" + elementName);
			ObjectRepo.test.log(Status.INFO, " Text is not present on" + elementName);
		}
	}

	public void switchToIframe(WebElement element, String elementName, int n) {
		int iframeindex = driver.get().findElements(By.tagName("iframe")).size();
		if (iframeindex >= 0) {
			driver.get().switchTo().frame(n);
			ObjectRepo.test.log(Status.PASS, elementName + "iframe is present");
			ObjectRepo.test.log(Status.INFO, elementName + "Switch to iframe");

		} else {
			ObjectRepo.test.log(Status.PASS, elementName + "iframe is not present");
			ObjectRepo.test.log(Status.INFO, elementName + "Unable to switch to iframe");
		}
	}

	public void EventDropDownHandling(String eventtName, List<WebElement> element) throws InterruptedException {
		String eveName = getEventName(eventtName, element);
		if (eventtName.equalsIgnoreCase(eveName)) {
			ObjectRepo.test.log(Status.PASS, eventtName + "Event is present in the event list");
			ObjectRepo.test.log(Status.INFO, eventtName + "Event is present in the event list");
		} else {
			Assert.fail("Event not found");
			ObjectRepo.test.log(Status.PASS, eventtName + "Event is not present in the event list");
			ObjectRepo.test.log(Status.INFO, eventtName + "Event is not present in the event list");
		}

	}

	public String getEventName(String eventtName, List<WebElement> element) throws InterruptedException {
		String event = null;
		List<WebElement> events = element;
		// Iterating through the list selecting the desire option
		for (int j = 0; j < events.size(); j++) {

			if (events.get(j).getText().equals(eventtName)) {
				event = eventtName;
				events.get(j).click();
				break;
			}

		}
		return event;
	}
}
