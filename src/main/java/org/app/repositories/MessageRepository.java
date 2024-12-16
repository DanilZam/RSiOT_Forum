package org.app.repositories;

import org.app.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository {

    private List<Message> messageCache = new ArrayList<>();
    private  final String filePath = "D:/JavaProject/RSiOT_Forum/src/main/resources/message.txt";

    public void loadMessageData(){

    }

    public void saveDataChanges(){

    }

    public List<Message> getAllMessages(){
        return new ArrayList<>(messageCache);
    }

    public void addMessage(Message message){
        messageCache.add(message);
        saveDataChanges();
    }

}
