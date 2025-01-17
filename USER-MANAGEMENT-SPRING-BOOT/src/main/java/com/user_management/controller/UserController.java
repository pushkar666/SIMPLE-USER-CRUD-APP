package com.user_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user_management.entity.User;
import com.user_management.service.UserService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/saveUser")
	public ResponseEntity<?> saveUser(@RequestBody User user){
		
		return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
	}
	
	@GetMapping("/")
	public ResponseEntity<?> getAllUsers(){
		return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable Integer id){
		return ResponseEntity.ok(userService.getUserById(id));
		
	}
}
