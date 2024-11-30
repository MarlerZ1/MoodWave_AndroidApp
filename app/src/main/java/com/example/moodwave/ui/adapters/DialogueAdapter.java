package com.example.moodwave.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moodwave.R;
import com.example.moodwave.data.models.Repsonses.ChatResponse;
import com.example.moodwave.data.api.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DialogueAdapter extends RecyclerView.Adapter<DialogueAdapter.DialogueViewHolder> {

    private List<ChatResponse> dialogues;

    public DialogueAdapter(List<ChatResponse> dialogues) {
        this.dialogues = dialogues;
    }

    @NonNull
    @Override
    public DialogueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialogue_item_layout, parent, false);
        return new DialogueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogueViewHolder holder, int position) {
        ChatResponse dialogue = dialogues.get(position);
        holder.bind(dialogue);
    }

    @Override
    public int getItemCount() {
        return dialogues.size();
    }

    public static class DialogueViewHolder extends RecyclerView.ViewHolder {
        private TextView dialogueTitleView;
        private TextView lastMessageTextView;
        private ImageView dialogueImageView;

        public DialogueViewHolder(@NonNull View itemView) {
            super(itemView);
            dialogueTitleView = itemView.findViewById(R.id.dialogueTitleView);
            lastMessageTextView = itemView.findViewById(R.id.dialogueLastMessageView);
            dialogueImageView = itemView.findViewById(R.id.dialogueLogoView);
        }

        public void bind(ChatResponse dialogue) {
            dialogueTitleView.setText(dialogue.getName());
            lastMessageTextView.setText(dialogue.getMessage_text());
            Picasso.get()
                    .load(RetrofitClient.getURL().substring(0, RetrofitClient.getURL().length() - 1) + dialogue.getLogo())
                    .into(dialogueImageView);

            System.out.println(dialogue.getLogo());
        }
    }
}
