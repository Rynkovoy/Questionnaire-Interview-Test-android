package com.qit.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.rest.dto.QuestionnaireDTO;
import com.qit.android.rest.dto.QuizDTO;

import java.util.List;

public class QuizAdapter extends BaseAdapter {

    private List<QuestionnaireDTO> questionnaireDTOs;
    private LayoutInflater inflater;

    public QuizAdapter(Context context, List<QuestionnaireDTO> questionnaireDTOs) {
        this.questionnaireDTOs = questionnaireDTOs;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return questionnaireDTOs.size();
    }

    @Override
    public QuizDTO getItem(int i) {
        return questionnaireDTOs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_questionnaire_list, viewGroup, false);
        }

        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvTopic = view.findViewById(R.id.tvTopic);

        tvTitle.setText(getItem(i).getTitle());
        tvTopic.setText(getItem(i).getTopic());

        return view;
    }
}
