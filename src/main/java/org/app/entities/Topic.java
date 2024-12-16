package org.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class Topic {
    private int id;
    private String title;
    private String creationDate;
    private int messageCount;

    public Topic(String title, String creationDate, int messageCount) {
        this.title = title;
        this.creationDate = creationDate;
        this.messageCount = messageCount;
    }
}
