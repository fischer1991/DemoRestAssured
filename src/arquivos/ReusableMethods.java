package arquivos;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;

public class ReusableMethods {
	
	public static XmlPath rawToXML(Response r)
	{
		String resp = r.asString();
		XmlPath x = new XmlPath(resp);
		return x;
	}
	
	public static JsonPath rawToJson(Response r)
	{
		String resp = r.asString();
		JsonPath x = new JsonPath(resp);
		return x;
	}

//	public static XmlPath rawToXML(Response r, String s) 
//	{
//		if (s=="xml")
//		{
//		String respon = r.asString();
//		XmlPath x = new XmlPath(respon);
//		return x;
//		}
//		else if (s=="json")
//		{
//			String respon = r.asString();
//			JsonPath x = new JsonPath(respon);
//			return x;
//		}
//	}
}	

	