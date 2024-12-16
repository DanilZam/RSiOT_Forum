package org.app.repositories;

import org.app.entities.User;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    public UserRepository(){
        loadUserData();
    }

    static private List<User> userCache = new ArrayList<>();
    static private final String filePath = "D:/JavaProject/RSiOT_Forum/src/main/resources/users.txt";

    static public void loadUserData(){
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = parseUser(line);
                userCache.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка чтения файла пользователей.");
        }
    }

    static public User parseUser(String line) {
        String[] fields = line.split(";");
        int id = 0;
        String fullName = null;
        String nickname = null;
        String registrationDate = null;
        int activityRating = 0;

        for (String field : fields) {
            String[] keyValue = field.split(":");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "id" -> id = Integer.parseInt(value);
                case "fullName" -> fullName = value;
                case "nickname" -> nickname = value;
                case "registrationDate" -> registrationDate = value;
                case "activityRating" -> activityRating = Integer.parseInt(value);
            }
        }
        User user = new User(fullName, nickname, registrationDate, activityRating);
        user.setId(id);
        return user;
    }

    public void saveDataChanges(){
        try (FileWriter writer = new FileWriter(filePath)) {
            for (User user : userCache) {
                String line = String.format(
                        "id:%d;fullName:%s;nickname:%s;registrationDate:%s;activityRating:%d",
                        user.getId(), user.getFullName(), user.getNickname(),
                        user.getRegistrationDate(), user.getActivityRating()
                );
                writer.write(line + System.lineSeparator());
            }
            System.out.println("Список пользователей успешно записан в файл!");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ошибка записи данных в файл.");
        }
    }

    public List<User> getAllUsers(){
        return new ArrayList<>(userCache);
    }

    public void addUser(User user){
        int max_id = userCache.stream().mapToInt(User::getId).max().orElse(0);
        user.setId(max_id+1);
        userCache.add(user);
        saveDataChanges();
    }

    public void deleteUser(int id){
        userCache.removeIf(user -> user.getId() == id);
        saveDataChanges();
    }

    public void updateUser(User newUser){
        for (int i = 0; i < userCache.size(); i++) {
            if(userCache.get(i).getId() == newUser.getId()){
                userCache.set(i, newUser);
                saveDataChanges();
                break;
            }
        }
    }
}
