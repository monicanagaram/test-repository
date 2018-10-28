package org.sonatype.examples.APITesting;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.JsonConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.path.json.config.JsonPathConfig;
import io.restassured.response.Response;
import java.math.BigDecimal;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
    	
    	
    	
    	//String url="https://reqres.in/api/users/23";
    	
    	//System.out.println(RestAssured.get(url).body());
    	
   /*  String page = "4";
      Response response = RestAssured.get("https://reqres.in/api/users?delay=3" + page);
      System.out.println(response.getStatusCode());
      AssertJUnit.assertEquals(200, response.getStatusCode());
      System.out.println(response.jsonPath().get("data[1].last_name"));
    //  AssertJUnit.assertEquals(response.jsonPath().get("page").toString()); */
    	
    	 // System.out.println( "Hello World!" );
    	
    	Response response = RestAssured.get("https://api.gemini.com/v1/pubticker/ethbtc");
    	System.out.println(response.getStatusCode());
    	AssertJUnit.assertEquals(200, response.getStatusCode());
    	System.out.println(response.getBody().asString());
    }
}