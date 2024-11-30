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
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int message_id);
    }
    public MessageAdapter(List<MessageResponse> messages, OnItemClickListener listener) {
        this.messages = messages;
        this.listener = listener;
    }

    public void addItem(MessageResponse message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }

    public void deleteItem(int message_id){
        for (int i = 0; i < messages.size(); i++){
            if (messages.get(i).getMessage_id() == message_id){
                messages.remove(i);
                notifyItemRemoved(i);
//                notifyItemRangeChanged(i, messages.size());
                return;
            }
        }
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
        holder.bind(message, message.getMessage_id(), listener);
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


        public void bind(MessageResponse message, int message_id, OnItemClickListener listener) {
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

            ImageView imageFromMeView;

            if (message.getFrom_me()) {
                imageFromMeView = userLogoFromMeImageView;
                userLogoToMeImageView.setVisibility(View.GONE);
            }
            else {
                imageFromMeView = userLogoToMeImageView;
                userLogoFromMeImageView.setVisibility(View.GONE);
            }

            if (message.getLogo_url() == null || message.getLogo_url().isEmpty()){
                imageFromMeView.setImageResource(R.drawable.default_logo);

            } else {
                Picasso.get()
                        .load(RetrofitClient.getURL().substring(0, RetrofitClient.getURL().length() - 1) + message.getLogo_url())
                        .into(imageFromMeView);
            }

            if (message.getFrom_me())
            {
                itemView.setOnClickListener(v -> {
                    if (listener != null) {
                        listener.onItemClick(message_id);
                    }
                });
            }
        }

    }

};

