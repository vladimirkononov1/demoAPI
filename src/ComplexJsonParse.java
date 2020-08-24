

import java.util.List;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(Payload.coursePrice());
		
		System.out.println("Print No of courses returned by API");
		
		int count = js.getInt("courses.size()");
		System.out.println(count);
		
		System.out.println("\nPrint Purchase Amount");
		
		int totalAmmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmmount);
		
		System.out.println("\nPrint Title of the first course");
		
		String titleFirstCourse = js.getString("courses[0].title");
		System.out.println(titleFirstCourse);
		
		System.out.println("\nPrint All course titles and their respective Prices");
		
		for(int i=0; i<count; i++) {
			String courseTitle = js.get("courses["+ i +"].title");
			int coursePrice = js.getInt("courses["+ i +"].price");
			System.out.println(courseTitle + " " + coursePrice);
		}
		
		System.out.println("\nPrint no of copies sold by RPA Course");
		
		for(int i=0;i<count;i++) {
			String courseTitles = js.get("courses["+ i +"].title");
			if(courseTitles.equals("RPA")) {
				int copies = js.getInt("courses["+i+"].copies");
				System.out.println(copies);
				break;
			}
		}

	}

}
