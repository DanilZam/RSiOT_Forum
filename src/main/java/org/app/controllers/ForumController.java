package org.app.controllers;

import org.app.entities.Message;
import org.app.entities.User;
import org.app.repositories.UserRepository;
import org.app.services.MessageService;
import org.app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/app")
public class ForumController {
    private final UserService userService;
    private final MessageService messageService;

    public ForumController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public boolean addMessage(){
        return messageService.addMessage(new Message(1,1,1,"/murder-1"));
    }
}
