package org.app.entities;

import ch.qos.logback.core.joran.sanity.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.AbstractMap;
import java.util.Map;

@NoArgsConstructor
@Data
public class Message {
    private int id;
    private AbstractMap.SimpleEntry<String, Integer> topicLink;
    private int numberInTopic;
    private AbstractMap.SimpleEntry<String, Integer> userLink;
    private String text;

    public Message(AbstractMap.SimpleEntry<String, Integer> topicLink, int numberInTopic, AbstractMap.SimpleEntry<String, Integer> userLink, String text) {
        this.topicLink = topicLink;
        this.numberInTopic = numberInTopic;
        this.userLink = userLink;
        this.text = text;
    }
}
