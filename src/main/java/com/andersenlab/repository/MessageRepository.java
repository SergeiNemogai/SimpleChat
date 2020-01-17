package com.andersenlab.repository;

import com.andersenlab.model.Message;
import com.andersenlab.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Sergei Nemogai
 * created at 14.01.2020
 */

public interface MessageRepository extends JpaRepository<Message, Long>, JpaSpecificationExecutor<Message> {
    Message findFirstByUserOrderById(User user);
    List<Message> findAllBySendAtAfter(Timestamp userConnectedTime);
}
