package org.app.repositories;

import org.app.entities.Topic;
import org.app.entities.Topic;

import java.util.ArrayList;
import java.util.List;

public class TopicRepository {
    private List<Topic> topicCache = new ArrayList<>();
    private  final String filePath = "D:/JavaProject/RSiOT_Forum/src/main/resources/topics.txt";

    public void loadTopicData(){

    }

    public void saveDataChanges(){

    }

    public List<Topic> getAllTopics(){
        return new ArrayList<>(topicCache);
    }

    public void addTopic(Topic topic){
        topicCache.add(topic);
        saveDataChanges();
    }
}
