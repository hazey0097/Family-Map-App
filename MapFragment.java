package com.example.client.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.client.DataCache.DataCache;
import com.example.client.R;
import com.example.client.activities.EventActivity;
import com.example.client.activities.PersonActivity;
import com.example.client.activities.SearchActivity;
import com.example.client.activities.SettingsActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

import Domain.Event;
import Domain.Person;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap map;
    private DataCache myDataCache = DataCache.getInstance();
    private EditText eventInformation;
    private ImageView icon;
    private Intent settingsIntent;
    private Intent searchIntent;
    private Event selectedEventFromEvenActivity;
    private final float[] colours = new float[]{BitmapDescriptorFactory.HUE_AZURE, BitmapDescriptorFactory.HUE_BLUE, BitmapDescriptorFactory.HUE_CYAN,
            BitmapDescriptorFactory.HUE_GREEN, BitmapDescriptorFactory.HUE_MAGENTA, BitmapDescriptorFactory.HUE_ROSE, BitmapDescriptorFactory.HUE_VIOLET,
            BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_YELLOW, BitmapDescriptorFactory.HUE_ORANGE};
    private HashMap<String, Float> colorsUsed = new HashMap<>();
    private final Random r = new Random();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getActivity().getClass().equals(EventActivity.class)){
            setHasOptionsMenu(false);
        }
        else{
            setHasOptionsMenu(true);
        }
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("MAPPING");
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.activity_map, container, false);
        eventInformation = (EditText) view.findViewById(R.id.infoMarker);
        icon = (ImageView) view.findViewById(R.id.icon);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        settingsIntent =  new Intent(getActivity(), SettingsActivity.class);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.setOnMarkerClickListener(this);
        this.CreateMarkers(myDataCache.getAllEvents(), null);
        setUpSelectedEvent();
    }
    @Override
    public  void onResume() {
        super.onResume();
        if (map != null){
            map.clear();
            this.eventInformation.setText("Click on marker to see event details");
            icon.setImageResource(R.drawable.ic_android_black_24dp);
            boolean switchMarkerControl = myDataCache.isFatherLine() || myDataCache.isMotherLine() || myDataCache.isMaleLine() || myDataCache.isFemaleLine();
            if(myDataCache.isFatherLine()){
                CreateMarkers(null, myDataCache.getEventFatherSide());
            }
            if(myDataCache.isMotherLine()){
                CreateMarkers(null, myDataCache.getEventMotherSide());
            }
            if(myDataCache.isMaleLine()){
                CreateMarkers(null, myDataCache.getEventsMale());
            }
            if(myDataCache.isFemaleLine()){
                CreateMarkers(null, myDataCache.getEventsFemale());
            }
            if(!switchMarkerControl){
                CreateMarkers(myDataCache.getAllEvents(), null);
            }
        }
    }
    public void CreateMarkers(HashMap<String, HashSet<Event>> eventMap, HashSet<Event> eventSet){
        if(eventSet != null){
            for(Event temp: eventSet){
                if(temp.getEventType().equals("birth") || temp.getEventType().equals("Birth")){
                    LatLng currentEvent = new LatLng(temp.getLatitude(), temp.getLongitude());
                    Marker tempMarker = map.addMarker(new MarkerOptions().position(currentEvent).title("Marker in " + temp.getCity() + ", " + temp.getCountry()).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                    tempMarker.setTag(temp);
                }
                else if(temp.getEventType().equals("Death") || temp.getEventType().equals("death")  ){
                    LatLng currentEvent = new LatLng(temp.getLatitude(), temp.getLongitude());
                    Marker tempMarker =map.addMarker(new MarkerOptions().position(currentEvent).title("Marker in " + temp.getCity() + ", " + temp.getCountry()).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                    tempMarker.setTag(temp);

                }
                else if(temp.getEventType().equals("Marriage") || temp.getEventType().equals("marriage")){
                    LatLng currentEvent = new LatLng(temp.getLatitude(), temp.getLongitude());
                    Marker tempMarker = map.addMarker(new MarkerOptions().position(currentEvent).title("Marker in " + temp.getCity() + ", " + temp.getCountry()).
                            icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                    tempMarker.setTag(temp);
                }
                else{
                    LatLng currentEvent = new LatLng(temp.getLatitude(), temp.getLongitude());
                    Marker tempMarker = map.addMarker(new MarkerOptions().position(currentEvent).title("Marker in " + temp.getCity() + ", " + temp.getCountry()).
                            icon(BitmapDescriptorFactory.defaultMarker(getUniqueColor(temp.getEventType()))));
                    tempMarker.setTag(temp);
                }
            }
        }
        if(eventMap != null){
            for(Map.Entry<String, HashSet<Event>> event: eventMap.entrySet()){
                for(Event temp: event.getValue()){
                    if(temp.getEventType().equals("birth") || temp.getEventType().equals("Birth")){
                        LatLng currentEvent = new LatLng(temp.getLatitude(), temp.getLongitude());
                        Marker tempMarker =map.addMarker(new MarkerOptions().position(currentEvent).title("Marker in " + temp.getCity() + ", " + temp.getCountry()).
                                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        tempMarker.setTag(temp);
                    }
                    else if(temp.getEventType().equals("Death") || temp.getEventType().equals("death")  ){
                        LatLng currentEvent = new LatLng(temp.getLatitude(), temp.getLongitude());
                        Marker tempMarker =map.addMarker(new MarkerOptions().position(currentEvent).title("Marker in " + temp.getCity() + ", " + temp.getCountry()).
                                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        tempMarker.setTag(temp);

                    }
                    else if(temp.getEventType().equals("Marriage") || temp.getEventType().equals("marriage")){
                        LatLng currentEvent = new LatLng(temp.getLatitude(), temp.getLongitude());
                        Marker tempMarker = map.addMarker(new MarkerOptions().position(currentEvent).title("Marker in " + temp.getCity() + ", " + temp.getCountry()).
                                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
                        tempMarker.setTag(temp);
                    }
                    else{
                        LatLng currentEvent = new LatLng(temp.getLatitude(), temp.getLongitude());
                        Marker tempMarker = map.addMarker(new MarkerOptions().position(currentEvent).title("Marker in " + temp.getCity() + ", " + temp.getCountry()).
                                icon(BitmapDescriptorFactory.defaultMarker(getUniqueColor(temp.getEventType()))));
                        tempMarker.setTag(temp);
                    }
                }
            }
        }

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_xml, menu);


        searchIntent = new Intent(getActivity(), SearchActivity.class);
        settingsIntent = new Intent(getActivity(), SettingsActivity.class);
        MenuItem settingsMenuItem = menu.findItem(R.id.settingsMenuItem);
        MenuItem searchMenuItem = menu.findItem(R.id.searchMenuItem);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch(menu.getItemId()) {
            case R.id.settingsMenuItem:
                startActivity(settingsIntent);
                return true;
            case R.id.searchMenuItem:
                startActivity(searchIntent);
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }
    public float getUniqueColor(String eventType){
        if(colorsUsed.containsKey(eventType)){
            return colorsUsed.get(eventType);
        }
        else{
            int temp;
            boolean colorIsUsed;
            do{
                colorIsUsed = false;
                temp = r.nextInt(colours.length);
                for(Map.Entry<String, Float> color: colorsUsed.entrySet()){
                    if(color.getValue().equals(colours[temp])){
                        System.out.println("Here");
                        colorIsUsed = true;
                        break;
                    }
                }
            }while(colorIsUsed);
            colorsUsed.put(eventType , colours[temp]);
            return colours[temp];
        }
    }
    public void onMarkerClicked(final Event currentEvent){
        if(map != null){
            map.clear();
        }
        onResume();
        LatLng cameraMarker = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());
        this.map.animateCamera(CameraUpdateFactory.newLatLng(cameraMarker));
        Person currentPerson = myDataCache.getAllPeople().get(currentEvent.getPersonID());
        String eventInformation = currentPerson.getFirstName() + " " + currentPerson.getLastName() + "\n" +
                currentEvent.getEventType().toUpperCase() + ": " + currentEvent.getCity() + ", " + currentEvent.getCountry() +
                " \n(" + currentEvent.getYear() + ")";
        this.eventInformation.setText(eventInformation);
        if(currentPerson.getGender() == 'm'){
            icon.setImageResource(R.drawable.ic_man_24dp);
        }
        else{
            icon.setImageResource(R.drawable.ic_woman_24dp);
        }
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent personActivity = new Intent(getActivity(), PersonActivity.class);
                personActivity.putExtra("personID", currentEvent.getPersonID());
                startActivity(personActivity);
            }
        });
        if(myDataCache.isSpouseLine() && (!myDataCache.isMaleLine() && !myDataCache.isFemaleLine())){
            drawSpouseLine(currentEvent);
        }
        if(myDataCache.isFamilyTreeLine()){
            drawFamilyTreeLines(currentEvent, 10);
        }
        if(myDataCache.isStoryLine()){
            drawStoryLine(currentEvent);
        }
    }

    public void drawSpouseLine(Event eventClicked){
        Person personClicked = myDataCache.getAllPeople().get(eventClicked.getPersonID());
        Person spouse = myDataCache.getAllPeople().get(personClicked.getSpouseID());
        if(spouse != null){
            int earliestYear = 1000000;
            Event earliestEventForSpouse =  new Event();
            for(Event curr: myDataCache.getAllEvents().get(spouse.getPersonID())){
                if(curr.getYear() < earliestYear){
                    earliestYear = curr.getYear();
                    earliestEventForSpouse = curr;
                }
            }
            LatLng spouseLine = new LatLng(earliestEventForSpouse.getLatitude(), earliestEventForSpouse.getLongitude());
            LatLng personLine = new LatLng(eventClicked.getLatitude(), eventClicked.getLongitude());
            Polyline tempPolyLine =  map.addPolyline(new PolylineOptions().add(personLine, spouseLine)
                    .width(5)
                    .color(Color.RED));
        }
        LatLng currentLine = new LatLng(eventClicked.getLatitude(), eventClicked.getLongitude());
    }
    void drawStoryLine(Event eventClicked){
        Person personClicked = myDataCache.getAllPeople().get(eventClicked.getPersonID());
        ArrayList<Event> allEventsOfUser = new ArrayList<>();
        ArrayList<Integer> orderedYears = new ArrayList<>();
        allEventsOfUser.addAll(myDataCache.getAllEvents().get(personClicked.getPersonID()));
        ArrayList<LatLng> lines =  new ArrayList<>();

        for(int i = 0; i < allEventsOfUser.size(); i++){
            orderedYears.add(allEventsOfUser.get(i).getYear());
        }
        Collections.sort(orderedYears);
        for(int i = 0; i < orderedYears.size(); i++){
            for(int j = 0; j < allEventsOfUser.size(); j++){
                if(allEventsOfUser.get(j).getYear() == orderedYears.get(i)){
                    LatLng currentLine = new LatLng(allEventsOfUser.get(j).getLatitude(), allEventsOfUser.get(j).getLongitude());
                    lines.add(currentLine);
                }
            }
        }
        for(int i = 0; i < lines.size() -1 ; i++){
            Polyline tempPolyLine =  map.addPolyline(new PolylineOptions().add(lines.get(i), lines.get(i+1))
                    .width(5)
                    .color(Color.WHITE));
        }
    }
    public void drawFamilyTreeLines(Event eventClicked, int width){
        Person user = myDataCache.getAllPeople().get(eventClicked.getPersonID());
        Person father = myDataCache.getAllPeople().get(user.getFatherID());
        Person mother = myDataCache.getAllPeople().get(user.getMotherID());

        if (father != null) {
            int earliestYear = 1000000;
            Event earliestEventForFather=  new Event();
            for(Event curr: myDataCache.getAllEvents().get(father.getPersonID())){
                if(curr.getYear() < earliestYear){
                    earliestYear = curr.getYear();
                    earliestEventForFather = curr;
                }
            }
            LatLng fatherLine = new LatLng(earliestEventForFather.getLatitude(), earliestEventForFather.getLongitude());
            LatLng personLine = new LatLng(eventClicked.getLatitude(), eventClicked.getLongitude());
            Polyline  tempPolyLine =  map.addPolyline(new PolylineOptions().add(personLine, fatherLine)
                    .width(width)
                    .color(Color.DKGRAY));
            drawFamilyTreeLines(earliestEventForFather, width - 3);
        }
        if (mother != null){
            int earliestYear = 1000000;
            Event earliestEventForMother =  new Event();
            for(Event curr: myDataCache.getAllEvents().get(mother.getPersonID())){
                if(curr.getYear() < earliestYear){
                    earliestYear = curr.getYear();
                    earliestEventForMother = curr;
                }
            }
            LatLng motherLine = new LatLng(earliestEventForMother.getLatitude(), earliestEventForMother.getLongitude());
            LatLng personLine = new LatLng(eventClicked.getLatitude(), eventClicked.getLongitude());
            Polyline tempPolyLine =  map.addPolyline(new PolylineOptions().add(personLine, motherLine)
                    .width(width)
                    .color(Color.DKGRAY));
            drawFamilyTreeLines(earliestEventForMother, width - 3);
        }
        return;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        onMarkerClicked((Event) marker.getTag());
        return true;
    }
    public void setUpSelectedEvent(){
        if(selectedEventFromEvenActivity != null){
            onMarkerClicked(selectedEventFromEvenActivity);
            selectedEventFromEvenActivity = null;
        }
    }

    public Event getSelectedEventFromEvenActivity() {
        return selectedEventFromEvenActivity;
    }

    public void setSelectedEventFromEvenActivity(Event selectedEventFromEvenActivity) {
        this.selectedEventFromEvenActivity = selectedEventFromEvenActivity;
    }
}
