import org.testng.Assert;
import org.testng.annotations.Test;

import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidationTestNG {

	@Test
	public void sumOfCourses()
	{
		JsonPath js = new JsonPath(payload.CoursePrice());
		int totalPrice = js.getInt("dashboard.purchaseAmount");
		int count = js.getInt("courses.size()");
		int sum = 0;
		for(int i = 0; i<count; i++ )
		{
			int price = js.getInt("courses["+i+"].price");
			
			int copies = js.getInt("courses["+i+"].copies");
			sum = sum + price*copies;
		}
		System.out.println("summed price " + sum);
		Assert.assertEquals(sum, totalPrice);
		
	}
	
}
