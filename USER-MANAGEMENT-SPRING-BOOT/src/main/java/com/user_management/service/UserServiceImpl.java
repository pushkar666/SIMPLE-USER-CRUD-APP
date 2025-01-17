package com.user_management.service;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.user_management.entity.User;
import com.user_management.globalException.UserCreationException;
import com.user_management.globalException.UserNotFoundException;
import com.user_management.globalException.UsersNotFoundException;
import com.user_management.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User saveUser(User user) throws UserCreationException {
		return userRepository.save(user);
	}

	@Override
	public Page<User> getAllUsers(int page, int size) throws UsersNotFoundException {
		Pageable pageable = PageRequest.of(page, size);
		return userRepository.findAll(pageable);
	}

	@Override
	public User getUserById(Integer id) throws UserNotFoundException {
		return userRepository.findById(id).get();
	}

}
