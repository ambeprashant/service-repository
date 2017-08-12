package com.demo.spring.service;

import java.util.List;

import com.demo.spring.domain.User;

public interface UserService {
	
	public List<User> findAllUser();

	public User findUserById(Long id);

}
