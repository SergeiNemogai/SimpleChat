package com.andersenlab.repository;

import com.andersenlab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @author Sergei Nemogai
 * created at 14.01.2020
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
