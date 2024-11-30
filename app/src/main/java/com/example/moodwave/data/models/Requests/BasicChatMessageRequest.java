package com.example.moodwave.data.models.Requests;

public class BasicChatMessageRequest {
    private String message_type;

    public BasicChatMessageRequest(String messageType) {
        message_type = messageType;
    }

    public String getMessage_type() {
        return message_type;
    }
}
