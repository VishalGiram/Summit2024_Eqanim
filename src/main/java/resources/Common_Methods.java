package resources;


import static constants.ThreadConstants.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Common_Methods {

    private WebDriverWait wait;
	private By btnHome = By.xpath("//*[contains(text(),'Home')]");

	public Common_Methods() {
        PageFactory.initElements(driver.get(), this);
    }

	public boolean privateHomePageLoaded() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(btnHome));
		String str = driver.get().findElement(btnHome).getText();
		Assert.assertEquals(str, "Home");
		return true;
	}

	public boolean privateHomePageLoaded1() {
		wait.until(ExpectedConditions.invisibilityOfElementLocated(btnHome));
		String str = driver.get().findElement(btnHome).getText();
		Assert.assertEquals(str, "Home");
		return true;

	}
}
