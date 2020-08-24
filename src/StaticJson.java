import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJson {

	@Test
	public void addBook() throws IOException
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response = given()
				.log().all().header("Content-Type", "application/json")
		.body(generateStringFromResource("C:\\Users\\vladi\\OneDrive\\Documents\\Udemy\\SeleniumWebDriver\\documentsToRead\\APIs\\AddBookDetails.json"))
		.when()
		.post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	public static String generateStringFromResource(String path) throws IOException
	{
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
