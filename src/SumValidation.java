import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	JsonPath js = new JsonPath(Payload.coursePrice());

	@Test
	public void sumOfCourses()
	{
		//Verify if Sum of all Course prices matches with Purchase Amount
		
		int count = js.getInt("courses.size()");
		
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		int sum = 0;
		for(int i=0;i<count;i++)
		{
			int copies = js.getInt("courses["+ i +"].copies");
			int price = js.getInt("courses["+ i +"].price");
			sum = sum + price * copies;
		}
		System.out.println(sum);
		Assert.assertEquals(sum, purchaseAmount);
	}
}
