import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(payload.CoursePrice());
		//Print No of courses returned by API
		int count = js.getInt("courses.size()");
System.out.println(count);
//Print Title of the first course
String courseName = js.get("courses[0].title");
System.out.println(courseName);
//Print Purchase Amount
int amount = js.getInt("courses[0].price");
System.out.println(amount);
//Print All course titles and their respective Prices
for(int i=0 ; i< count; i++)
{
	String courseName1 = js.getString("courses["+ i +"].title");
	int price1 = js.getInt("courses["+ i +"].price");
	System.out.println(courseName1 + "is priced at " + price1);
	}
//Print value of Copies when title is RPA
for(int i=0; i< count ; i++)
{
String title = js.getString("courses["+i+"].title")	;
if (title.equalsIgnoreCase("RPA"))
{
int copies = js.getInt("courses["+i+"].copies")	;
System.out.println(copies);
}
}
	}

}
