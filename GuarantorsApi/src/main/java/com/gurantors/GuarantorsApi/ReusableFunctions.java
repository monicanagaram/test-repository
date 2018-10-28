package com.gurantors.GuarantorsApi;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class ReusableFunctions 
{
	public Response PostRequest(String url, String payload) {
		RestAssured.baseURI = url;
		Response response = RestAssured.given().
				header("Accept","application/json").
				header("Content-Type", "application/json").relaxedHTTPSValidation().body(payload).when().post(url);

		return response;
	}

}
