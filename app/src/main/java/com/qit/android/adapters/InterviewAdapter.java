package com.qit.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.rest.dto.InterviewDTO;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InterviewAdapter extends RecyclerView.Adapter<InterviewAdapter.InterviewViewHolder> {

    private List<InterviewDTO> interviewDTOs;

    public static class InterviewViewHolder extends RecyclerView.ViewHolder {

        public CircleImageView civQuestionnaire;
        public TextView tvTitle;
        public TextView tvTopic;

        public InterviewViewHolder(View view) {
            super(view);
            civQuestionnaire = view.findViewById(R.id.civInterview);
            tvTitle = view.findViewById(R.id.tvInterviewTitle);
            tvTopic = view.findViewById(R.id.tvInterviewTopic);
        }
    }

    public InterviewAdapter(List<InterviewDTO> interviewDTOs) {
        this.interviewDTOs = interviewDTOs;
    }

    @Override
    public InterviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interview_list, parent,false);

        return new InterviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InterviewViewHolder holder, int position) {
        InterviewDTO interviewDTO = interviewDTOs.get(position);
        holder.tvTitle.setText(interviewDTO.getTitle());
        holder.tvTopic.setText(interviewDTO.getTopic());
    }

    @Override
    public int getItemCount() {
        return interviewDTOs.size();
    }

}
