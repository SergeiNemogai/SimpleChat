package com.andersenlab.repository;

import com.andersenlab.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Sergei Nemogai
 * created at 14.01.2020
 */

public interface UserRepository extends CrudRepository<User, Long> {
}
