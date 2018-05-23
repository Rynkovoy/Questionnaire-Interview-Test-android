package com.qit.android.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.qit.R;
import com.qit.android.activity.NewEventOrChoseEventActivity;
import com.qit.android.activity.QitActivity;
import com.qit.android.models.event.Event;
import com.qit.android.rest.api.FirebaseEventinfoGodObj;
import com.qit.android.rest.utils.QitFirebaseCreateEvent;
import com.qit.android.utils.BtnClickAnimUtil;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class CreateEventFragment  extends Fragment implements View.OnClickListener {

    private TextView saveEvent;
    private EditText nameEvent;
    private EditText descriptionEvent;
    private EditText passwordEvent;
    private FirebaseAuth mAuth;

    //TODO edit radio btns!
    private int currenRadioBtnId = 0;
    private RadioGroup rb;


    private Context context;
    private ConstraintLayout cl;
    private LinearLayout ll;

    @SuppressLint("ValidFragment")
    public CreateEventFragment (FirebaseAuth mAuth, ConstraintLayout cl, LinearLayout ll){
        this.mAuth = mAuth;
        this.cl = cl;
        this.ll = ll;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_event,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        context = view.getContext();

        saveEvent = view.findViewById(R.id.saveEventbtn);
        saveEvent.setOnClickListener(this);

        nameEvent = view.findViewById(R.id.eventNameEditText);
        descriptionEvent = view.findViewById(R.id.eventDescriptionEditText);
        passwordEvent = view.findViewById(R.id.eventPasswordEditText);

        //radioButtonConfirmation = view.findViewById(R.id.radioButtonConfirm);
        //radioButtonFreeEntrance = view.findViewById(R.id.radioButtonFreeEntrance);
        //radioButtonPassword = view.findViewById(R.id.radioButtonPassword);

        rb = (RadioGroup) view.findViewById(R.id.radioGroup);
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                currenRadioBtnId = checkedId;
                switch (checkedId) {
                    case (R.id.radioButtonConfirm) : {
                        passwordEvent.setVisibility(View.GONE);
                        break;
                    }
                    case (R.id.radioButtonFreeEntrance) : {
                        passwordEvent.setVisibility(View.GONE);
                        break;
                    }
                    case (R.id.radioButtonPassword) : {
                        passwordEvent.setVisibility(View.VISIBLE);
                        break;
                    }
                    default: {

                    }
                }
            }

        });

    }


    @Override
    public void onClick(View view) {
        BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, context, 0);
        switch (view.getId()){
            case (R.id.saveEventbtn):{
                // TODO if radio Btns are chosen show hidden field for password and equal it to not it null
                if(nameEvent.getText().toString().equalsIgnoreCase("") || descriptionEvent.getText().toString().equalsIgnoreCase("")){
                    Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show();
                } else {
                    if (currenRadioBtnId == R.id.radioButtonFreeEntrance || currenRadioBtnId == 0) {
                    Event event = new Event(nameEvent.getText().toString(), descriptionEvent.getText().toString(), "", "", mAuth.getCurrentUser().getUid(), new ArrayList<String>(), new ArrayList<String>(), true, false);
                    QitFirebaseCreateEvent qitFirebaseCreateEvent = new QitFirebaseCreateEvent(mAuth);
                    qitFirebaseCreateEvent.createEventInFirebase(context, event);
                    } else if (currenRadioBtnId == R.id.radioButtonPassword) {
                        Event event = new Event(nameEvent.getText().toString(), descriptionEvent.getText().toString(), "", passwordEvent.getText().toString(), mAuth.getCurrentUser().getUid(), new ArrayList<String>(), new ArrayList<String>(), false, false);
                        QitFirebaseCreateEvent qitFirebaseCreateEvent = new QitFirebaseCreateEvent(mAuth);
                        qitFirebaseCreateEvent.createEventInFirebase(context, event);
                    } else if (currenRadioBtnId == R.id.radioButtonConfirm) {
                        Event event = new Event(nameEvent.getText().toString(), descriptionEvent.getText().toString(), "", "", mAuth.getCurrentUser().getUid(), new ArrayList<String>(), new ArrayList<String>(), false, true);
                        QitFirebaseCreateEvent qitFirebaseCreateEvent = new QitFirebaseCreateEvent(mAuth);
                        qitFirebaseCreateEvent.createEventInFirebase(context, event);
                    }

                    //onPause();
                }

            }
        }
    }


    @Override
    public void onPause() {
        cl.setVisibility(View.VISIBLE);
        ll.setVisibility(View.GONE);
        super.onPause();
    }
}
