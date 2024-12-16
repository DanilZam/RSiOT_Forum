package org.app.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class Topic {
    private int id;
    private String title;
    private String creationDate;
    private int messageCount;
}
