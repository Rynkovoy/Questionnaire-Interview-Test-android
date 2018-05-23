package com.qit.android.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.Drawer;
import com.qit.R;
import com.qit.android.adapters.EventAdapter;
import com.qit.android.fragments.CreateEventFragment;
import com.qit.android.models.event.Event;
import com.qit.android.navigation.QitDrawerBuilder;
import com.qit.android.utils.BtnClickAnimUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewEventOrChoseEventActivity extends AppCompatActivity implements View.OnClickListener {

    private Button favoriteEventsBtn;
    private Button allEventsBtn;
    private Button myEventsBtn;
    private ImageView drawerIcon;
    private QitDrawerBuilder qitDrawerBuilder;

    private Button newEventBtn;

    private RecyclerView eventsRecyclerView;

    private SearchView searchView;
    private LinearLayout searchLayout;
    private FirebaseAuth mAuth;
    private List<Event> eventList;

    private List<Event> searchEventListener;

    public boolean isFragmentinBackStack = false;

    private boolean doubleBackToExitPressedOnce = false;

    private List<Button> btnsGroup = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_finder);

        addToolbar();

        drawerIcon = findViewById(R.id.drawer_icon);
        drawerIcon.setOnClickListener(this);

        favoriteEventsBtn = findViewById(R.id.btn_favorite_events);
        allEventsBtn = findViewById(R.id.btn_all_events);
        myEventsBtn = findViewById(R.id.btn_my_events);
        newEventBtn = findViewById(R.id.btn_create_new_event);
        eventsRecyclerView = findViewById(R.id.recyclerAllEvents);

        btnsGroup.add(favoriteEventsBtn);
        btnsGroup.add(allEventsBtn);
        btnsGroup.add(myEventsBtn);

        searchView = findViewById(R.id.searchPlace);
        //searchLayout = findViewById(R.id.searchLayout);

        newEventBtn.setOnClickListener(this);
        favoriteEventsBtn.setOnClickListener(this);
        myEventsBtn.setOnClickListener(this);
        allEventsBtn.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        eventList = new ArrayList<>();
        DatabaseReference databaseReference = database.getReference("event");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING DATA");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                eventList.clear();
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    Event eventObj = childDataSnapshot.getValue(Event.class);
                    eventList.add(eventObj);
                }
                Collections.sort(eventList, new Comparator<Event>() {
                    public int compare(Event o1, Event o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });
                Collections.reverse(eventList);
                UpdateUi(eventList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                UpdateUi(null);
            }
        });

//        searchLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                searchView.performClick();
//            }
//        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchEventListener = new ArrayList<>();
                for (int x = 0; x < eventList.size(); x++){
                    if(s.equalsIgnoreCase("")){
                        searchEventListener = eventList;
                    } else if (eventList.get(x).getEventOwner().toLowerCase().contains(s)){
                        searchEventListener.add(eventList.get(x));
                    } else if (eventList.get(x).getFullDetails().toLowerCase().contains(s)) {
                        searchEventListener.add(eventList.get(x));
                    } else if (eventList.get(x).getFullHeader().toLowerCase().contains(s)) {
                        searchEventListener.add(eventList.get(x));
                    }
                }
                UpdateUi(searchEventListener);
                return false;
            }
        });

        favoriteEventsBtn.performClick();
    }

    private void addToolbar() {
        qitDrawerBuilder = new QitDrawerBuilder(true);
        qitDrawerBuilder.setToolbar(null).build(this);
    }

    public void UpdateUi(List<Event> events) {
        EventAdapter eventAdapter = new EventAdapter(events);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        eventsRecyclerView.setLayoutManager(mLayoutManager);
        eventsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        eventsRecyclerView.setAdapter(eventAdapter);
    }

    @Override
    public void onClick(View view) {

        try {
            BtnClickAnimUtil btnClickAnimUtil = new BtnClickAnimUtil(view, this, btnsGroup);
        } catch (Exception e) {e.printStackTrace();}
        switch (view.getId()) {
            case (R.id.drawer_icon):{
                qitDrawerBuilder.drawer.openDrawer();
                break;
            }

            case (R.id.btn_create_new_event): {
                ConstraintLayout cl = findViewById(R.id.mainConstraintLayoutEventCreation);
                cl.setVisibility(View.GONE);

                LinearLayout ll = findViewById(R.id.fragment);
                ll.setVisibility(View.VISIBLE);

                isFragmentinBackStack = true;
                CreateEventFragment fragment = new CreateEventFragment(mAuth, cl, ll);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment, fragment);
                fragmentTransaction.addToBackStack(String.valueOf(fragment));
                fragmentTransaction.commit();

                break;
            }
            case (R.id.btn_my_events): {
//
//
//
//
//
//
//
//                need METHOD!!!
//
//
//
//
//
//
//                UpdateUi(favoriteEventList);
                break;
            }
            case (R.id.btn_favorite_events): {
                List<Event> favoriteEventList = new ArrayList<>();

                // Add to list all events that was created by your UID
                for (int x = 0; x < eventList.size(); x++) {
                    if (eventList.get(x).getEventOwner().equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                        favoriteEventList.add(eventList.get(x));
                    } else {
                        // Add to list all events in witch you are in coop already or you moderator
                        for (int y = 0; y < eventList.get(x).getEventCoop().size(); y++) {
                            if (eventList.get(x).getEventCoop().get(y).equalsIgnoreCase(mAuth.getCurrentUser().getUid()) || eventList.get(x).getEventModerators().get(y).equalsIgnoreCase(mAuth.getCurrentUser().getUid())) {
                                favoriteEventList.add(eventList.get(x));
                            }
                        }
                    }
                }
                UpdateUi(favoriteEventList);
                break;
            }
            case (R.id.btn_all_events): {
                UpdateUi(eventList);

                break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (!isFragmentinBackStack) {
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else {
            isFragmentinBackStack = false;
            super.onBackPressed();
        }
    }
}
