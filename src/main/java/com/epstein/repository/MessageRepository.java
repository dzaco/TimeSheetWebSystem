package com.epstein.repository;

import com.epstein.entity.MessageSchema;
import com.epstein.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository  extends JpaRepository<Message,Integer> {

    @Query(value = "SELECT * FROM message WHERE user_id = :id AND status = 1", nativeQuery = true)
    List<Message> findAllByUserId(int id);
}
