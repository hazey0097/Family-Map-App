package com.example.client.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.client.DataCache.DataCache;
import com.example.client.R;
import com.example.client.fragments.MapFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;



import Domain.Event;

public class EventActivity extends AppCompatActivity {
    private String eventID;
    private String personID;
    private Event eventSelected = null;
    private DataCache myDataCache = DataCache.getInstance();
    private static final String EVENT_ID = "eventID";
    private static final String PERSON_ID = "personID";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eventID = getIntent().getStringExtra(EVENT_ID);
        personID = getIntent().getStringExtra(PERSON_ID);
        eventSelected = new Event();

        FragmentManager fm = getSupportFragmentManager();
        MapFragment map = new MapFragment();

        map.setHasOptionsMenu(false);

        for(Event temp: myDataCache.getAllEvents().get(personID)){
            if(temp.getEventID().equals(eventID)){
                eventSelected = temp;
                map.setSelectedEventFromEvenActivity(eventSelected);
            }
        }
        fm.beginTransaction().add(R.id.event_container, map,"MAP").commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        if(menu.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return  true;
    }
}
