import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraTests {

	SessionFilter session = new SessionFilter();

	@Test
	public void logInToJira() {
		RestAssured.baseURI = "http://localhost:8080";

		String response = given().header("Content-Type", "application/json")
				.body("{ \"username\": \"gautam-anand\", \"password\": \"---------\" }").filter(session).when().log()
				.all().post("rest/auth/1/session").then().log().all().extract().response().asString();
		System.out.println(response);

	}

	@Test(dependsOnMethods = { "logInToJira" })
	public void addComment() {
		RestAssured.baseURI = "http://localhost:8080";
		given().pathParam("key", "10006").log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"body\": \"I am adding the comment 3\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(session).when().post("/rest/api/2/issue/{key}/comment").then().log().all().assertThat()
				.statusCode(201);
	}

	@Test(dependsOnMethods = { "logInToJira" })
	public void addAttachment() {
		RestAssured.baseURI = "http://localhost:8080";
		String locationOfFile = System.getProperty("user.dir") + "\\Resources\\jira.txt";
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("key", "10006")
				.header("Content-Type", "multipart/form-data").multiPart("file", new File(locationOfFile)).log().all()
				.when().post("/rest/api/2/issue/{key}/attachments").then().log().all().assertThat().statusCode(200);
	}
}
