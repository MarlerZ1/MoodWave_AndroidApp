package com.example.moodwave.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodwave.R;
import com.example.moodwave.data.api.RetrofitClient;
import com.example.moodwave.data.models.Repsonses.MessageResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private List<MessageResponse> messages;

    public MessageAdapter(List<MessageResponse> messages) {
        this.messages = messages;
    }


    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item_layout, parent, false);
        return new MessageAdapter.MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        MessageResponse message = messages.get(position);

        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageSenderName;
        private TextView messageDescription;
        private ImageView userLogoFromMeImageView;
        private ImageView userLogoToMeImageView;
        private ImageView imageAttachmentImageView;


        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageSenderName = itemView.findViewById(R.id.messageSenderName);
            messageDescription = itemView.findViewById(R.id.messageDescription);
            userLogoFromMeImageView = itemView.findViewById(R.id.userLogoFromMeImageView);
            userLogoToMeImageView = itemView.findViewById(R.id.userLogoToMeImageView);
            imageAttachmentImageView = itemView.findViewById(R.id.imageAttachmentImageView);

        }

        public void bind(MessageResponse message) {
            messageSenderName.setText(message.getName());


            if (message.getText() == null || message.getText().isEmpty()) {
                messageDescription.setVisibility(View.GONE);
            } else{
                messageDescription.setText(message.getText());
            }

            if (message.getImage_url() == null || message.getImage_url().isEmpty()){
                imageAttachmentImageView.setVisibility(View.GONE);
            } else {
                Picasso.get()
                        .load(RetrofitClient.getURL().substring(0, RetrofitClient.getURL().length() - 1) + message.getImage_url())
                        .into(imageAttachmentImageView);
            }

            ImageView imageView;
            if (message.getFrom_me())
                imageView = userLogoFromMeImageView;
            else
                imageView = userLogoToMeImageView;

            if (message.getLogo_url() == null || message.getLogo_url().isEmpty()){
                imageView.setImageResource(R.drawable.default_logo);

            } else {
                Picasso.get()
                        .load(RetrofitClient.getURL().substring(0, RetrofitClient.getURL().length() - 1) + message.getLogo_url())
                        .into(imageView);
            }
        }

    }

};

