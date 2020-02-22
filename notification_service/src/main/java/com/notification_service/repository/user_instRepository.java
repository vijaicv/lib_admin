package com.notification_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notification_service.models.user_inst;

public interface user_instRepository extends JpaRepository<user_inst, Integer> {


List<user_inst> findByRole(String role);
}
