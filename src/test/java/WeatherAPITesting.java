import com.jayway.jsonpath.JsonPath;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Created by kohlih on 10-11-2017.
 */
public class WeatherAPITesting {

	@Test
	public void TestCase1() {
		Response response = given().param("query", "chennai").when()
				.get("http://api.weatherstack.com/current?access_key=4bb95b1299b55098b766112e7c1dff07").then()
				.statusCode(200).extract().response();

		System.out.println(response.getStatusCode());
		System.out.println(response != null ? "Suceess" : "failure");

		int expectedValue = 200;
		int actualValue = response.getStatusCode();
		
		Headers  headers= response.getHeaders();
		
		List<Header>   headerList = headers.asList();
		System.out.println(headerList.size());
		
		System.out.println(headers.getValue("Content-Type"));
		
		
		System.out.println();

		// int totalItems = JsonPath.read(response.asString(),"$.totalItems");

		Assert.assertEquals(expectedValue, actualValue);

	}

	@Test
	public void testTimeZone() {

		String[] cities = { "Chennai", "Kolkata", "Mumbai", "Delhi", "Trichy", "Madurai", "Cheyyar"};

		for (String city : cities) {

			Response response = given().param("query", city).when()
					.get("http://api.weatherstack.com/current?access_key=4bb95b1299b55098b766112e7c1dff07").then()
					.statusCode(200).extract().response();
			
			System.out.println(response.asString());

			System.out.println("Running Testcase2 :" + response.getStatusCode());

			String timezone = "Asia/Kolkata";
			String actulatimezone = JsonPath.read(response.asString(), "$.location.timezone_id");
			System.out.println(actulatimezone);

			Assert.assertEquals(timezone, actulatimezone);
		}

	}

}
