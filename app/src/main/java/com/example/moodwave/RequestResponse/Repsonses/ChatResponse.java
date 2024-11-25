package com.example.moodwave.RequestResponse.Repsonses;

public class ChatResponse {
    private String name;
    private String logo;
    private String message_text;
    private String format;
    private String chat_id;

    public ChatResponse(String name, String logo, String messageText, String format, String chatId) {
        this.name = name;
        this.logo = logo;
        this.message_text = messageText;
        this.format = format;
        this.chat_id = chatId;
    }


    public String getName() {
        return name;
    }

    public String getLogo() {
        return logo;
    }

    public String getMessage_text() {
        return message_text;
    }

    public String getFormat() {
        return format;
    }

    public String getChat_id() {
        return chat_id;
    }
}
