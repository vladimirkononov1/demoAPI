import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class Basics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//validate if Add Place API is working as expected
		//Add place --> Update Place with New Address -> Get Place validate if New address is present in response

		
		//given - all input details
		//when - Submit the API - resource, HTTP method
		//then - validate the response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(Payload.addPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
//		JsonPath js = new JsonPath(response);
		JsonPath js = ReusableMethods.rawToJson(response);
		String placeId = js.getString("place_id");
		
		System.out.println("\n" +placeId + "\n");

		//Update Place
		String newAddress = "75 Summer Walk, Africa";
		
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n" + 
				"\"place_id\":\"" + placeId + "\",\r\n" + 
				"\"address\":\"" + newAddress + "\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}").when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place
		System.out.println("\n");
		
		String getPlaceResponse = given().log().all().queryParams("key",  "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200)
		.extract().response().asString();
	
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		
		String actualAddress = js1.getString("address");
		
		System.out.println("\n" + actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
	}

}
