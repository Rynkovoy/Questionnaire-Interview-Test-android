package com.qit.android.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopeer.shadow.ShadowView;
import com.qit.R;
import com.qit.android.activity.NewEventOrChoseEventActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.activity.QuestionnaireCreationActivity;
import com.qit.android.models.event.ConfirmedUser;
import com.qit.android.models.event.Event;
import com.qit.android.models.quiz.Interview;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.utils.BtnClickAnimUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    public List<Event> eventList = new ArrayList<>();

    public static class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView tvFullHeader;
        public TextView tvFullDetails;
        public TextView tvDate;
        private ImageView starBtn;

        public TextView tvEventOwner;
        public ImageView tvIsOpened;
        public Context context;

        public ShadowView cardView;

        private FirebaseAuth mAuth;

        public EventViewHolder(View view) {
            super(view);
            context = view.getContext();

            cardView = view.findViewById(R.id.layout_card);

            starBtn = view.findViewById(R.id.tv_star);

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
        holder.starBtn.setBackground(holder.context.getDrawable(R.drawable.star_unpressed));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                holder.mAuth = FirebaseAuth.getInstance();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot.getKey().equalsIgnoreCase(holder.mAuth.getUid())) {
                        User user = childDataSnapshot.getValue(User.class);

                        for (int x = 0; x < user.getFavoriteEvents().size(); x++) {
                            if (user.getFavoriteEvents().get(x).equalsIgnoreCase(eventList.get(position).getFullHeader())) {
                                holder.starBtn.setBackground(holder.context.getDrawable(R.drawable.star_pressed));
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

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
                    Activity activity = (Activity) holder.context;
                    activity.finish();
                }
            });
        } else if (!eventList.get(position).getEventPassword().equalsIgnoreCase("")) {
            holder.tvIsOpened.setBackground(holder.context.getResources().getDrawable(R.drawable.icon_password));
            android.view.ViewGroup.LayoutParams layoutParams = holder.tvIsOpened.getLayoutParams();
            layoutParams.width = 128;
            layoutParams.height = 128;
            holder.tvIsOpened.setLayoutParams(layoutParams);


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(holder.context, R.string.need_pasw, Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder adb = new AlertDialog.Builder(holder.context);
                    LayoutInflater inflater = (LayoutInflater) holder.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    view = inflater.inflate(R.layout.item_password_werification, null);
                    adb.setView(view);
                    final Dialog dialog = adb.create();
                    dialog.show();

                    final EditText editText = view.findViewById(R.id.password_edit_text_to_event);
                    Button cancelbtn = view.findViewById(R.id.cancel_password_btn);
                    Button entetBtn = view.findViewById(R.id.enter_password_btn);

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
                            if (editText.getText().toString().equalsIgnoreCase(eventList.get(position).getEventPassword())) {

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

                            } else {
                                Toast.makeText(holder.context, R.string.wrong_pasw, Toast.LENGTH_SHORT).show();
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

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    boolean flagAlreadyConfirmed = false;
                    holder.mAuth = FirebaseAuth.getInstance();
                    for (int x = 0; x < eventList.get(position).getConfirmedUserList().size(); x++){
                        if (eventList.get(position).getConfirmedUserList().get(x).getId().equalsIgnoreCase(holder.mAuth.getUid())
                                && eventList.get(position).getConfirmedUserList().get(x).isConfirmed()){
                            flagAlreadyConfirmed = true;
                        }
                    }

                    if (eventList.get(position).getEventOwner().equalsIgnoreCase(holder.mAuth.getUid()) || flagAlreadyConfirmed) {
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
                        Activity activity = (Activity) holder.context;
                        activity.finish();
                    } else {

                        AlertDialog.Builder adb = new AlertDialog.Builder(holder.context);
                        LayoutInflater inflater = (LayoutInflater) holder.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        view = inflater.inflate(R.layout.item_ask_for_event_entrance, null);
                        adb.setView(view);
                        final Dialog dialog = adb.create();
                        dialog.show();

                        Button cancelbtn = view.findViewById(R.id.cancel_password_btn);
                        Button entetBtn = view.findViewById(R.id.enter_password_btn);

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

                                Toast.makeText(holder.context, R.string.need_confirm, Toast.LENGTH_SHORT).show();
                                boolean flagIsThatUserOnList = false;
                                for (int x = 0; x < eventList.get(position).getConfirmedUserList().size(); x++) {
                                    if (eventList.get(position).getConfirmedUserList().get(x).getId().equalsIgnoreCase(holder.mAuth.getUid())) {
                                        flagIsThatUserOnList = true;
                                    }
                                }

                                if (!flagIsThatUserOnList) {
                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    final DatabaseReference myRef = database.getReference("event");
                                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            int counter = 0;
                                            DatabaseReference mMyRef = null;
                                            for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                                                if (eventList.size()-1-counter == position) {
                                                    mMyRef = database.getReference("event" + "/" + childDataSnapshot.getKey());
                                                }
                                                counter++;
                                            }
                                            Event event = eventList.get(position);
                                            event.getConfirmedUserList().add(new ConfirmedUser(holder.mAuth.getUid(), new Date()));
                                            mMyRef.setValue(event);

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                                dialog.dismiss();
                            }
                        });

                    }

                }
            });
        }

        holder.starBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("user");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                            FirebaseAuth mAuth = FirebaseAuth.getInstance();
                            if (childDataSnapshot.getKey().equalsIgnoreCase(mAuth.getUid())) {
                                User user = childDataSnapshot.getValue(User.class);

                                boolean isThisOnList = false;
                                for (int x = 0; x < user.getFavoriteEvents().size(); x++) {
                                    if (user.getFavoriteEvents().get(x).equalsIgnoreCase(eventList.get(position).getFullHeader())) {
                                        isThisOnList = true;
                                        user.getFavoriteEvents().remove(x);
                                        holder.starBtn.setBackground(holder.context.getDrawable(R.drawable.star_unpressed));
                                    }
                                }

                                if (!isThisOnList) {
                                    user.getFavoriteEvents().add(eventList.get(position).getFullHeader());
                                    holder.starBtn.setBackground(holder.context.getDrawable(R.drawable.star_pressed));
                                }
                                DatabaseReference myRef = database.getReference("user/" + mAuth.getUid());
                                myRef.setValue(user);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


}
