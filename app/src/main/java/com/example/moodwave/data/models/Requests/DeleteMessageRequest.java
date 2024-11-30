package com.example.moodwave.data.models.Requests;

public class DeleteMessageRequest extends BasicChatMessageRequest {
    private int message_id;
    public DeleteMessageRequest(int messageId) {
        super("delete_message");
        message_id = messageId;
    }

    public int getMessage_id() {
        return message_id;
    }
}
