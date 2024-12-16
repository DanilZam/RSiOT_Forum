package org.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private int id;
    private String fullName;
    private String nickname;
    private String registrationDate;
    private int activityRating;
}
