package com.user_management.service;

import java.util.List;

import com.user_management.entity.User;
// import com.user_management.globalException.UserNotFoundException;
// import com.user_management.globalException.UsersNotFoundException;

public interface UserService {

	public User saveUser(User user);

	public List<User> getAllUsers();

	public User getUserById(Integer id);

	// public String deleteUser(Integer id);

	// public User editProduct(User user, Integer id);
}
