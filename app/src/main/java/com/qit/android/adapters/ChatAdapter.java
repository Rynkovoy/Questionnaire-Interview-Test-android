package com.qit.android.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.qit.R;
import com.qit.android.activity.NewEventOrChoseEventActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.activity.QuestionnaireCreationActivity;
import com.qit.android.models.chat.Message;
import com.qit.android.models.event.Event;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    public List<Message> messagesList;


    public static class ChatViewHolder extends RecyclerView.ViewHolder {

        public Context context;

        public TextView messageUser;
        public LinearLayout messageTextLayout;
        public TextView messageText;
        public TextView messageTime;
        public LinearLayout messageLayout;
        private FirebaseAuth mAuth;

        public ChatViewHolder(View view) {
            super(view);
            context = view.getContext();

            messageUser = view.findViewById(R.id.text_message_name);

            messageTextLayout = view.findViewById(R.id.messageLayoutText);
            messageTime = view.findViewById(R.id.text_message_time);
            messageText = view.findViewById(R.id.text_message_body);

            messageLayout = view.findViewById(R.id.messageLayout);
            mAuth = FirebaseAuth.getInstance();
        }
    }

    public ChatAdapter(List<Message> messageList) {
        this.messagesList = messageList;
    }

    @Override
    public ChatAdapter.ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_received, parent, false);

        return new ChatAdapter.ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        if(messagesList.get(position).getUser().getId().equalsIgnoreCase(holder.mAuth.getCurrentUser().getUid())){
            holder.messageLayout.setGravity(Gravity.RIGHT);
            Drawable myBackGround = holder.context.getResources().getDrawable( R.drawable.rounded_rectangle_blue );
            holder.messageTextLayout.setBackground(myBackGround);
        } else {
            holder.messageLayout.setGravity(Gravity.LEFT);
            Drawable myBackGround = holder.context.getResources().getDrawable( R.drawable.rounded_rectangle_orange );
            holder.messageTextLayout.setBackground(myBackGround);
        }

        holder.messageUser.setText(messagesList.get(position).getUser().getName());
        holder.messageText.setText(messagesList.get(position).getText());
        SimpleDateFormat formatter = new SimpleDateFormat("hh.mm");
        String folderName = formatter.format(messagesList.get(position).getCreatedAt());
        holder.messageTime.setText(folderName);
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }


}
