package com.gurantors.GuarantorsApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class AppTest 
{
	ReusableFunctions reusableFn = new ReusableFunctions();
	String BaseURL="https://research.theguarantors.com/qa-test/";

 @Test
 public void Post200TestWithWholeNumbers(){
	 Response response;
	 JSONObject requestJson = new JSONObject();  
	 requestJson.put("numbers", new JSONArray(new Object[] { 1,2,3} ));
	 System.out.println("Request Body is =>  " + requestJson.toString());
	 response=reusableFn.PostRequest(BaseURL, requestJson.toString());
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		Assert.assertEquals(response.getStatusCode(), 200,"Status code validation");
		JSONArray JSONResponseBody = new   JSONArray(response.body().asString());
		validateForAscendingOrder(JSONResponseBody);
		 
		}
 
 @Test
 public void Post200TestWithNegativeNumbers(){
	 Response response;
	 JSONObject requestJson = new JSONObject();  
	 requestJson.put("numbers", new JSONArray(new Object[] { -4,-6,-9} ));
	 System.out.println("Request Body is =>  " + requestJson.toString());
	 response=reusableFn.PostRequest(BaseURL, requestJson.toString());
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		Assert.assertEquals(response.getStatusCode(), 200,"Status code validation");
		JSONArray JSONResponseBody = new   JSONArray(response.body().asString());
		validateForAscendingOrder(JSONResponseBody);		 
		}
 
 @Test
 public void Post400Test(){
	 Response response;
	 JSONObject requestJson = new JSONObject();  
	 requestJson.put("numbers", new JSONArray(new Object[] { 1,2,'#'} ));
	 System.out.println("Request Body is =>  " + requestJson.toString());
	 response=reusableFn.PostRequest(BaseURL, requestJson.toString());
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		Assert.assertEquals(response.getStatusCode(), 400,"Status code validation");		 
		}
 
 
 public void validateForAscendingOrder(JSONArray array){
	 boolean flag=false;
	 for(int i=1; i<array.length(); i++){
		 if(((Integer)array.get(i-1)) > ((Integer) array.get(i))){				 
		    flag=false;
		    Assert.assertTrue(flag);
		 }
			flag=true;
	    }
        Assert.assertTrue(flag);
	 
 }
 
 /****
  * Found following bugs in the code
  * 
  * If the request contains 0 the numbers are not sorted (position of 0 is unchanged)
  * If request contains only one number then response has empty array instead of returning that number
  * If content-type is other than application/json it returns 500 response code
  * If numbers contains any characters response doesn't give 400 bad request which is expected
  * 
  */
 }
    

