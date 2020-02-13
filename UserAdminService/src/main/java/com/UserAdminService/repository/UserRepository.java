package com.UserAdminService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UserAdminService.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	List<User> findTopByOrderByIdDesc();
}
