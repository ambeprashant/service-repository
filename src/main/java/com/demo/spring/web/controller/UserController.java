package com.demo.spring.web.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.domain.User;
import com.demo.spring.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	public static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	// -------------------Retrieve All User--------//
    //URL=http://localhost:8080/sprintrest/user
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUser();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	// -------------------Retrieve user-------------------//
    //URL=http://localhost:8080/sprintrest/user/2
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		System.out.println("Fetching User with id " + id);
		User user = userService.findUserById(id);
		if (user == null) {
			System.out.println("User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	// -------------------Create a User----------------//
	
    //URl=http://localhost:8080/sprintrest/user
	
	/*User cretae Object please do not provide Id its auto increment
	{
	        
	        "emailId": "a@a.com",
	        "password": "1234",
	        "firstName": "Prashant",
	        "lastName": "Ambe"
	    }*/
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {

		if (null != user) {
			userService.createUser(user);
			return new ResponseEntity<User>(user, HttpStatus.CREATED);
		}

		return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
	}

	// ------------------- Update a User--------//
	 //URL=http://localhost:8080/sprintrest/user
	
		/*User Update/edit Object please  provide Id for update operation other wise it will create new entry.
		{
		        "id":2,
		        "emailId": "a@a.com",
		        "password": "1234",
		        "firstName": "Prashant",
		        "lastName": "Ambe"
		    }*/

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@RequestBody User user) {

		if (user == null) {

			return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);
		}

		user.setEmailId(user.getEmailId());
		user.setPassword(user.getPassword());
		user.setFirstName(user.getFirstName());
		user.setLastName(user.getFirstName());

		userService.updateUser(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	// ------------------- Delete a User------------//
	//URL=http://localhost:8080/sprintrest/user/2
 
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		System.out.println("Fetching & Deleting User with id " + id);

		User user = userService.findUserById(id);
		if (user == null) {
			System.out.println("Unable to delete. User with id " + id + " not found");
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}

		userService.deleteUserById(id);
		return new ResponseEntity<User>(HttpStatus.OK);
	}

	
	
}
