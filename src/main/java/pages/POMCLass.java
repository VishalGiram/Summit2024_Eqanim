package pages;

import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import io.restassured.response.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;

import static constants.ThreadConstants.driver;
import static resources.listeners.wait;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class POMCLass {

	public static String headline1;
	public static String hrefvalue;
	public static String date;
	public static String formattedDate;
	public static String id;
	public static String jsonString1;
	public static String jsonString2;
	

	@FindBy(xpath = "//*[@data-ie-event-label='Politics']")
	private WebElement politicsElement;

	@FindBy(xpath = "//*[@aria-label=\"1 of 5\"]")
	private WebElement slickSlideControl10;

	@FindBy(xpath = "//*[@aria-label=\"2 of 5\"]")
	private WebElement slickSlideControl11;

	@FindBy(xpath = "//*[@aria-label='3 of 5']")
	private WebElement slickSlideControl12;

	@FindBy(xpath = "//li[@id='slick-slide10']")
	private WebElement slickSlide10;

	@FindBy(xpath = "//li[@id='slick-slide10']//span")
	private WebElement headlineElement;

	@FindBy(xpath = "//li[@id='slick-slide11']//span")
	private WebElement headlineElement2;

	@FindBy(xpath = "//li[@id='slick-slide12']//span")
	private WebElement headlineElement3;

	@FindBy(xpath = "//li[@id='slick-slide10']//a")
	private WebElement linkElement;

	@FindBy(xpath = "//li[@id='slick-slide11']//a")
	private WebElement linkElement2;

	@FindBy(xpath = "//li[@id='slick-slide12']//a")
	private WebElement linkElement3;

	@FindBy(xpath = "//div[contains(text(),'First uploaded on:')]")
	private WebElement linkDate;

	public POMCLass() {
		PageFactory.initElements(driver.get(), this);
	}

	// Add methods to interact with these elements, for example:
	public void clickOnPoliticsElement() {
		String mainWindowHandle = driver.get().getWindowHandle();
		wait.until(ExpectedConditions.elementToBeClickable(politicsElement));
		politicsElement.click();
		Set<String> allWindowHandles = driver.get().getWindowHandles();
		// Loop through all window handles
		for (String windowHandle : allWindowHandles) {
			if (!windowHandle.equals(mainWindowHandle)) {
				// Switch to the newly opened window
				driver.get().switchTo().window(windowHandle);
			}
		}
	}

	public void clickOnSlickSlideControl10() {
		wait.until(ExpectedConditions.elementToBeClickable(slickSlideControl10));
		slickSlideControl10.click();
	}

	public void clickOnSlickSlideControl11() {
		wait.until(ExpectedConditions.elementToBeClickable(slickSlideControl11));
		slickSlideControl11.click();
	}

	public void clickOnSlickSlideControl12() {
		wait.until(ExpectedConditions.elementToBeClickable(slickSlideControl12));
		slickSlideControl12.click();
	}

	public void clickOnSlickSlide10() {
		wait.until(ExpectedConditions.elementToBeClickable(slickSlide10));
		slickSlide10.click();
	}

	public String getHeadline() {
		System.out.println(headlineElement.getText());
		headline1 = headlineElement.getText();
		return headlineElement.getText();
	}

	public String getHeadline2() {
		wait.until(ExpectedConditions.visibilityOf(headlineElement2));
		System.out.println(headlineElement2.getText());
		return headlineElement2.getText();
	}

	public String getHeadline3() {
		wait.until(ExpectedConditions.visibilityOf(headlineElement3));
		System.out.println(headlineElement3.getText());
		return headlineElement3.getText();
	}

	// Method to get the href value
	public String getHref() {
		System.out.println("LInk :" + linkElement.getAttribute("href"));
		hrefvalue = linkElement.getAttribute("href");
		return linkElement.getAttribute("href");
	}

	public String getHref2() {
		System.out.println("LInk :" + linkElement2.getAttribute("href"));
		return linkElement2.getAttribute("href");
	}

	public String getHref3() {
		System.out.println("LInk :" + linkElement3.getAttribute("href"));
		return linkElement3.getAttribute("href");
	}

	public void getHeadlineelementclick() {
		wait.until(ExpectedConditions.elementToBeClickable(headlineElement));
		headlineElement.click();
	}

	public void getHeadlineelementclick2() {
		wait.until(ExpectedConditions.elementToBeClickable(headlineElement2));
		headlineElement2.click();
	}

	public void getHeadlineelementclick3() {
		wait.until(ExpectedConditions.elementToBeClickable(headlineElement3));
		headlineElement3.click();
	}

	public String getDate() {
		wait.until(ExpectedConditions.elementToBeClickable(linkDate));
		System.out.println(linkDate.getText());
		date = linkDate.getText();
		formattedDate = convertDate(date);
		System.out.println(formattedDate); // Output: 270824
		return linkDate.getText();
	}

	public String getDate2() {
		wait.until(ExpectedConditions.elementToBeClickable(linkDate));
		System.out.println(linkDate.getText());
		return linkDate.getText();
	}

	public String getDate3() {
		wait.until(ExpectedConditions.elementToBeClickable(linkDate));
		System.out.println(linkDate.getText());
		return linkDate.getText();
	}

	public static String convertDate(String date) {
		// Extract the date part using regex
		String regex = "\\d{2}-\\d{2}-\\d{4}";
		String extractedDate = date.replaceAll(".*?(\\d{2}-\\d{2}-\\d{4}).*", "$1");

		// Convert the extracted date to the required format
		String[] dateParts = extractedDate.split("-");
		String day = dateParts[0];
		String month = dateParts[1];
		String year = dateParts[2].substring(2); // Get last two digits of the year

		// Return the formatted date
		return day + month + year;
	}

	public void createDataJson() {
		// Creating a Map to store the JSON data
		Map<String, Object> jsonData = new HashMap<>();
		jsonData.put("name", headline1);
		jsonData.put("description", hrefvalue);
		jsonData.put("price", formattedDate);
		jsonData.put("item_type", "Eqanim Tech Pvt Ltd");

		try {
			// Converting the Map to JSON
			ObjectMapper objectMapper = new ObjectMapper();
			String jsonString = objectMapper.writeValueAsString(jsonData);
			System.out.println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getIDusingAPI() throws JsonProcessingException {

		Map<String, Object> jsonData = new HashMap<>();
		jsonData.put("name", headline1);
		jsonData.put("description", hrefvalue);
		jsonData.put("price", formattedDate);
		jsonData.put("item_type", "Eqanim Tech Pvt Ltd");

		ObjectMapper objectMapper = new ObjectMapper();
		jsonString1 = objectMapper.writeValueAsString(jsonData);
		System.out.println(jsonString1);

//		String jsonString = "{\n"
//				+ "  \"name\": \"Modi discusses Kyiv visit with Putin, calls for early resolution of conflict\",\n"
//				+ "  \"description\": \"https://indianexpress.com/article/india/pm-modi-russia-president-putin-ukraine-9535608/\",\n"
//				+ "  \"price\": 28082024,\n" + "  \"item_type\": \"Eqanim Tech Pvt Ltd\"\n" + "}";

		// Set the base URI
		RestAssured.baseURI = "http://ec2-54-254-162-245.ap-southeast-1.compute.amazonaws.com:9000";

		// Send POST request
		Response response = given().header("Content-Type", "application/json").body(jsonString1).when().post("/items/")
				.then().assertThat().statusCode(200) // assuming 201 Created is the expected status code
				.extract().response();

		// Fetch the ID from the response
		id = response.jsonPath().getString("id");
		System.out.println("ID from response: " + id);
	}

	public void getdataUsingID() {
		RestAssured.baseURI = "http://ec2-54-254-162-245.ap-southeast-1.compute.amazonaws.com:9000";

		// Perform a GET request
		Response response = given().when().get("/items/" + id).then().statusCode(200) // Validate the response status
																						// code is 200
				.extract().response(); // Extract the response

		// Print the entire response body
		jsonString2 = response.getBody().asString();
		System.out.println("Response Body: " + jsonString2);

		// Fetch specific data from the response
		// Assuming the response is in JSON format, and you want to fetch a specific
		// field
		String itemName = response.jsonPath().getString("itemName");
		System.out.println("Item Name: " + itemName);

		// Example of fetching another field
		int itemId = response.jsonPath().getInt("id");
		System.out.println("Item ID: " + itemId);
	}
	
	public void compareJson() {
        // Convert the strings to JSONObjects
        JSONObject jsonObject1 = new JSONObject(jsonString1);
        JSONObject jsonObject2 = new JSONObject(jsonString2);

        // Compare the "name" field
        if (jsonObject1.getString("name").equals(jsonObject2.getString("name"))) {
            System.out.println("Name is verified");
        } else {
            System.out.println("Names are different");
        }

        // Compare the "description" field
        if (jsonObject1.getString("description").equals(jsonObject2.getString("description"))) {
            System.out.println("Description is verified");
        } else {
            System.out.println("Description are different");
        }

        // Compare the "price" field after converting to a common data type
        String price1 = jsonObject1.getString("price");
        double price2 = jsonObject2.getDouble("price");

        if (Double.parseDouble(price1) == price2) {
            System.out.println("Price is verified");
        } else {
            System.out.println("Price are different");
        }

        // Compare the "item_type" field
        if (jsonObject1.getString("item_type").equals(jsonObject2.getString("item_type"))) {
            System.out.println("Item type is verified");
        } else {
            System.out.println("Item types are different");
        }
    }
	}

