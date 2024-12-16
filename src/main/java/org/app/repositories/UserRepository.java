package org.app.repositories;

import org.app.entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> userCache = new ArrayList<>();
    private  final String filePath = "D:/JavaProject/RSiOT_Forum/src/main/resources/users.txt";

    public void loadUserData(){

    }

    public void saveDataChanges(){

    }

    public List<User> getAllUsers(){
        return new ArrayList<>(userCache);
    }

    public void addUser(User user){
        userCache.add(user);
        saveDataChanges();
    }
}
