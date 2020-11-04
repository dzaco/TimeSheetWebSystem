package com.epstein.repository;

import com.epstein.entity.MessageSchema;
import com.epstein.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository  extends JpaRepository<Message,Integer> {

    Optional<Message> findByCode(String code);



}
