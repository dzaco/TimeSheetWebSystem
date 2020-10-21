package com.epstein.service;

import com.epstein.entity.Message;
import com.epstein.entity.User;
import com.epstein.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired private MessageRepository messageRepository;

    public List<Message> getAll() {
        return this.messageRepository.findAll();
    }

    public Message getById(int id) {
        return this.messageRepository.findById(id).orElse(new Message());
    }

    public Message save(Message message) {
        return this.messageRepository.save(message);
    }


    public List<Message> getUserMessages(User user) {
        return this.getUserMessages(user.getId());
    }
    public List<Message> getUserMessages(int id) {
        return this.messageRepository.findAllByUserId(id);
    }

    public boolean hasMessage(User user) {
        return this.getUserMessages(user.getId()).size() > 0;
    }


    public Message setStatus(int status, int id) {
        Message m = this.getById(id);
        m.setStatus(status);
        return this.save(m);
    }
}
