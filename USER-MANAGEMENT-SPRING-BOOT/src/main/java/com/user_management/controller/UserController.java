package com.user_management.controller;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import com.user_management.entity.User;
import com.user_management.service.UserService;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<?> saveUser(@Valid @RequestBody User user) {
		// return new ResponseEntity<>(userService.saveUser(user), HttpStatus.CREATED);
		// User savedUser = userService.saveUser(user);
		// if (savedUser == null) {
		// log.error("Failed to save user");
		// return new ResponseEntity<>("Failed to save user",
		// HttpStatus.INTERNAL_SERVER_ERROR);
		// }
		// return ResponseEntity.ok(savedUser);
		try {
			// Attempt to save the user
			User savedUser = userService.saveUser(user);
			if (savedUser == null) {
				log.error("USER NOT SAVED");
				ApiResponse<User> response = new ApiResponse<>(
						Status.FAILURE,
						null,
						"Failed to save user");
				return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			// Success response
			ApiResponse<User> response = new ApiResponse<>(
					Status.SUCCESS,
					savedUser,
					"User created successfully");
			return new ResponseEntity<>(response, HttpStatus.CREATED);
		} catch (Exception ex) {
			// Log the exception and return a failure response
			log.error("Exception in controller while saving user: {}", ex.getMessage(), ex);
			ApiResponse<User> response = new ApiResponse<>(
					Status.FAILURE,
					null,
					"An error occurred while saving the user");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @GetMapping
	// public ResponseEntity<?> getAllUsers() {
	// // return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
	// List<User> users = userService.getAllUsers();
	// if (users.isEmpty()) {
	// log.error("No users found");
	// return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
	// }
	// return ResponseEntity.ok(users);
	// }

	@GetMapping
	public ResponseEntity<ApiResponse<Page<User>>> getAllUsers(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		Page<User> users = userService.getAllUsers(page, size);
		if (users.isEmpty()) {
			ApiResponse<Page<User>> response = new ApiResponse<>(
					Status.FAILURE,
					null,
					"No users found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		ApiResponse<Page<User>> response = new ApiResponse<>(
				Status.SUCCESS,
				users,
				"Users fetched successfully");
		return ResponseEntity.ok(response);
	}

	// @GetMapping("/{id}")
	// public ResponseEntity<?> getUserById(@PathVariable Integer id) {
	// User user = userService.getUserById(id);
	// if (user == null) {
	// log.error("User not found with id {}", id);
	// return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	// }
	// return ResponseEntity.ok(user);
	// }
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Integer id) {
		User user = userService.getUserById(id);
		if (user == null) {
			log.error("User not found with id {}", id);
			ApiResponse<User> response = new ApiResponse<>(
					Status.FAILURE,
					null,
					"User not found");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}

		ApiResponse<User> response = new ApiResponse<>(
				Status.SUCCESS,
				user,
				"User fetched successfully");
		return ResponseEntity.ok(response);
	}

}
