package com.example.moodwave.data.models.Requests;

public class SendMessageFormRequest {
    private String text;

    public SendMessageFormRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


}
