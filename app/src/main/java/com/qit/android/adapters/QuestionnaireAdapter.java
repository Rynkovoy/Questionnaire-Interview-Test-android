package com.qit.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.rest.dto.QuestionnaireDTO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuestionnaireAdapter extends BaseAdapter {

    private List<QuestionnaireDTO> questionnaireDTOs;
    private LayoutInflater inflater;

    public QuestionnaireAdapter(Context context, List<QuestionnaireDTO> questionnaireDTOs) {
        this.questionnaireDTOs = questionnaireDTOs;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return questionnaireDTOs.size();
    }

    @Override
    public QuestionnaireDTO getItem(int position) {
        return questionnaireDTOs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_questionnaire_list, viewGroup, false);
        }

        CircleImageView civQuiz = view.findViewById(R.id.civQuiz);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvTopic = view.findViewById(R.id.tvTopic);

        tvTitle.setText(getItem(position).getTitle());
        tvTopic.setText(getItem(position).getTopic());

        return view;
    }
}
