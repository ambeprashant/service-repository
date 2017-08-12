package com.demo.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
