package com.qit.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.adapters.ChatAdapter;
import com.qit.android.models.chat.ChatUser;
import com.qit.android.models.chat.Message;
import com.qit.android.models.user.User;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChatFragment extends Fragment implements View.OnClickListener {

    private EditText messageEditText;
    private Button messageSendBtn;
    private RecyclerView messagesRV;
    private List<Message> messageListToAdapter = new ArrayList<>();

    private ChatAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_tab, container, false);

        messageEditText = view.findViewById(R.id.messageEditText);
        messageSendBtn = view.findViewById(R.id.sendMessageBtn);
        messageSendBtn.setOnClickListener(this);

        messagesRV = view.findViewById(R.id.messagesList);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        messagesRV.setLayoutManager(mLayoutManager);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("event/"+ FirebaseEventinfoGodObj.getFirebaseCurrentEventName()+"/chatRoom/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageListToAdapter = new ArrayList<>();
                for (DataSnapshot childs : dataSnapshot.getChildren()){
                    Message msgObj = childs.getValue(Message.class);
                    messageListToAdapter.add(msgObj);
                }
                mAdapter = new ChatAdapter(messageListToAdapter);
                messagesRV.setAdapter(mAdapter);
                messagesRV.scrollToPosition(messageListToAdapter.size()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }


    private DatabaseReference myRef;
    private List<Message> messageList = new ArrayList<>();

    @Override
    public void onClick(final View view) {
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        myRef = database.getReference("event/"+ FirebaseEventinfoGodObj.getFirebaseCurrentEventName()+"/chatRoom/");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageList = new ArrayList<>();
                for (DataSnapshot childs : dataSnapshot.getChildren()){
                    Log.i("LOG",childs.getValue().toString());
                    Message msgObj = childs.getValue(Message.class);
                    messageList.add(msgObj);
                }

                    Message message = null;
                    try {
                        message = new Message(UUID.randomUUID().toString(), new ChatUser(mAuth.getCurrentUser().getUid(), FirebaseEventinfoGodObj.getFirebaseUserFullName(), null, true), messageEditText.getText().toString());
                        Toast.makeText(view.getContext(), message.getUser().getName(), Toast.LENGTH_SHORT).show();
                        messageList.add(message);
                        myRef.setValue(messageList);
                        myRef.removeEventListener(this);
                    } catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(view.getContext(), "Message not send", Toast.LENGTH_SHORT).show();
                        myRef.removeEventListener(this);
                    }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
}
