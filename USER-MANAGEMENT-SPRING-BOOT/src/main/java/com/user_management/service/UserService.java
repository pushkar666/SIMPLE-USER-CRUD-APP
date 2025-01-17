package com.user_management.service;

// import java.util.List;

import org.springframework.data.domain.Page;

import com.user_management.entity.User;
// import com.user_management.globalException.UserNotFoundException;
// import com.user_management.globalException.UsersNotFoundException;

public interface UserService {

	public User saveUser(User user);

	public Page<User> getAllUsers(int page, int size);

	public User getUserById(Integer id);

	// public String deleteUser(Integer id);

	// public User editProduct(User user, Integer id);
}
