package org.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Message {
    private int id;
    private int topicId;
    private int numberInTopic;
    private int userId;
    private String text;

    public Message(int topicId, int numberInTopic, int userId, String text) {
        this.topicId = topicId;
        this.numberInTopic = numberInTopic;
        this.userId = userId;
        this.text = text;
    }
}
