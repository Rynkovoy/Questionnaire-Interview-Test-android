package com.qit.android.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qit.R;
import com.qit.android.models.event.Event;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public List<Event> eventList;


    public static class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFullHeader;
        public TextView tvFullDetails;
        public TextView tvDate;

        public TextView tvEventOwner;
        public TextView tvIsOpened;
        public Context context;

        public CardView cardView;

        public EventViewHolder(View view) {
            super(view);
            context = view.getContext();

            cardView = view.findViewById(R.id.layout_card);

            tvFullHeader = view.findViewById(R.id.tv_header_card);
            tvFullDetails = view.findViewById(R.id.tv_details_card);
            tvDate = view.findViewById(R.id.tv_date_card);

            tvEventOwner = view.findViewById(R.id.tv_owner_card);
            tvIsOpened = view.findViewById(R.id.tv_is_event_opened_card);

        }
    }

    public EventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event_card, parent,false);

        return new EventAdapter.EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventAdapter.EventViewHolder holder, int position) {

        holder.tvFullHeader.setText(eventList.get(position).getFullHeader());
        holder.tvFullDetails.setText(eventList.get(position).getFullDetails());
        holder.tvDate.setText(eventList.get(position).getDate());
        holder.tvEventOwner.setText(eventList.get(position).getEventOwner());
        if(eventList.get(position).isEventOpened()){
            holder.tvIsOpened.setText("OPEN EVENT");
            holder.tvIsOpened.setTextColor(holder.context.getResources().getColor(R.color.colorGreen));
        } else {
            holder.tvIsOpened.setText("NEED PASSWORD");
            holder.tvIsOpened.setTextColor(holder.context.getResources().getColor(R.color.colorRed));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO HERE NEED TO BE INTENT TO NEXT ACTIVITY
            }
        });

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }



}
