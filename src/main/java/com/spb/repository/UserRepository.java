package com.spb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spb.entity.UserDTO;

public interface UserRepository extends JpaRepository<UserDTO, Integer> {
	UserDTO findByEmail(String email);
}
