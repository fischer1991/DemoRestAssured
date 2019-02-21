import static io.restassured.RestAssured.given;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import arquivos.ReusableMethods;

public class xmlPost {
	
	Properties prop = new Properties();
	
	@BeforeTest
	public void getData() throws IOException {
		FileInputStream fis = new FileInputStream(
				"C:\\Users\\IT_Admin\\workspace\\DemoRestAssured\\src\\arquivos\\dados.properties");
		prop.load(fis);
	}

	@Test
	public void postData() throws IOException {
		String postdata = GenerateStringFromResource("C:\\Users\\IT_Admin\\Downloads\\RestAssured\\postdata.xml");
		RestAssured.baseURI = prop.getProperty("HOST");

		Response r = given().

				queryParam("key", prop.getProperty("KEY")).body(postdata).when().post("/maps/api/place/add/xml").then()
				.assertThat().statusCode(200).and().contentType(ContentType.XML).extract().response();
		
		XmlPath x = ReusableMethods.rawToXML(r);
		System.out.printf(x.get("response.place_id"));
		
	}

	public static String GenerateStringFromResource(String path) throws IOException {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}