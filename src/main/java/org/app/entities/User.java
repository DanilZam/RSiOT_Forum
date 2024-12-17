package org.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class User {
    private int id;
    private String fullName;
    private String nickname;
    private String registrationDate;
    private int activityRating;
    private String password;


    public User(String fullName, String nickname, String registrationDate, int activityRating, String password) {
        this.fullName = fullName;
        this.nickname = nickname;
        this.registrationDate = registrationDate;
        this.activityRating = activityRating;
        this.password = password;
    }
}
