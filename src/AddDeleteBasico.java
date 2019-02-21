import arquivos.resources;
import arquivos.payLoad;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class AddDeleteBasico {

	Properties prop = new Properties();

	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\IT_Admin\\workspace\\DemoRestAssured\\src\\arquivos\\dados.properties");
		prop.load(fis);
	}

	@Test
	public void AddandDeletePlace() {

		// Pegar a resposta
		RestAssured.baseURI = prop.getProperty("HOST");
		Response r = given().

				queryParam("key", prop.getProperty("KEY")).body(payLoad.dadosJson()).when().post(resources.adicionarJson()).then()
				.assertThat().statusCode(200).and().contentType(ContentType.JSON).and().body("status", equalTo("OK"))
				.extract().response();

		String responseString = r.asString();
		System.out.println(responseString);
		JsonPath js = new JsonPath(responseString);
		// Pegar o place ID da resposta
		String placeId = js.get("place_id");
		System.out.println(placeId);

		// Pegar o place ID e inserir no Delete request
		given().queryParam("key", prop.getProperty("KEY")).body("{" + "\"place_id\" : \"" + placeId + "\"" + "}").when()
				.post(resources.deletarJson()).then().assertThat().statusCode(200).and().contentType(ContentType.JSON)
				.and().body("status", equalTo("OK"));

	}
}
