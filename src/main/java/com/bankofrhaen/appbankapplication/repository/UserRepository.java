package com.bankofrhaen.appbankapplication.repository;

import com.bankofrhaen.appbankapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
}
