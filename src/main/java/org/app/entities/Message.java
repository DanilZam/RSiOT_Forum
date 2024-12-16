package org.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Message {
    private int id;
    private int topicId;
    private int numberInTopic;
    private int userId;
    private String text;
}
