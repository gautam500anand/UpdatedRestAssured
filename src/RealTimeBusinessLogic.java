import files.payload;
import io.restassured.path.json.JsonPath;

public class RealTimeBusinessLogic {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(payload.CoursePrice());
		int totalPrice = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalPrice);
		int count = js.getInt("courses.size()");
		int sum = 0;
		for(int i = 0; i<count; i++ )
		{
			int price = js.getInt("courses["+i+"].price");
			
			int copies = js.getInt("courses["+i+"].copies");
			sum = sum + price*copies;
		}
		System.out.println("summed price " + sum);
	}

}
