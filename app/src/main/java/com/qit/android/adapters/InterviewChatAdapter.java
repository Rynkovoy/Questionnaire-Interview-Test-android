package com.qit.android.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.activity.QuestionnaireAnswersActivity;
import com.qit.android.models.answer.Comment;
import com.qit.android.models.question.Question;
import com.qit.android.models.question.QuestionType;
import com.qit.android.models.quiz.Result;

import java.text.SimpleDateFormat;
import java.util.List;

public class InterviewChatAdapter extends RecyclerView.Adapter<InterviewChatAdapter.CommentViewHolder> {

    private List<Comment> commentList;

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        public TextView messageUser;
        public LinearLayout messageTextLayout;
        public TextView messageText;
        public TextView messageTime;
        public LinearLayout messageLayout;
        Context context;

        public CommentViewHolder(View view) {
            super(view);

            context = view.getContext();

            messageUser = view.findViewById(R.id.text_message_name);

            messageTextLayout = view.findViewById(R.id.messageLayoutText);
            messageTime = view.findViewById(R.id.text_message_time);
            messageText = view.findViewById(R.id.text_message_body);

            messageLayout = view.findViewById(R.id.messageLayout);
        }
    }

    public InterviewChatAdapter (List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_received, parent, false);

        return new CommentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {

            holder.messageLayout.setGravity(Gravity.LEFT);
            Drawable myBackGround = holder.context.getResources().getDrawable( R.drawable.rounded_rectangle_grey );
            holder.messageTextLayout.setBackground(myBackGround);

        holder.messageUser.setText(commentList.get(position).getCommentCreatorName());
        holder.messageText.setText(commentList.get(position).getComment());
        SimpleDateFormat formatter = new SimpleDateFormat("hh.mm");
        String folderName = formatter.format(commentList.get(position).getDateOfComment());
        holder.messageTime.setText(folderName);
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
