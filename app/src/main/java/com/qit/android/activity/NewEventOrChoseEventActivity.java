package com.qit.android.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qit.R;
import com.qit.android.adapters.EventAdapter;
import com.qit.android.models.event.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewEventOrChoseEventActivity extends AppCompatActivity implements View.OnClickListener {

    private Button favoriteEventsBtn;
    private Button allEventsBtn;

    private Button newEventBtn;

    private RecyclerView eventsRecyclerView;

    private SearchView searchView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_finder);

        favoriteEventsBtn = findViewById(R.id.btn_favorite_events);
        allEventsBtn = findViewById(R.id.btn_all_events);
        newEventBtn = findViewById(R.id.btn_create_new_event);
        eventsRecyclerView = findViewById(R.id.recyclerAllEvents);
        searchView = findViewById(R.id.searchPlace);

        newEventBtn.setOnClickListener(this);
        favoriteEventsBtn.setOnClickListener(this);
        allEventsBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        final List<Event> event = new ArrayList<>();
        DatabaseReference databaseReference = database.getReference("event");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING DATA");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                event.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Event eventObj = childDataSnapshot.getValue(Event.class);
                    event.add(eventObj);
                }
                UpdateUi(event);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            UpdateUi(null);
            }
        });
    }

    public void UpdateUi(List<Event> events){
        EventAdapter eventAdapter = new EventAdapter(events);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        eventsRecyclerView.setLayoutManager(mLayoutManager);
        eventsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        eventsRecyclerView.setAdapter(eventAdapter);
    }

    @Override
    public void onClick(View view) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_s");
        String strDate = sdf.format(c.getTime());

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("event"+"/event_"+mAuth.getCurrentUser().getUid()+"_"+strDate);

        myRef.setValue(new Event("TEST", "TEST TEST TEST", strDate, "", mAuth.getCurrentUser().getUid(), new ArrayList<String>(), new ArrayList<String>(), true, false));
    }
}
