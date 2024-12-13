package api.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/*
UserEndpoints.java
created to perform CRUD create, read, update, delete requests to the user API

*/

public class UserEndpoints {
	
	
	public static Response createUser(User payload){
		
		Response response=given()
								.accept(ContentType.JSON)
								.contentType("application/json")
								.body(payload)
								
						  .when()
								.post(Routes.post_url); 
		
		return response; 	
	}
	
	
	public static Response getUser(String userName){
		
		Response response=given()
								.pathParam("username", userName)
		
						  .when()
								.get(Routes.get_url);
		
		return response; 	
	}
	
	
	public static Response updateUser(String userName, User payload){
		
		Response response=given()
							.accept(ContentType.JSON)
							.contentType("application/json")
							.body(payload)
							.pathParam("username", userName)
		
						.when()
							.put(Routes.put_url);
		
		return response; 
	}
	
	
	public static Response deleteUser(String userName){
		
		Response response=given()
							.pathParam("username", userName)
		
						 .when()
							.delete(Routes.delete_url);
		return response; 
	}
}
