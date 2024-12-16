package org.app.repositories;

import org.app.entities.Topic;
import org.app.entities.Topic;
import org.app.entities.Topic;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TopicRepository {
    public TopicRepository(){
        loadTopicData();
    }

    static private List<Topic> topicCache = new ArrayList<>();
    static private final String filePath = "D:/JavaProject/RSiOT_Forum/src/main/resources/topics.txt";

    static public void loadTopicData(){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Topic topic = parseTopic(line);
                topicCache.add(topic);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка чтения файла пользователей.");
        }
    }

    static public Topic parseTopic(String line) {
        String[] fields = line.split(";");
        int id = 0;
        String title = null;
        String creationDate = null;
        int messageCount = 0;

        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "id" -> id = Integer.parseInt(value);
                case "title" -> title = value;
                case "creationDate" -> creationDate = value;
                case "messageCount" -> messageCount = Integer.parseInt(value);
            }
        }
        Topic topic = new Topic(title, creationDate, messageCount);
        topic.setId(id);
        return topic;
    }

    public void saveDataChanges(){
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Topic topic : topicCache) {
                String line = String.format(
                        "id:%d;title:%s;creationDate:%s;messageCount:%d",
                        topic.getId(), topic.getTitle(), topic.getCreationDate(), topic.getMessageCount()
                );
                writer.write(line + System.lineSeparator());
            }
            System.out.println("Список пользователей успешно записан в файл!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка записи данных в файл.");
        }
    }

    public List<Topic> getAllTopics(){
        return new ArrayList<>(topicCache);
    }

    public void addTopic(Topic topic){
        int max_id = topicCache.stream().mapToInt(Topic::getId).max().orElse(0);
        topic.setId(max_id+1);
        topicCache.add(topic);
        saveDataChanges();
    }

    public void deleteTopic(int id){
        topicCache.removeIf(topic -> topic.getId() == id);
        saveDataChanges();
    }

    public void updateTopic(Topic newTopic){
        for (int i = 0; i < topicCache.size(); i++) {
            if(topicCache.get(i).getId() == newTopic.getId()){
                topicCache.set(i, newTopic);
                saveDataChanges();
                break;
            }
        }
    }
}
