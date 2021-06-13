import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import files.ReusableMethods;
import files.payload;

public class DynamicJSON {
	
@Test(dataProvider = "BooksData") @Ignore
public void addBook(String isbn, String aisle)
{
	RestAssured.baseURI = "http://216.10.245.166";
	String addBookRes = given().log().all().header("Content-Type", "application/json").
	body(payload.AddBook(isbn, aisle)).when().post("Library/Addbook.php").
	then().log().all().assertThat().statusCode(200).extract().response().asString();
	System.out.println("Gautam" +addBookRes);
	JsonPath js = ReusableMethods.rawToJson(addBookRes);
	String ID = js.get("ID");
	System.out.println(ID);
	}
@DataProvider(name = "BooksData")
public Object [][] getData()
{
	return new Object [][] {{"dsewew","9363"},{"ewrer","3243"},{"gfgfd","3455"}};
	
	}

//Content of file to String--->Content of file can convert into Byte--->Byte data to String
@Test
public void payLoadFromFile() throws IOException {
	String path = System.getProperty("user.dir")+"\\Resources\\AddPlace.json";
	RestAssured.baseURI= "https://rahulshettyacademy.com";
	String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").
	body(new String (Files.readAllBytes(Paths.get(path)))).when().post("/maps/api/place/add/json").
	then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server" , "Apache/2.4.18 (Ubuntu)" ).extract().response().asString();
System.out.println(response);
JsonPath js = new JsonPath(response);
String placeID = js.getString("place_id");
System.out.println(placeID);
}
}
