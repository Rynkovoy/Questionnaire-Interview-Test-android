package com.qit.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.rest.dto.InterviewDTO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InterviewAdapter extends BaseAdapter {

    private List<InterviewDTO> interviewDTOs;
    private LayoutInflater inflater;

    public InterviewAdapter(Context context, List<InterviewDTO> interviewDTOs) {
        this.interviewDTOs = interviewDTOs;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return interviewDTOs.size();
    }

    @Override
    public InterviewDTO getItem(int i) {
        return interviewDTOs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_interview_list, viewGroup, false);
        }

        CircleImageView civQuestionnaire = view.findViewById(R.id.civInterview);
        TextView tvTitle = view.findViewById(R.id.tvInterviewTitle);
        TextView tvTopic = view.findViewById(R.id.tvInterviewTopic);

        tvTitle.setText(getItem(i).getTitle());
        tvTopic.setText(getItem(i).getTopic());

        return view;
    }
}
