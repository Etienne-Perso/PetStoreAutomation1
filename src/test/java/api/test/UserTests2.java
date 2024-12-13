package api.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints;
import api.endpoints.UserEndpoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	public Logger logger; //For logs
	
	@BeforeClass
	void setup() {
		
		faker=new Faker(); 
		userPayload=new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//Logs
		logger=LogManager.getLogger(this.getClass());  
		
	}
	
	@Test(priority=1)
	public void testPostUser(){
		
		logger.info("****** Creating User ********");
		
		Response response=UserEndpoints2.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		logger.info("****** Created User ********");
		
	}
	
	
	@Test(priority=2)
	public void testGetUserByUsername() {
		
		logger.info("****** Get User info by username ********");
		
		Response response=UserEndpoints2.getUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("****** User info are captured ********");
	}
	
	
	@Test(priority=3)
	public void testUpdateUserByUsername() {
		
		logger.info("****** Update user by username ********");
		
		//Update payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response=UserEndpoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		//Cheking the updated data
		Response responseafterUpdate=UserEndpoints2.getUser(this.userPayload.getUsername());
		responseafterUpdate.then().log().all();
		Assert.assertEquals(responseafterUpdate.getStatusCode(),200);
		
		logger.info("******  User is updated ********");
	}
	
	@Test(priority=4)
	public void testDeleteUserByUsername() {
		
		logger.info("******* Delete user by username *********");
		
		Response response=UserEndpoints2.deleteUser(this.userPayload.getUsername());
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("******* User is deleted *********");
	}
}
