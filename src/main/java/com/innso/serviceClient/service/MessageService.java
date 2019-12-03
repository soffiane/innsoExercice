package com.innso.serviceClient.service;

import com.innso.serviceClient.entities.Message;
import com.innso.serviceClient.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRespository;

    public List<Message> findAll() {
        return messageRespository.findAll();
    }

    public Optional<Message> findById(Long id) {
        return messageRespository.findById(id);
    }

    public Message save(Message message) {
        return messageRespository.save(message);
    }

    public void deleteById(Long id) {
        messageRespository.deleteById(id);
    }

    public Optional<Message> findByNameAndContent(String auteur, String message) {
        return messageRespository.findByNameAndContent(auteur,message);
    }
}
