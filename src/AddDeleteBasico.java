import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddDeleteBasico {

	@Test

	public void AddandDeletePlace() {
		String infoJson = "{" + "\"location\": {" + "\"lat\" : -38.383494," + "\"lng\" : 33.427362" + "},"
				+ "\"accuracy\":50," + "\"name\":\"Frontline house\"," + "\"phone_number\":\"(+91) 983 893 3937\","
				+ "\"address\" : \"29, side layout, cohen 09\"," + "\"types\": [\"shoe park\"],"
				+ "\"website\" : \"http://google.com/\"," + "\"language\" : \"French-IN\"" + "}";
		RestAssured.baseURI = "http://216.10.245.166";
		Response r = given().

				queryParam("key", "qaclick123").body(infoJson).when().post("/maps/api/place/add/json").then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).and().body("status", equalTo("OK"))
				.extract().response();

		String responseString = r.asString();
		System.out.println(responseString);
		JsonPath js = new JsonPath(responseString);
		// Pegar o place ID da resposta
		String placeId = js.get("place_id");
		System.out.println(placeId);

		// Pegar o place ID e inserir no Delete request
		given().queryParam("key", "qaclick123").body("{" + "\"place_id\" : \"" + placeId + "\"" + "}").when()
				.post("/maps/api/place/delete/json").then().assertThat().statusCode(200).and()
				.contentType(ContentType.JSON).and().body("status", equalTo("OK"));

	}
}
