package org.app.repositories;

import ch.qos.logback.core.joran.sanity.Pair;
import org.app.entities.Message;
import org.app.entities.Message;
import org.springframework.boot.json.JsonWriter;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

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
        AbstractMap.SimpleEntry<String, Integer> topicLink = null;
        int numberInTopic = 0;
        AbstractMap.SimpleEntry<String, Integer> userLink = null;
        String text = null;

        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "id" -> id = Integer.parseInt(value);
                case "topicLink" -> topicLink = parseLink(value);
                case "numberInTopic" -> numberInTopic = Integer.parseInt(value);
                case "userLink" -> userLink = parseLink(value);
                case "text" -> text = value;
            }
        }
        Message message = new Message(topicLink, numberInTopic, userLink, text);
        message.setId(id);
        return message;
    }

    public void saveDataChanges(){
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Message message : messageCache) {
                String line = String.format(
                        "id:%d;topicLink:(%s,%d);numberInTopic:%d;userLink:(%s,%d);text:%s",
                        message.getId(),
                        message.getTopicLink().getKey(), message.getTopicLink().getValue(),
                        message.getNumberInTopic(),
                        message.getUserLink().getKey(), message.getUserLink().getValue(),
                        message.getText()
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
        System.out.println(message.getText());
        if (message.getText() == null || message.getText().isEmpty()) return false;

        String[] words = message.getText().split("\\s+");
        for (String word : words) {
            System.out.println(word.toLowerCase().replaceAll("[^a-zA-Z]", ""));
            if (forbiddenWords.contains(word.toLowerCase().replaceAll("[^a-zA-Z]", ""))) {
                return true;
            }
        }
        return false;
    }

    static public AbstractMap.SimpleEntry<String, Integer> parseLink(String value){
        if (value == null || value.isBlank() || !value.startsWith("(") || !value.endsWith(")")) {
            throw new IllegalArgumentException("Неверный формат строки: " + value);
        }

        // Убираем скобки и разбиваем по ";"
        String[] parts = value.substring(1, value.length() - 1).split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Ожидался формат (key;value), получено: " + value);
        }

        try {
            String key = parts[0].trim();
            int intValue = Integer.parseInt(parts[1].trim());
            return new AbstractMap.SimpleEntry<>(key, intValue);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка преобразования числа из строки: " + value, e);
        }
    }
}
