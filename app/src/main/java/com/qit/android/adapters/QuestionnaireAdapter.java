package com.qit.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.rest.dto.QuestionnaireDTO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.QuestionnaireViewHolder> {

    private List<QuestionnaireDTO> questionnaireDTOs;

    public class QuestionnaireViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView civQuestionnaire;
        public TextView tvTitle;
        public TextView tvTopic;

        public QuestionnaireViewHolder(View view) {
            super(view);
            civQuestionnaire = view.findViewById(R.id.civQuestionnaire);
            tvTitle = view.findViewById(R.id.tvQuestionnaireTitle);
            tvTopic = view.findViewById(R.id.tvQuestionnaireTopic);
        }
    }

    public QuestionnaireAdapter(List<QuestionnaireDTO> questionnaireDTOs) {
        this.questionnaireDTOs = questionnaireDTOs;
    }

    @Override
    public QuestionnaireViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_questionnaire_list, parent,false);

        return new QuestionnaireViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionnaireViewHolder holder, int position) {
        QuestionnaireDTO questionnaireDTO = questionnaireDTOs.get(position);
        holder.tvTitle.setText(questionnaireDTO.getTitle());
        holder.tvTopic.setText(questionnaireDTO.getTopic());
    }

    @Override
    public int getItemCount() {
        return questionnaireDTOs.size();
    }
}
