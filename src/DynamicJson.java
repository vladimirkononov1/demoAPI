

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class DynamicJson {

	@Test(dataProvider = "BookData", enabled=true)
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		String response = given()
				.log().all().header("Content-Type", "application/json")
		.body(Payload.addBook(isbn, aisle))
		.when()
		.post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	@Test(dataProvider = "BookData")
	public void deleteBook(String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		Response res = given().log().all().body(Payload.deleteBook(aisle, isbn))
		.when().delete("/Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response();
		
		System.out.println("Response starts\n" + res.asString() + "\nends");
	}
	
	@DataProvider(name = "BookData")
	public Object[][] getData()
	{
		return new Object[][] {{"bsced", "4561"}, {"scds", "94942"}, {"lskdf", "993p8"}};
	}
}
