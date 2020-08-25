package demo;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.Courses;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		String [] webAutoTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		/*
		 * Blocked by Google
		 * 
		 * 
		 * System.setProperty("webdriver.chrome.driver",
		 * "C:\\Users\\vladi\\workspace\\chromedriver.exe"); WebDriver driver = new
		 * ChromeDriver(); driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php"
		 * );
		 * 
		 * driver.findElement(By.cssSelector("input[type='email']")).sendKeys(
		 * "vladimirkon@gmail.com");
		 * driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER
		 * ); Thread.sleep(3000);
		 * driver.findElement(By.cssSelector("input[type='password']")).sendKeys(
		 * "123456");
		 * driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.
		 * ENTER); Thread.sleep(4000); String url = driver.getCurrentUrl();
		 */
		String codeS = "4%2F3QHfRKKQIO9lXXs9bu4P8j9hDn9CmUHjyv8tMvqlLFJgSYXBYDZLsTv-jAxbJQvBV5aqi0isKgfBZRAAsRDT6gk";
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code="+ codeS + "&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none#";
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];
		System.out.println("\nPrinting filtered CODE: " + code + "\n");

		String accessTokenResponse = given().urlEncodingEnabled(false).queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();

		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");

		GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
				.when()
				.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		System.out.println(gc.getLinkedIn() + "\n" + gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<Api> apiCourses = gc.getCourses().getApi();
		
		String soapUIprice = "";
		for(int i=0; i<apiCourses.size(); i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) 
			{
				soapUIprice = apiCourses.get(i).getPrice();
				break;
			}
		}
		
		//Get course names of WebAutomation

		List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();
		List<String> titleNames = new ArrayList<String>();

		for(int i=0; i<webAutomationCourses.size(); i++)
		{
			titleNames.add(webAutomationCourses.get(i).getCourseTitle());
			
		}
		System.out.println("SoapUI Webservices testing price is " + soapUIprice + "\n");
		
		List<String> expectedTitles = Arrays.asList(webAutoTitles);
		
		Assert.assertTrue(titleNames.equals(expectedTitles));

		System.out.println(titleNames + "\n" + expectedTitles);

		
	}

}
