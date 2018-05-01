package com.qit.android.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qit.R;
import com.qit.android.activity.NewEventOrChoseEventActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.activity.QuestionnaireCreationActivity;
import com.qit.android.models.chat.Message;
import com.qit.android.models.event.Event;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    public List<Message> messagesList;


    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        public Context context;
        //public CardView cardView;

        public TextView messageUser;
        public TextView messageText;
        public TextView messageTime;

        public ChatViewHolder(View view) {
            super(view);
            context = view.getContext();
            //cardView = view.findViewById(R.id.layout_card);

            messageUser = view.findViewById(R.id.text_message_name);
            messageText = view.findViewById(R.id.text_message_body);
            messageTime = view.findViewById(R.id.text_message_time);

        }
    }

    public ChatAdapter(List<Message> eventList) {
        this.messagesList = eventList;
    }

    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_received, parent, false);

        return new ChatAdapter.ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.messageUser.setText(messagesList.get(position).getUser().getName());
        holder.messageText.setText(messagesList.get(position).getText());
        holder.messageTime.setText(messagesList.get(position).getCreatedAt().toString());
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }


}
