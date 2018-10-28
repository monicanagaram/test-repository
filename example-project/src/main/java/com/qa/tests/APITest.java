/**
/**
 * @author Monica Nagaram
 * Possible Test Cases for New Order API
 */
package com.qa.tests;

/**
 * Imports 
 */

import java.util.Base64;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;

public class APITest {

	/**
	 * Setup Parameters
	 */

	/**
	 * Test Case 1:	
	 * 		
	 */
	@Test
	public void TestNewOrder() {
		String url = "http://reqres.in/api/users?page=2";
		System.out.println(RestAssured.get(url).body());
	}

} 
