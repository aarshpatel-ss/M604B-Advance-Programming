package com.gisma.socialconnect.dto;

import com.gisma.socialconnect.model.Topic;

public class TopicResponse {

    private final Integer topicId;
    private final String topicName;

    private TopicResponse(Integer topicId, String topicName) {
        this.topicId = topicId;
        this.topicName = topicName;
    }

    public static TopicResponse fromEntity(Topic topic) {
        return new TopicResponse(topic.getTopicId(), topic.getTopicName());
    }

    public Integer getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }
}
