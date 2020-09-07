package com.example.client.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.client.DataCache.DataCache;
import com.example.client.R;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import Domain.Event;
import Domain.Person;

public class PersonActivity  extends AppCompatActivity {
    private DataCache myDataCache = DataCache.getInstance();
    private List<Event> eventsForPerson;
    private List<Person> familyForPerson;
    final static String PERSON_ID = "personID";
    private String personIDSelected;
    private TextView userFirstName;
    private TextView userLastName;
    private TextView gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        personIDSelected = getIntent().getStringExtra(PERSON_ID);
        userFirstName = findViewById(R.id.userFirstName);
        userFirstName.setText(myDataCache.getAllPeople().get(personIDSelected).getFirstName());
        userLastName = findViewById(R.id.userLastName);
        userLastName.setText(myDataCache.getAllPeople().get(personIDSelected).getLastName());
        gender = findViewById(R.id.userGender);
        if(myDataCache.getAllPeople().get(personIDSelected).getGender() =='m'){
            gender.setText("Male");
        }
        else{
            gender.setText("Female");
        }
        ExpandableListView expandableListView = findViewById(R.id.expandableListView);

        // setting up lists
        eventsForPerson = new ArrayList<>();
        familyForPerson = new ArrayList<>();
        this.setUpEventsForUser();
        this.setUpFamilyForUser();

        expandableListView.setAdapter(new ExpandableListAdapter(eventsForPerson, familyForPerson));
    }
    /*public Intent newIntent(Class<PersonActivity> context, String personID){
        Intent intent = new Intent(this, PersonActivity.class);
        intent.putExtra(PERSON_ID, personID);
        return intent;
    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        if(menu.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }
    public void getDataForPerson(Person personClicked){
        eventsForPerson.addAll(myDataCache.getAllEvents().get(personClicked.getPersonID()));
    }
    private class ExpandableListAdapter extends BaseExpandableListAdapter {
        private static final int EVENT_GROUP_POSITION = 0;
        private static final int FAMILY_GROUP_POSITION = 1;

        private final List<Event> events;
        private final List<Person> family;

        ExpandableListAdapter(List<Event> events, List<Person> family) {
            this.events = events;
            this.family = family;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return events.size();
                case FAMILY_GROUP_POSITION:
                    return family.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return getString(R.string.eventsTitle);
                case FAMILY_GROUP_POSITION:
                    return getString(R.string.familyTitle);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    return events.get(childPosition);
                case FAMILY_GROUP_POSITION:
                    return family.get(childPosition);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case EVENT_GROUP_POSITION:
                    titleView.setText(R.string.eventsTitle);
                    break;
                case FAMILY_GROUP_POSITION:
                    titleView.setText(R.string.familyTitle);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch(groupPosition) {
                case  EVENT_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.event_item, parent, false);
                    initializeEventView(itemView, childPosition);
                    break;
                case FAMILY_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.person_item, parent, false);
                    initializePersonView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return itemView;
        }

        private void initializeEventView(View eventItemView, final int childPosition) {
            if(events.get(childPosition) != null){
                TextView eventInformation = eventItemView.findViewById(R.id.eventInformation);
                Person userOfEvent = myDataCache.getAllPeople().get(events.get(childPosition).getPersonID());
                String information = events.get(childPosition).getEventType().toUpperCase() + ": " + events.get(childPosition).getCity() + ", "
                        + events.get(childPosition).getCountry() + " (" + events.get(childPosition).getYear() + ")";
                eventInformation.setText(information);

                TextView eventUserName = eventItemView.findViewById(R.id.nameOfUser);
                eventUserName.setText(userOfEvent.getFirstName() + " " + userOfEvent.getLastName());

                eventItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newEventIntent = new Intent(PersonActivity.this, EventActivity.class);
                        newEventIntent.putExtra(PERSON_ID, personIDSelected);
                        newEventIntent.putExtra("eventID", events.get(childPosition).getEventID());
                        startActivity(newEventIntent);
                    }
                });
            }
        }
        private void initializePersonView(View personItemView, final int childPosition) {
            if(family.get(childPosition) != null){
                TextView nameOfPerson = personItemView.findViewById(R.id.name);
                nameOfPerson.setText(family.get(childPosition).getFirstName() + " " + family.get(childPosition).getLastName());

                TextView relationship = personItemView.findViewById(R.id.relationship);
                String relationshipType = getRelationship(childPosition);
                relationship.setText(relationshipType);


                ImageView icon = personItemView.findViewById(R.id.icon);
                if(family.get(childPosition).getGender() == 'm'){
                    icon.setImageResource(R.drawable.ic_man_24dp);
                }
                else{
                    icon.setImageResource(R.drawable.ic_woman_24dp);
                }
                personItemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent newPersonIntent = new Intent(PersonActivity.this, PersonActivity.class);
                        newPersonIntent.putExtra(PERSON_ID, familyForPerson.get(childPosition).getPersonID());
                        startActivity(newPersonIntent);
                    }
                });
            }

        }
        private String getRelationship(final int childPosition){
            return myDataCache.findRelationship(familyForPerson.get(childPosition), myDataCache.getAllPeople().get(personIDSelected));
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    public void setUpEventsForUser(){
        HashSet<Event> eventsOfUser =  new HashSet<>();
        boolean filterControl = myDataCache.isMaleLine() || myDataCache.isFemaleLine() || myDataCache.isFatherLine() || myDataCache.isMotherLine();
        HashSet<Integer> yearsOfEvents = new HashSet<>();
        if(myDataCache.isMaleLine()){
            for(Event temp: myDataCache.getEventsMale()){
                if(personIDSelected.equals(temp.getPersonID())){
                    eventsOfUser.add(temp);
                    yearsOfEvents.add(temp.getYear());
                }
            }
        }
        if(myDataCache.isFemaleLine()){
            for(Event temp: myDataCache.getEventsFemale()){
                if(personIDSelected.equals(temp.getPersonID())){
                    eventsOfUser.add(temp);
                    yearsOfEvents.add(temp.getYear());
                }
            }
        }
        if(myDataCache.isFatherLine()){
            for(Event temp: myDataCache.getEventFatherSide()){
                if(personIDSelected.equals(temp.getPersonID())){
                    eventsOfUser.add(temp);
                    yearsOfEvents.add(temp.getYear());
                }
            }
        }
        if(myDataCache.isMotherLine()){
            for(Event temp: myDataCache.getEventMotherSide()){
                if(personIDSelected.equals(temp.getPersonID())){
                    eventsOfUser.add(temp);
                    yearsOfEvents.add(temp.getYear());
                }
            }
        }
        if(!filterControl){
            eventsOfUser.addAll(myDataCache.getAllEvents().get(personIDSelected));
            for(Event temp: myDataCache.getAllEvents().get(personIDSelected)){
                yearsOfEvents.add(temp.getYear());
            }
        }
        eventsForPerson = myDataCache.orderedEvents(yearsOfEvents, eventsOfUser);
    }
    public void setUpFamilyForUser(){
        for(Map.Entry<String, Person> temp: myDataCache.getAllPeople().entrySet()){
            if(temp.getValue().getFatherID() != null && temp.getValue().getFatherID().equals(personIDSelected)){
                familyForPerson.add(temp.getValue());
            }
            if(temp.getValue().getMotherID() !=  null && temp.getValue().getMotherID().equals(personIDSelected)){
                familyForPerson.add(temp.getValue());
            }
            if(temp.getValue().getSpouseID() != null && temp.getValue().getSpouseID().equals(personIDSelected)){
                familyForPerson.add(temp.getValue());
            }
        }
        if(myDataCache.getAllPeople().get(myDataCache.getAllPeople().get(personIDSelected).getMotherID()) != null){
            familyForPerson.add(myDataCache.getAllPeople().get(myDataCache.getAllPeople().get(personIDSelected).getMotherID()));
        }
        if(myDataCache.getAllPeople().get(myDataCache.getAllPeople().get(personIDSelected).getFatherID()) != null){
            familyForPerson.add(myDataCache.getAllPeople().get(myDataCache.getAllPeople().get(personIDSelected).getFatherID()));
        }

    }
}
