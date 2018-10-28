package testproject.test;

import org.testng.AssertJUnit;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class App 
{
    public static void main( String[] args )
    {
    	
    	Response response = RestAssured.get("https://api.gemini.com/v1/pubticker/btcusd");
    	System.out.println(response.getStatusCode());
    	AssertJUnit.assertEquals(200, response.getStatusCode());
    	System.out.println(response.getBody().asString());
    
      
    }
}
