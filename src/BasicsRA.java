import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.ReusableMethods;
import files.payload;

public class BasicsRA {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//Validate if Add Place API is working as expected
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(payload.AddPlace()).when().post("maps/api/place/add/json").then().assertThat()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		System.out.println(response);
		JsonPath js = ReusableMethods.rawToJson(response);
		String placeID = js.getString("place_id");
		System.out.println(placeID);
//Scenario to automate - Add place -> update place with new address -> Get place to validate if new address is present in response.
	//update place
		String newaddress = "Summer walk, Africa";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.UpdatePlace(placeID, newaddress)).when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
	
		//getPlaceAPI
		String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID).
		when().get("maps/api/place/get/json").
		then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js1 = ReusableMethods.rawToJson(getPlaceResponse);
		
		String actualAddress = js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newaddress);
	}

}
