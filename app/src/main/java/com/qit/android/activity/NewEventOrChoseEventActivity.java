package com.qit.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

        List<Event> event = new ArrayList<>();
        //TODO HERE NEED TO BE FIREBASSE AND GETTING DATA FROM IT TO LIST VIEW

        event.add(new Event("TEST", "TEST TEST TEST", "21.01.2008", "", mAuth.getCurrentUser().getUid(), null, null, true, false));
        event.add(new Event("TEST", "TEST TEST TEST", "21.01.2008", "", mAuth.getCurrentUser().getUid(), null, null, true, false));
        event.add(new Event("TEST", "TEST TEST TEST", "21.01.2008", "", mAuth.getCurrentUser().getUid(), null, null, true, false));
        event.add(new Event("TEST", "TEST TEST TEST", "21.01.2008", "", mAuth.getCurrentUser().getUid(), null, null, true, false));
        event.add(new Event("TEST", "TEST TEST TEST", "21.01.2008", "", mAuth.getCurrentUser().getUid(), null, null, true, false));

        EventAdapter eventAdapter = new EventAdapter(event);
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
        myRef.setValue(new Event("TEST", "TEST TEST TEST", strDate, "", mAuth.getCurrentUser().getUid(), null, null, true, false));
    }
}
