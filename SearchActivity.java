package com.example.client.activities;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.example.client.DataCache.DataCache;
import com.example.client.R;

import Domain.Event;
import Domain.Person;

public class SearchActivity extends AppCompatActivity {
    private static final int PERSON_ITEM_VIEW_TYPE = 0;
    private static final int EVENT_ITEM_VIEW_TYPE = 1;
    private DataCache myDataCache = DataCache.getInstance();
    private SearchView mySearchEngine;
    private List<Event> events;
    private List<Person> people;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        events = getEvents();
        people = getPeople();
        System.out.println(events.size());
        System.out.println(people.size());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mySearchEngine = findViewById(R.id.searchEngine);
        mySearchEngine.setIconifiedByDefault(true);


        mySearchEngine.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerView = findViewById(R.id.RecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                SearchClient adapter = new SearchClient(updateEventsList(query), updatePeopleList(query));
                recyclerView.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mySearchEngine.getQuery().length() == 0) {
                    recyclerView = findViewById(R.id.RecyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
                    SearchClient adapter = new SearchClient(updateEventsList(newText), updatePeopleList(newText));
                    recyclerView.setAdapter(adapter);
                }
                return true;
            }
        });
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
    private class SearchClient extends RecyclerView.Adapter<SearchViewHolder>{
        private final List<Event> events;
        private final List<Person> people;

        SearchClient(List<Event> events, List<Person> people) {
            this.events = events;
            this.people = people;
        }

        @Override
        public int getItemViewType(int position) {
            return position < people.size() ? PERSON_ITEM_VIEW_TYPE : EVENT_ITEM_VIEW_TYPE;
        }

        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.person_item, parent, false);
            } else {
                view = getLayoutInflater().inflate(R.layout.event_item, parent, false);
            }
            return new SearchViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            if(position < people.size()) {
                holder.bind(people.get(position));
            } else {
                holder.bind(events.get(position - people.size()));
            }
        }

        @Override
        public int getItemCount() {
            if(people == null || events == null){
                return 0;
            }
            else{
                return people.size() + events.size();
            }
        }
    }
    private class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name;
        private TextView relationship;
        private TextView eventInformation;
        private ImageView icon;


        private final int viewType;
        private Event event;
        private Person person;

        SearchViewHolder(View view, int viewType) {
            super(view);
            this.viewType = viewType;
            itemView.setOnClickListener(this);

            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                name = itemView.findViewById(R.id.name);
                icon = itemView.findViewById(R.id.icon);
                relationship = itemView.findViewById(R.id.relationship);
            }
            else {
                eventInformation = itemView.findViewById(R.id.eventInformation);
                name = itemView.findViewById(R.id.nameOfUser);
            }
        }

        private void bind(Person person) {
            if(person != null){
                this.person = person;
                name.setText(person.getFirstName() + " " + person.getLastName());
                relationship.setText("");
                if(person.getGender() == 'm'){
                    icon.setImageResource(R.drawable.ic_man_24dp);
                }
                else{
                    icon.setImageResource(R.drawable.ic_woman_24dp);
                }
            }
        }
        private void bind(Event event) {
            if(event != null){
                this.event = event;
                Person person = myDataCache.getAllPeople().get(event.getPersonID());
                name.setText(person.getFirstName() + " " + person.getLastName());
                eventInformation.setText(event.getEventType().toUpperCase() + ": " + event.getCity() + ", "
                        + event.getCountry() + " (" + event.getYear() + ")");
            }
        }

        @Override
        public void onClick(View view) {
            if(viewType == PERSON_ITEM_VIEW_TYPE) {
                Intent newPersonIntent = new Intent(SearchActivity.this, PersonActivity.class);
                newPersonIntent.putExtra("personID", person.getPersonID());
                startActivity(newPersonIntent);
            } else {
                Intent newEventIntent = new Intent(SearchActivity.this, EventActivity.class);
                newEventIntent.putExtra("personID", event.getPersonID());
                newEventIntent.putExtra("eventID", event.getEventID());
                startActivity(newEventIntent);
            }
        }
    }
    public ArrayList<Event> getEvents(){
        ArrayList<Event> events = new ArrayList<>();
        boolean filterControl = myDataCache.isMaleLine() || myDataCache.isFemaleLine() || myDataCache.isFatherLine() || myDataCache.isMotherLine();
        if(myDataCache.isMaleLine()){
            events.addAll(myDataCache.getEventsMale());
        }
        if(myDataCache.isFemaleLine()){
            events.addAll(myDataCache.getEventsFemale());
        }
        if(myDataCache.isFatherLine()){
            events.addAll(myDataCache.getEventFatherSide());
        }
        if(myDataCache.isMotherLine()){
            events.addAll(myDataCache.getEventMotherSide());
        }
        if(!filterControl){
            for(Map.Entry<String, HashSet<Event>> entry: myDataCache.getAllEvents().entrySet()){
                events.addAll(entry.getValue());
            }
        }
        return events;
    }
    public ArrayList<Person> getPeople(){
        ArrayList<Person> people = new ArrayList();
        for(Map.Entry<String, Person> personEntry: myDataCache.getAllPeople().entrySet()){
            people.add(personEntry.getValue());
        }
       return people;
    }
    public ArrayList<Event> updateEventsList(String textSearched){
        ArrayList<Event> newUpdatedList = new ArrayList<>();
        for(int i = 0; i < this.events.size(); i++){
            if(events.get(i).getCity().toLowerCase().contains(textSearched) || events.get(i).getCountry().toLowerCase().contains(textSearched)){
                newUpdatedList.add(events.get(i));
            }
        }
        return newUpdatedList;
    }
    public ArrayList<Person> updatePeopleList(String textSearched){
        textSearched = textSearched.toLowerCase();
        ArrayList<Person> newUpdatedList = new ArrayList<>();
        for(int i = 0; i < this.people.size(); i++){
            if(people.get(i).getFirstName().toLowerCase().contains(textSearched) || people.get(i).getLastName().toLowerCase().contains(textSearched)){
                newUpdatedList.add(people.get(i));
            }
        }
        return newUpdatedList;
    }
}
