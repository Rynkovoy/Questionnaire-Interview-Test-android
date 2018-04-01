package com.qit.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.models.question.Question;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private List<Question> questionList;

    public static class QuestionViewHolder extends RecyclerView.ViewHolder {
        public TextView tvQuestion;
        public TextView tvIsNecessary;


        public QuestionViewHolder(View view) {
            super(view);

            tvQuestion = view.findViewById(R.id.question_haeder);
            //tvIsNecessary = view.findViewById(R.id.tvIsNecessary);
        }
    }

    public QuestionAdapter(List<Question> questionList) {
        this.questionList = questionList;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_question_questionnarie_answer, parent,false);

        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        Question question = questionList.get(position);
        holder.tvQuestion.setText(question.getText());
        //if (!question.getIsNecessary()) {
        //    holder.tvIsNecessary.setVisibility(View.INVISIBLE);
        //} else {
        //    holder.tvIsNecessary.setVisibility(View.VISIBLE);
        //}
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
