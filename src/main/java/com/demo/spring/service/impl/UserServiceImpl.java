package com.demo.spring.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.domain.User;
import com.demo.spring.repositories.UserRepository;
import com.demo.spring.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository ;
	
	public List<User> findAllUser() {
	
		return userRepository.findAll();
	}

	public User findUserById(Long id) {
		if(null!=id){
			return userRepository.findOne(id);
		}
		return null;
	}

	@Override
	public User createUser(User user) {
		User saveUser = null;
		if (null != user) {

			logger.debug(" User created");
			saveUser = this.userRepository.save(user);
		}

		return saveUser;
	}

	@Override
	public User deleteUserById(Long id) {
		if (null != id) {
			logger.debug("Delete User By id");
			this.userRepository.delete(id);
		}
		logger.debug("NOT found User");
		return null;
	}

	@Override
	public User updateUser(User user) {
		if (null != user) {
			logger.info("update User By Id");
			return this.userRepository.save(user);
		}
		logger.error("NOT found User");
		return null;
	}

	

}
