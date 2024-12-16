package org.app.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.app.entities.Message;
import org.app.entities.User;
import org.app.repositories.MessageRepository;
import org.app.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getAllMessages(){
        return messageRepository.getAllMessages();
    }

    public boolean addMessage(Message message){
        if (messageRepository.containsForbiddenWords(message)){
            return false;
        }
        messageRepository.addMessage(message);
        return true;
    }
}