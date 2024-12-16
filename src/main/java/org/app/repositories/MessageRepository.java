package org.app.repositories;

import org.app.entities.Message;
import org.app.entities.Message;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class MessageRepository {

    public MessageRepository(){
        loadMessageData();
        loadForbiddenWords();
    }

    static private List<Message> messageCache = new ArrayList<>();
    static private final String filePath = "D:/JavaProject/RSiOT_Forum/src/main/resources/message.txt";
    static private final String censorFilePath = "D:/JavaProject/RSiOT_Forum/src/main/resources/censor.txt";
    static private final Set<String> forbiddenWords = new HashSet<>();

    static public void loadMessageData(){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Message message = parseMessage(line);
                messageCache.add(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка чтения файла пользователей.");
        }
    }

    static public Message parseMessage(String line) {
        String[] fields = line.split(";");
        int id = 0;
        int topicId = 0;
        int numberInTopic = 0;
        int userId = 0;
        String text = null;

        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "id" -> id = Integer.parseInt(value);
                case "topicId" -> topicId = Integer.parseInt(value);
                case "numberInTopic" -> numberInTopic = Integer.parseInt(value);
                case "userId" -> userId = Integer.parseInt(value);
                case "text" -> text = value;
            }
        }
        Message message = new Message(topicId, numberInTopic, userId, text);
        message.setId(id);
        return message;
    }

    public void saveDataChanges(){
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Message message : messageCache) {
                String line = String.format(
                        "id:%d;topicId:%d;numberInTopic:%d;userId:%d;text:%s",
                        message.getId(), message.getTopicId(), message.getNumberInTopic(),
                        message.getUserId(), message.getText()
                );
                writer.write(line + System.lineSeparator());
            }
            System.out.println("Список пользователей успешно записан в файл!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка записи данных в файл.");
        }
    }

    public List<Message> getAllMessages(){
        return new ArrayList<>(messageCache);
    }

    public void addMessage(Message message){
        int max_id = messageCache.stream().mapToInt(Message::getId).max().orElse(0);
        message.setId(max_id+1);
        messageCache.add(message);
        saveDataChanges();
    }

    public void deleteMessage(int id){
        messageCache.removeIf(message -> message.getId() == id);
        saveDataChanges();
    }

    public void updateMessage(Message newMessage){
        for (int i = 0; i < messageCache.size(); i++) {
            if(messageCache.get(i).getId() == newMessage.getId()){
                messageCache.set(i, newMessage);
                saveDataChanges();
                break;
            }
        }
    }

    static private void loadForbiddenWords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(censorFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(";");
                for (String word : words) {
                    forbiddenWords.add(word.trim().toLowerCase()); // Приведение к нижнему регистру
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка при загрузке списка запрещённых слов.");
        }
    }

    public Set<String> getForbiddenWords(){
        return forbiddenWords;
    }

    public boolean containsForbiddenWords(Message message){
        if (message.getText() == null || message.getText().isEmpty()) return false;

        String[] words = message.getText().split("\\s+");
        for (String word : words) {
            if (forbiddenWords.contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
