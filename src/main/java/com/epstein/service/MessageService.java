package com.epstein.service;

import com.epstein.entity.Message;
import com.epstein.entity.MessageSchema;
import com.epstein.entity.User;
import com.epstein.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired private MessageRepository messageRepository;




}
