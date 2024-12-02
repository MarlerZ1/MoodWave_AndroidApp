package com.example.moodwave.data.models.Requests;

public class SendMessageRequest extends BasicChatMessageRequest
{
    private int chat_id;
    private SendMessageFormRequest form_data;
    private String images_data;
    public SendMessageRequest(SendMessageFormRequest formData, int chatId) {
        super("send_message");
        form_data = formData;
        chat_id = chatId;
        images_data = "";
    }

    public SendMessageFormRequest getForm_data() {
        return form_data;
    }
}
