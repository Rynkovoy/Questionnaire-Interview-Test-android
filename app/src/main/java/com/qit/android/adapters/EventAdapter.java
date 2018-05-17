package com.qit.android.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopeer.shadow.ShadowView;
import com.qit.R;
import com.qit.android.activity.NewEventOrChoseEventActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.activity.QuestionnaireCreationActivity;
import com.qit.android.models.event.Event;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.utils.BtnClickAnimUtil;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public List<Event> eventList;


    public static class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFullHeader;
        public TextView tvFullDetails;
        public TextView tvDate;

        public TextView tvEventOwner;
        public ImageView tvIsOpened;
        public Context context;

        public ShadowView cardView;

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
                .inflate(R.layout.item_event_card, parent, false);

        return new EventAdapter.EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EventAdapter.EventViewHolder holder, final int position) {

        holder.tvFullHeader.setText(eventList.get(position).getFullHeader());
        holder.tvFullDetails.setText(eventList.get(position).getFullDetails());
        holder.tvDate.setText(eventList.get(position).getDate());
        holder.tvEventOwner.setText(eventList.get(position).getEventOwnerName());

        if (eventList.get(position).isEventOpened()) {
            holder.tvIsOpened.setBackground(holder.context.getResources().getDrawable(R.drawable.icon_open));
            android.view.ViewGroup.LayoutParams layoutParams = holder.tvIsOpened.getLayoutParams();
            layoutParams.width = 128;
            layoutParams.height = 128;
            holder.tvIsOpened.setLayoutParams(layoutParams);
            //holder.tvIsOpened.setText("OPEN EVENT");
            // holder.tvIsOpened.setTextColor(holder.context.getResources().getColor(R.color.colorGreen));
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String date = eventList.get(position).getDate();
                    char[] a = date.toCharArray();
                    for (int x = 0; x < a.length; x++) {
                        if (a[x] == '.' || a[x] == ':' || a[x] == ' ') {
                            a[x] = '_';
                        }
                    }
                    date = new String(a);
                    FirebaseEventinfoGodObj.setFirebaseCurrentEventName("event_" + eventList.get(position).getEventOwner() + "_" + date);
                    holder.context.startActivity(new Intent(holder.context, QitActivity.class));
                }
            });
        } else if (!eventList.get(position).getEventPassword().equalsIgnoreCase("")) {
            holder.tvIsOpened.setBackground(holder.context.getResources().getDrawable(R.drawable.icon_password));
            android.view.ViewGroup.LayoutParams layoutParams = holder.tvIsOpened.getLayoutParams();
            layoutParams.width = 128;
            layoutParams.height = 128;
            holder.tvIsOpened.setLayoutParams(layoutParams);
            //holder.tvIsOpened.setText("NEED PASSWORD");
            //holder.tvIsOpened.setTextColor(holder.context.getResources().getColor(R.color.colorRed));


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(holder.context, "YOU NEED PASSWORD!", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder adb = new AlertDialog.Builder(holder.context);
                    LayoutInflater inflater = (LayoutInflater) holder.context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
                    view = (LinearLayout) inflater.inflate(R.layout.item_password_werification, null);
                    adb.setView(view);
                    final Dialog dialog = adb.create();
                    dialog.show();

                    final EditText editText = (EditText) view.findViewById(R.id.password_edit_text_to_event);
                    Button cancelbtn = (Button) view.findViewById(R.id.cancel_password_btn);
                    Button entetBtn = (Button) view.findViewById(R.id.enter_password_btn);

                    cancelbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, holder.context, 0);
                            dialog.dismiss();
                        }
                    });

                    entetBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, holder.context, 0);
                            if (editText.getText().toString().equalsIgnoreCase(eventList.get(position).getEventPassword())){

                                String date = eventList.get(position).getDate();
                                char [] a = date.toCharArray();
                                for (int x= 0 ; x < a.length; x++){
                                    if (a[x] == '.' || a[x] == ':' || a[x] == ' '){
                                        a[x] = '_';
                                    }
                                }
                                date = new String(a);
                                FirebaseEventinfoGodObj.setFirebaseCurrentEventName("event_"+eventList.get(position).getEventOwner()+"_"+date);
                                holder.context.startActivity(new Intent(holder.context, QitActivity.class));

                            } else {
                                Toast.makeText(holder.context, "WRONG PASSWORD, TRY AGAIN!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });







                }
            });

        } else if (eventList.get(position).isNewUserInEventeNeedToBeConfirmed()) {
            holder.tvIsOpened.setBackground(holder.context.getResources().getDrawable(R.drawable.icon_moderation));
            android.view.ViewGroup.LayoutParams layoutParams = holder.tvIsOpened.getLayoutParams();
            layoutParams.width = 128;
            layoutParams.height = 128;
            holder.tvIsOpened.setLayoutParams(layoutParams);
            //holder.tvIsOpened.setText("NEED CONFIRMATION");
            //holder.tvIsOpened.setTextColor(holder.context.getResources().getColor(R.color.colorDarkBlue));

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(holder.context, "YOU NEED TO BE CONFIRMED!", Toast.LENGTH_SHORT).show();
//                    String date = eventList.get(position).getDate();
//                    char [] a = date.toCharArray();
//                    for (int x= 0 ; x < a.length; x++){
//                        if (a[x] == '.' || a[x] == ':' || a[x] == ' '){
//                            a[x] = '_';
//                        }
//                    }
//                    date = new String(a);
//                    FirebaseEventinfoGodObj.setFirebaseCurrentEventName("event_"+eventList.get(position).getEventOwner()+"_"+date);
//                    holder.context.startActivity(new Intent(holder.context, QitActivity.class));
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


}
