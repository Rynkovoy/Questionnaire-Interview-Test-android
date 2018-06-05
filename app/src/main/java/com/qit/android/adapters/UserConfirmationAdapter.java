package com.qit.android.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.models.chat.Message;
import com.qit.android.models.event.ConfirmedUser;
import com.qit.android.models.event.Event;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.utils.BtnClickAnimUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserConfirmationAdapter extends RecyclerView.Adapter<UserConfirmationAdapter.UserConfirmationViewHolder> {

    public List<ConfirmedUser> confirmedUserList;

    public class UserConfirmationViewHolder extends RecyclerView.ViewHolder {

        public Context context;

        public TextView tvUserName;
        public TextView tvDateOfAsk;
        public Button confirmBtn;

        public UserConfirmationViewHolder(View view) {
            super(view);
            context = view.getContext();

            tvUserName = view.findViewById(R.id.tvUserName);
            tvDateOfAsk = view.findViewById(R.id.tvDateOfAsk);
            confirmBtn = view.findViewById(R.id.confirm_btn);

        }
    }

    public UserConfirmationAdapter(List<ConfirmedUser> confirmedUsers) {
        this.confirmedUserList = confirmedUsers;
    }

    @NonNull
    @Override
    public UserConfirmationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_confirm, parent, false);
        return new UserConfirmationAdapter.UserConfirmationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserConfirmationViewHolder holder, final int position) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("user");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                List<String> names = new ArrayList<>();

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    users.add(childDataSnapshot.getValue(User.class));
                    names.add(childDataSnapshot.getKey());
                }

                for (int x = 0; x < users.size(); x++ ) {
                    if (names.get(x).equalsIgnoreCase(confirmedUserList.get(position).getId())){
                        holder.tvUserName.setText(users.get(x).getFirstName() + " " + users.get(x).getLastName());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        holder.tvDateOfAsk.setText(confirmedUserList.get(position).getAskDate().toString());


        if (confirmedUserList.get(position).isConfirmed()){
            holder.confirmBtn.setText("DISABLE USER");
        } else {
            holder.confirmBtn.setText("CONFIRM USER");
        }

        holder.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, holder.context, 0);
                confirmedUserList.get(position).setConfirmed(!confirmedUserList.get(position).isConfirmed());
                if (confirmedUserList.get(position).isConfirmed()){
                    holder.confirmBtn.setText("DISABLE USER");
                } else {
                    holder.confirmBtn.setText("CONFIRM USER");
                }

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("event/"+ FirebaseEventinfoGodObj.getFirebaseCurrentEventName());
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Event event = dataSnapshot.getValue(Event.class);
                        event.setConfirmedUserList(confirmedUserList);
                        myRef.setValue(event);
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
        return confirmedUserList.size();
    }


}
