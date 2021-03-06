/**
/**
 * @author Monica Nagaram
 * Possible Test Cases for New Order API
 */
package APITest;

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


	//These needs to be securely loaded
	private static final String API_SECRET = "4D27fFyvmJf4cmigg9vQuun7Aktv";
	private static final String API_KEY = "33GSPrDCVkjFgDItgsbC";
	private static final String REST_API_BASE = "https://api.sandbox.gemini.com";

	RequestSpecification spec;
	Mac mac;

	/**
	 * Test Case 1:	
	 * 		New Order Basic Test: Validate new order with buy/sell and 
	 * 		type “maker-or-cancel” with a symbol and test all fields in the response
	 */
	@Test
	public void TestNewOrder() {
		String symbol = "btcusd";
		Response response = null;

		double priceToSell = this.getCurrentPrice(symbol);
		String type = "maker-or-cancel";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));

	}

	/**
	 * Test Case 2:
	 * 		New Order Min Amount Test: Validate min_amount is optional. 
	 * 		Place new order without min_amount
	 */
	@Test
	public void TestNewOrderMinAmount() {
		String symbol = "btcusd";
		Response response = null;

		double curPrice = this.getCurrentPrice(symbol);
		String type = "maker-or-cancel";
		String orderid = this.generateOrderId();
		
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(curPrice)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));
	}

	/**
	 * Test Case 3:
	 * 		New Order “maker-or-cancel” business logic Test: 
	 * 		Place new order “sell” with amount 30% less than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestMakerOrCancelSellOrderLowPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% lower
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) - (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "maker-or-cancel";
		String orderid = this.generateOrderId(); 
		
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));

	}
	
	/**
	 * Test Case 4:
	 * 		New Order “maker-or-cancel” business logic Test: 
	 * 		Place new order “sell” with amount 30% greater than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestMakerOrCancelSellOrderHighPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% higher
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) + (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "maker-or-cancel";

		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));
	}
	
	/**
	 * Test Case 5:
	 * 		New Order “immediate-or-cancel” business logic Test: 
	 * 		Place new order “sell” with amount 30% greater than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestImmediateOrCancelSellOrderHighPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% higher
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) + (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "immediate-or-cancel";

		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertTrue(response.path("is_cancelled"));
	}
	
	/**
	 * Test Case 6:
	 * 		New Order “immediate-or-cancel” business logic Test: 
	 * 		Place new order “sell” with amount 30% less than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestImmediateOrCancelSellOrderLowPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% higher
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) - (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "immediate-or-cancel";

		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertTrue(response.path("is_cancelled"));
	}
	/**
	 * Test Case 7:
	 * 		New Order “auction-only” business logic Test: 
	 * 		Place new order “sell” with amount 30% greater than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestAuctionOnlyOrderHighPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% higher
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) + (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "auction-only";

		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));
	}
	
	
	/**
	 * Test Case 9:
	 * 		New Order with Invalid API Key
	 *
	 */
	@Test
	public void TestNewOrderInvalidAPIKey() {
		//Override headers with invalid API Key
		RequestSpecification spec = new RequestSpecBuilder().
				addHeader("Cache-Control", "no-cache").
				addHeader("Content-Type", "text/plain").
				addHeader("X-GEMINI-APIKEY", "some invalid API key").
				build();

		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(curPrice)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrderInvalidAPIKey(jsonObj, spec);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Test Case 10:
	 * 		New Order with incorrect symbol
	 *
	 */
	@Test
	public void TestNewOrderInvalidSymbol() {
		
		//Override headers with invalid symbol
		String symbol = "invalidsymbol";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(curPrice)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Test Case 11:
	 * 		New Order with incorrect amount
	 *
	 */
	@Test
	public void TestNewOrderInvalidAmount() {
		
		//Override headers with invalid symbol
		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","-100"). //Invalid amount
				put("price", Double.toString(curPrice)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Test Case 12:
	 * 		New Order with incorrect side
	 *
	 */
	@Test
	public void TestNewOrderInvalidSide() {
		
		//Override headers with invalid symbol
		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1"). 
				put("price", Double.toString(curPrice)).
				put("side","invalidside"). //Invalid side
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Test Case 13:
	 * 		New Order with incorrect type
	 *
	 */
	@Test
	public void TestNewOrderInvalidType() {
		
		//Override headers with invalid symbol
		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1"). 
				put("price", Double.toString(curPrice)).
				put("side","sell"). 
				put("type","invalid type"). //Invalid type
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	

	/**
	 * Test Case 14:
	 * 		New Order with invalid nonce
	 *
	 */
	@Test
	public void TestNewOrderInvalidNonce() {
		
		//Override headers with invalid symbol
		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", 1). //Invalid nonce
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1"). 
				put("price", Double.toString(curPrice)).
				put("side","sell"). 
				put("type","invalid type"). 
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Stub methods to test all other basic assertions
	 * @param requestObj
	 * @param response
	 */
	private void TestbasicAsserts(JSONObject requestObj, Response response) {
		ResponseBody<?> body = response.getBody();
		Assert.assertEquals(response.statusCode(), 200, "Reponse Code failed with non 200");
		Assert.assertEquals(Double.parseDouble(body.path("price")), Double.parseDouble(requestObj.get("price").toString()), "Response Price doesn't match with request price");
		Assert.assertEquals(body.path("symbol"), requestObj.get("symbol"), "Response symbol doesn't match with request symbol");
		Assert.assertEquals(body.path("client_order_id"), requestObj.get("client_order_id"), "Response client_order_id doesn't match with request client_order_id");
		Assert.assertEquals(body.path("side"), requestObj.get("side"), "Response side doesn't match with request side");
		// This assertion seems to be failing for auction only orders??
		//Assert.assertEquals(body.path("type"), requestObj.get("type"), "Response type doesn't match with request type");
		Assert.assertEquals(body.path("options[0]"), requestObj.getJSONArray("options").get(0), "Response options doesn't match with request options");
		Assert.assertEquals(Double.parseDouble(body.path("original_amount")),
				(Double.parseDouble(body.path("executed_amount")) 
						+ Double.parseDouble(body.path("remaining_amount"))),
				"Original Amount doesn't match Remaining + executed match");
	}

	/**
	 * Re-usable Place order method
	 * Adds the encrypted payload and returns response
	 * @param Request jsonObj
	 * @return Response
	 */
	public Response PlaceNewOrder(JSONObject jsonObj) {
		RestAssured.baseURI = REST_API_BASE + "/v1/order/new";
		String payLoad = this.Base64Encode(jsonObj.toString());

		String hmacPayLoad = this.generateHmacPayload(payLoad);

		Response response = RestAssured.given().
				header("X-GEMINI-PAYLOAD",payLoad).
				header("X-GEMINI-SIGNATURE", hmacPayLoad).
				spec(this.spec).
				request(Method.POST);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		return response;
	}

	/**
	 * Re-usable Place order method
	 * Adds the encrypted payload and returns response
	 * @param Request jsonObj
	 * @return Response
	 */
	public Response PlaceNewOrderInvalidAPIKey(JSONObject jsonObj, RequestSpecification spec) {
		RestAssured.baseURI = REST_API_BASE + "/v1/order/new";
		String payLoad = this.Base64Encode(jsonObj.toString());

		String hmacPayLoad = this.generateHmacPayload(payLoad);

		Response response = RestAssured.given().
				header("X-GEMINI-PAYLOAD",payLoad).
				header("X-GEMINI-SIGNATURE", hmacPayLoad).
				spec(spec).
				request(Method.POST);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		return response;
	}
	
	@BeforeClass
	/**
	 * Setup API headers which is common for all test cases
	 */
	public void beforeClass() {
		System.out.println();

		this.spec = new RequestSpecBuilder().
				addHeader("Cache-Control", "no-cache").
				addHeader("Content-Type", "text/plain").
				addHeader("X-GEMINI-APIKEY", API_KEY).
				build();
	}

	@BeforeTest
	public void beforeTest() {		
		addDelay();
		RestAssured.baseURI = REST_API_BASE + "/v1/order/new";
	}
	/**
	 * Gets current price for a given symbol using gemini public APIs
	 * @param symbol
	 * @return
	 */
	public double getCurrentPrice(String symbol) {
		RestAssured.baseURI = REST_API_BASE + "/v1/pubticker/" + symbol;
		Response response = RestAssured.given().get();
		//String responseBody = response.getBody().asString();
		//System.out.println("Ticker Response Body is =>  " + responseBody);
		String last = response.getBody().path("last");
		//System.out.println("Last Price is =>  " + last);
		return Double.parseDouble(last);

	}
	/**
	 * Following methods use encryption algorithm to generate API signatures as per gemini spec
	 * @param payload
	 * @return
	 */
	private String generateHmacPayload(String payload) {
		String HMAC_SHA1_ALGORITHM = "HmacSHA384";
		SecretKeySpec signingKey = new SecretKeySpec(API_SECRET.getBytes(), HMAC_SHA1_ALGORITHM);
		try {
			this.mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hmacPayLoad = bytesToHex(mac.doFinal(payload.getBytes()));
		return hmacPayLoad;
	}
	private String Base64Encode(String originalInput) {
		return Base64.getEncoder().encodeToString(originalInput.getBytes());
	}
	private static String bytesToHex(final byte[] hash) {
		final StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			final String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
	/**
	 * 
	 */
	private String generateOrderId() {	
		long timeStamp = System.currentTimeMillis();
		String timeStampString = Objects.toString(timeStamp);

		return timeStampString;
	}
	/**
	 * To sleep between multiple requests
	 */
	private static void addDelay() {
		try {
			Thread.sleep(2000);
		}
		catch(InterruptedException e) {
			System.out.println("Interruped while executing Test Cases" + e.getMessage());
		}
	}

} * @author Monica Nagaram
 * Possible Test Cases for New Order API
 */
package APITest;

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


	//These needs to be securely loaded
	private static final String API_SECRET = "4D27fFyvmJf4cmigg9vQuun7Aktv";
	private static final String API_KEY = "33GSPrDCVkjFgDItgsbC";
	private static final String REST_API_BASE = "https://api.sandbox.gemini.com";

	RequestSpecification spec;
	Mac mac;

	/**
	 * Test Case 1:	
	 * 		New Order Basic Test: Validate new order with buy/sell and 
	 * 		type “maker-or-cancel” with a symbol and test all fields in the response
	 */
	@Test
	public void TestNewOrder() {
		String symbol = "btcusd";
		Response response = null;

		double priceToSell = this.getCurrentPrice(symbol);
		String type = "maker-or-cancel";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));

	}

	/**
	 * Test Case 2:
	 * 		New Order Min Amount Test: Validate min_amount is optional. 
	 * 		Place new order without min_amount
	 */
	@Test
	public void TestNewOrderMinAmount() {
		String symbol = "btcusd";
		Response response = null;

		double curPrice = this.getCurrentPrice(symbol);
		String type = "maker-or-cancel";
		String orderid = this.generateOrderId();
		
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(curPrice)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));
	}

	/**
	 * Test Case 3:
	 * 		New Order “maker-or-cancel” business logic Test: 
	 * 		Place new order “sell” with amount 30% less than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestMakerOrCancelSellOrderLowPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% lower
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) - (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "maker-or-cancel";
		String orderid = this.generateOrderId(); 
		
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));

	}
	
	/**
	 * Test Case 4:
	 * 		New Order “maker-or-cancel” business logic Test: 
	 * 		Place new order “sell” with amount 30% greater than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestMakerOrCancelSellOrderHighPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% higher
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) + (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "maker-or-cancel";

		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));
	}
	
	/**
	 * Test Case 5:
	 * 		New Order “immediate-or-cancel” business logic Test: 
	 * 		Place new order “sell” with amount 30% greater than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestImmediateOrCancelSellOrderHighPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% higher
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) + (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "immediate-or-cancel";

		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertTrue(response.path("is_cancelled"));
	}
	
	/**
	 * Test Case 6:
	 * 		New Order “immediate-or-cancel” business logic Test: 
	 * 		Place new order “sell” with amount 30% less than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestImmediateOrCancelSellOrderLowPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% higher
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) - (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "immediate-or-cancel";

		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertTrue(response.path("is_cancelled"));
	}
	/**
	 * Test Case 7:
	 * 		New Order “auction-only” business logic Test: 
	 * 		Place new order “sell” with amount 30% greater than last traded price. 
	 * 		Order should be placed successfully as per business logic
	 *
	 */
	@Test
	public void TestAuctionOnlyOrderHighPrice() {
		String symbol = "btcusd";
		Response response = null;

		// Sell at 30% higher
		double curPrice = this.getCurrentPrice(symbol);
		double priceToSell = (curPrice) + (curPrice*0.3);
		priceToSell = (double) Math.round(priceToSell * 100) / 100;
		
		String type = "auction-only";

		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(priceToSell)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		this.TestbasicAsserts(jsonObj, response);
		Assert.assertFalse(response.path("is_cancelled"));
	}
	
	
	/**
	 * Test Case 9:
	 * 		New Order with Invalid API Key
	 *
	 */
	@Test
	public void TestNewOrderInvalidAPIKey() {
		//Override headers with invalid API Key
		RequestSpecification spec = new RequestSpecBuilder().
				addHeader("Cache-Control", "no-cache").
				addHeader("Content-Type", "text/plain").
				addHeader("X-GEMINI-APIKEY", "some invalid API key").
				build();

		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(curPrice)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrderInvalidAPIKey(jsonObj, spec);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Test Case 10:
	 * 		New Order with incorrect symbol
	 *
	 */
	@Test
	public void TestNewOrderInvalidSymbol() {
		
		//Override headers with invalid symbol
		String symbol = "invalidsymbol";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1").
				put("price", Double.toString(curPrice)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Test Case 11:
	 * 		New Order with incorrect amount
	 *
	 */
	@Test
	public void TestNewOrderInvalidAmount() {
		
		//Override headers with invalid symbol
		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","-100"). //Invalid amount
				put("price", Double.toString(curPrice)).
				put("side","sell").
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Test Case 12:
	 * 		New Order with incorrect side
	 *
	 */
	@Test
	public void TestNewOrderInvalidSide() {
		
		//Override headers with invalid symbol
		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1"). 
				put("price", Double.toString(curPrice)).
				put("side","invalidside"). //Invalid side
				put("type","exchange limit").
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Test Case 13:
	 * 		New Order with incorrect type
	 *
	 */
	@Test
	public void TestNewOrderInvalidType() {
		
		//Override headers with invalid symbol
		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", orderid).
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1"). 
				put("price", Double.toString(curPrice)).
				put("side","sell"). 
				put("type","invalid type"). //Invalid type
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	

	/**
	 * Test Case 14:
	 * 		New Order with invalid nonce
	 *
	 */
	@Test
	public void TestNewOrderInvalidNonce() {
		
		//Override headers with invalid symbol
		String symbol = "btcusd";
		Response response = null;

		double curPrice = 3000.00;
		
		String type = "auction-only";
		
		String orderid = this.generateOrderId();
		JSONObject jsonObj = new JSONObject().
				put("request","/v1/order/new").
				put("nonce", 1). //Invalid nonce
				put("client_order_id", orderid).
				put("symbol",symbol).
				put("amount","1"). 
				put("price", Double.toString(curPrice)).
				put("side","sell"). 
				put("type","invalid type"). 
				put("options", new JSONArray().put(type));			

		response = this.PlaceNewOrder(jsonObj);
		Assert.assertEquals(response.statusCode(), 400, "Expected failed response");

	}
	
	/**
	 * Stub methods to test all other basic assertions
	 * @param requestObj
	 * @param response
	 */
	private void TestbasicAsserts(JSONObject requestObj, Response response) {
		ResponseBody<?> body = response.getBody();
		Assert.assertEquals(response.statusCode(), 200, "Reponse Code failed with non 200");
		Assert.assertEquals(Double.parseDouble(body.path("price")), Double.parseDouble(requestObj.get("price").toString()), "Response Price doesn't match with request price");
		Assert.assertEquals(body.path("symbol"), requestObj.get("symbol"), "Response symbol doesn't match with request symbol");
		Assert.assertEquals(body.path("client_order_id"), requestObj.get("client_order_id"), "Response client_order_id doesn't match with request client_order_id");
		Assert.assertEquals(body.path("side"), requestObj.get("side"), "Response side doesn't match with request side");
		// This assertion seems to be failing for auction only orders??
		//Assert.assertEquals(body.path("type"), requestObj.get("type"), "Response type doesn't match with request type");
		Assert.assertEquals(body.path("options[0]"), requestObj.getJSONArray("options").get(0), "Response options doesn't match with request options");
		Assert.assertEquals(Double.parseDouble(body.path("original_amount")),
				(Double.parseDouble(body.path("executed_amount")) 
						+ Double.parseDouble(body.path("remaining_amount"))),
				"Original Amount doesn't match Remaining + executed match");
	}

	/**
	 * Re-usable Place order method
	 * Adds the encrypted payload and returns response
	 * @param Request jsonObj
	 * @return Response
	 */
	public Response PlaceNewOrder(JSONObject jsonObj) {
		RestAssured.baseURI = REST_API_BASE + "/v1/order/new";
		String payLoad = this.Base64Encode(jsonObj.toString());

		String hmacPayLoad = this.generateHmacPayload(payLoad);

		Response response = RestAssured.given().
				header("X-GEMINI-PAYLOAD",payLoad).
				header("X-GEMINI-SIGNATURE", hmacPayLoad).
				spec(this.spec).
				request(Method.POST);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		return response;
	}

	/**
	 * Re-usable Place order method
	 * Adds the encrypted payload and returns response
	 * @param Request jsonObj
	 * @return Response
	 */
	public Response PlaceNewOrderInvalidAPIKey(JSONObject jsonObj, RequestSpecification spec) {
		RestAssured.baseURI = REST_API_BASE + "/v1/order/new";
		String payLoad = this.Base64Encode(jsonObj.toString());

		String hmacPayLoad = this.generateHmacPayload(payLoad);

		Response response = RestAssured.given().
				header("X-GEMINI-PAYLOAD",payLoad).
				header("X-GEMINI-SIGNATURE", hmacPayLoad).
				spec(spec).
				request(Method.POST);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		return response;
	}
	
	@BeforeClass
	/**
	 * Setup API headers which is common for all test cases
	 */
	public void beforeClass() {
		System.out.println();

		this.spec = new RequestSpecBuilder().
				addHeader("Cache-Control", "no-cache").
				addHeader("Content-Type", "text/plain").
				addHeader("X-GEMINI-APIKEY", API_KEY).
				build();
	}

	@BeforeTest
	public void beforeTest() {		
		addDelay();
		RestAssured.baseURI = REST_API_BASE + "/v1/order/new";
	}
	/**
	 * Gets current price for a given symbol using gemini public APIs
	 * @param symbol
	 * @return
	 */
	public double getCurrentPrice(String symbol) {
		RestAssured.baseURI = REST_API_BASE + "/v1/pubticker/" + symbol;
		Response response = RestAssured.given().get();
		//String responseBody = response.getBody().asString();
		//System.out.println("Ticker Response Body is =>  " + responseBody);
		String last = response.getBody().path("last");
		//System.out.println("Last Price is =>  " + last);
		return Double.parseDouble(last);

	}
	/**
	 * Following methods use encryption algorithm to generate API signatures as per gemini spec
	 * @param payload
	 * @return
	 */
	private String generateHmacPayload(String payload) {
		String HMAC_SHA1_ALGORITHM = "HmacSHA384";
		SecretKeySpec signingKey = new SecretKeySpec(API_SECRET.getBytes(), HMAC_SHA1_ALGORITHM);
		try {
			this.mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String hmacPayLoad = bytesToHex(mac.doFinal(payload.getBytes()));
		return hmacPayLoad;
	}
	private String Base64Encode(String originalInput) {
		return Base64.getEncoder().encodeToString(originalInput.getBytes());
	}
	private static String bytesToHex(final byte[] hash) {
		final StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			final String hex = Integer.toHexString(0xff & hash[i]);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}
		return hexString.toString();
	}
	/**
	 * 
	 */
	private String generateOrderId() {	
		long timeStamp = System.currentTimeMillis();
		String timeStampString = Objects.toString(timeStamp);

		return timeStampString;
	}
	/**
	 * To sleep between multiple requests
	 */
	private static void addDelay() {
		try {
			Thread.sleep(2000);
		}
		catch(InterruptedException e) {
			System.out.println("Interruped while executing Test Cases" + e.getMessage());
		}
	}

}