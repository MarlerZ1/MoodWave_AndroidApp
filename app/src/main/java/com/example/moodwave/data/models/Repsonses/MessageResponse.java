package com.example.moodwave.data.models.Repsonses;

public class MessageResponse {
    private String message_type;
    private int message_id;
    private Boolean from_me;
    private String name;
    private String text;
    private String logo_url;
    private String image_url;

    public MessageResponse(String messageType, int messageId, Boolean fromMe, String name, String text, String logoUrl, String imageUrl) {
        this.message_type = messageType;
        this.message_id = messageId;
        this.from_me = fromMe;
        this.name = name;
        this.text = text;
        this.logo_url = logoUrl;
        this.image_url = imageUrl;
    }

    public String getMessage_type() {
        return message_type;
    }

    public int getMessage_id() {
        return message_id;
    }

    public Boolean getFrom_me() {
        return from_me;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public String getImage_url() {
        return image_url;
    }
}
