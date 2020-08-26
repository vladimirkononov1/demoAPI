package demo;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.AddPlace;
import pojo.Location;
import scala.Array;

public class SerializeTest {

	public static void main(String[] args) {

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddPlace p = new AddPlace();
		p.setAccuracy(50);
		p.setAddress("29, side layout, cohen 09");
		p.setLanguage("French-IN");
		p.setName("Frontline house");
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");

		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);

		p.setTypes(myList);
		p.setLocation(l);

		Response res = given().log().all()
				.queryParam("key", "qaclick123")
				.body(p)
				.when()
				.post("/maps/api/place/add/json")
				.then()
				.assertThat().statusCode(200)
				.extract().response();

		String responseString = res.asString();
		System.out.println(responseString);
	}
}
