package testgetsymbols.testgetsymbols;

import java.util.ArrayList;
import java.util.Collections;

import org.testng.AssertJUnit;

import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    
    	
    	Response response = RestAssured.get("https://api.gemini.com/v1/symbols");
    	System.out.println(response.getBody().asString());
    	System.out.println("------------------------");
    	
    	
    	//jsonArraystring to javaArraylist
    	 String jsonArrayString = response.getBody().asString();
    	 Gson googleJson = new Gson();
    	 ArrayList javaArrayListFromGSON = googleJson.fromJson(jsonArrayString, ArrayList.class);
    	 
    	 System.out.println(javaArrayListFromGSON);
    	 
    	 //object
    	 float []prices = new float[javaArrayListFromGSON.size()];
    	 for(int i=0;i<javaArrayListFromGSON.size();i++) {
    	  String api = "https://api.gemini.com/v1/auction/" + javaArrayListFromGSON.get(i);
    	  Response priceresponse = RestAssured.get(api);
    	  System.out.println(priceresponse.getBody().asString());
    	
    	  
    	  try {
    	    prices[i] = priceresponse.getBody().jsonPath().getFloat("last_auction_price");
    	  } catch(Exception e) {
    		  prices[i] = 0;
    	  }
    	 }

    	System.out.println(prices);
    	 float max = 0;
    	 for(int i=0;i<prices.length;i++) {
    			if(prices[i]>max) {
    			   max = prices[i];
    			}
    		}

    	System.out.println(max);
      
    	
    	
    		

    }
    
    
}
