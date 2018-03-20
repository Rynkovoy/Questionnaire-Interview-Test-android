package com.qit.android.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.models.question.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        public ImageView civQuestionnaire;
        public TextView tvQuestion;
        public TextView tvIsNecessary;

        public int rImagesCivQuestionnaire[] = {R.drawable.qiz_img_1, R.drawable.qiz_img_2, R.drawable.qiz_img_3, R.drawable.qiz_img_4, R.drawable.qiz_img_5, R.drawable.qiz_img_6, R.drawable.qiz_img_7};

        public QuestionViewHolder(View view) {
            super(view);
            civQuestionnaire = view.findViewById(R.id.civQuestionnaire);
            civQuestionnaire.setImageResource(rImagesCivQuestionnaire[(int) (Math.random() * 7)]);

            tvQuestion = view.findViewById(R.id.tvQuestion);
            tvIsNecessary = view.findViewById(R.id.tvIsNecessary);
        }
    }

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question, parent,false);

        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.tvQuestion.setText(question.getText());
        if (!question.getIsNecessary()) {
            holder.tvIsNecessary.setVisibility(View.INVISIBLE);
        } else {
            holder.tvIsNecessary.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
