package com.gisma.socialconnect.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Integer topicId;

    @Column(name = "topic_name", nullable = false, unique = true, length = 50)
    private String topicName;

    protected Topic() {
    }

    public Topic(String topicName) {
        this.topicName = topicName;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
