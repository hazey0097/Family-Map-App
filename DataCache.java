package com.example.client.DataCache;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Random;

import Domain.Event;
import Domain.Person;

public class DataCache {
    private static DataCache instance;
    private String serverHost;
    private String serverPort;
    private String authToken;

    private HashMap<String, HashSet<Event>> allEvents;
    private HashMap<String, Person> allPeople;
    private HashSet<Event> eventFatherSide;
    private HashSet<Event> eventMotherSide;
    private HashSet<Person> peopleFatherSide;
    private HashSet<Person> peopleMotherSide;
    private HashSet<Event> eventsMale;
    private HashSet<Event> eventsFemale;

    private Person user;
    private Person father;
    private Person mother;

    private boolean isStoryLine;
    private boolean isFamilyTreeLine;
    private boolean isFatherLine;
    private boolean isMotherLine;
    private boolean isMaleLine;
    private boolean isFemaleLine;
    private boolean isSpouseLine;
    private boolean isLogout;

    public static DataCache getInstance() {
        if (instance == null) {
            instance = new DataCache();
        }
        return instance;
    }

    private DataCache() {
        allEvents = new HashMap<>();
        allPeople = new HashMap<>();
        eventFatherSide = new HashSet<>();
        eventMotherSide = new HashSet<>();
        peopleFatherSide = new HashSet<>();
        peopleMotherSide = new HashSet<>();
        eventsMale = new HashSet<>();
        eventsFemale = new HashSet<>();
    }

    public void addDirectFamily() {
        Person father = allPeople.get(user.getFatherID());
        Person mother = allPeople.get(user.getMotherID());
        this.father = father;
        this.mother = mother;
        eventFatherSide.addAll(allEvents.get(user.getFatherID()));
        eventMotherSide.addAll(allEvents.get(user.getMotherID()));
        peopleFatherSide.add(father);
        peopleMotherSide.add(mother);
        eventFatherSide.addAll(allEvents.get(user.getPersonID()));
        eventMotherSide.addAll(allEvents.get(user.getPersonID()));
        peopleFatherSide.add(user);
        peopleMotherSide.add(user);
    }

    public void filterFatherSide(Person child) {
        //Child in the first call is the father of the user
        Person father = allPeople.get(child.getFatherID());
        Person mother = allPeople.get(child.getMotherID());
        while (father != null && mother != null) {
            eventFatherSide.addAll(allEvents.get(father.getPersonID()));
            eventFatherSide.addAll(allEvents.get(mother.getPersonID()));
            peopleFatherSide.add(father);
            peopleFatherSide.add(mother);
            filterFatherSide(father); // recursive
            filterFatherSide(mother);
            break; // recursive
        }
        return;
    }
    public void filterMotherSide(Person child) {
        Person father = allPeople.get(child.getFatherID());
        Person mother = allPeople.get(child.getMotherID());
        while (father != null && mother != null) {
            eventMotherSide.addAll(allEvents.get(father.getPersonID()));
            eventMotherSide.addAll(allEvents.get(mother.getPersonID()));
            peopleMotherSide.add(father);
            peopleMotherSide.add(mother);
            filterMotherSide(father); // recursive
            filterMotherSide(mother);
            break; // recursive
        }
        return;
    }
    public void filterGender() {
        for (Map.Entry<String, Person> person : allPeople.entrySet()) {
            if (person.getValue().getGender() == 'm') {
                eventsMale.addAll(allEvents.get(person.getKey()));
            } else {
                eventsFemale.addAll(allEvents.get(person.getKey()));
            }
        }
        return;
    }
    public String findRelationship(Person person, Person personSelected){
        if(personSelected.getSpouseID() != null &&
                person.getSpouseID().equals(personSelected.getPersonID())){
            return "Spouse";
        }
        else if((person.getMotherID() != null && person.getMotherID().equals(personSelected.getPersonID())) ||
                (person.getFatherID() != null && person.getFatherID().equals(personSelected.getPersonID()))){
            return "Child";
        }
        else if(personSelected.getMotherID() != null &&
                personSelected.getMotherID().equals(person.getPersonID())){
            return "Mother";
        }
        else if(personSelected.getFatherID() != null &&
                personSelected.getFatherID().equals(person.getPersonID())){
            return "Father";
        }
        return null;
    }
    public ArrayList<Event> orderedEvents(HashSet<Integer> yearsOfEvents, HashSet<Event> eventsOfUser){
        ArrayList<Integer> sortedYears = new ArrayList<>();
        ArrayList<Event> eventsForPerson= new ArrayList<>();
        sortedYears.addAll(yearsOfEvents);
        Collections.sort(sortedYears);
        for(int i = 0; i < sortedYears.size(); i++){
            for(Event temp: eventsOfUser){
                if(temp.getYear() == sortedYears.get(i)){
                    eventsForPerson.add(temp);
                }
            }
        }
        return eventsForPerson;
    }

    // SETTER AND GETTERS
    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public HashMap<String, HashSet<Event>> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(HashMap<String, HashSet<Event>> allEvents) {
        this.allEvents = allEvents;
    }

    public HashMap<String, Person> getAllPeople() {
        return allPeople;
    }

    public void setAllPeople(HashMap<String, Person> allPeople) {
        this.allPeople = allPeople;
    }

    public HashSet<Event> getEventFatherSide() {
        return eventFatherSide;
    }

    public void setEventFatherSide(HashSet<Event> eventFatherSide) {
        this.eventFatherSide = eventFatherSide;
    }

    public HashSet<Event> getEventMotherSide() {
        return eventMotherSide;
    }

    public void setEventMotherSide(HashSet<Event> eventMotherSide) {
        this.eventMotherSide = eventMotherSide;
    }

    public HashSet<Person> getPeopleFatherSide() {
        return peopleFatherSide;
    }

    public void setPeopleFatherSide(HashSet<Person> peopleFatherSide) {
        this.peopleFatherSide = peopleFatherSide;
    }

    public HashSet<Person> getPeopleMotherSide() {
        return peopleMotherSide;
    }

    public void setPeopleMotherSide(HashSet<Person> peopleMotherSide) {
        this.peopleMotherSide = peopleMotherSide;
    }

    public HashSet<Event> getEventsMale() {
        return eventsMale;
    }

    public void setEventsMale(HashSet<Event> eventsMale) {
        this.eventsMale = eventsMale;
    }

    public HashSet<Event> getEventsFemale() {
        return eventsFemale;
    }

    public void setEventsFemale(HashSet<Event> eventsFemale) {
        this.eventsFemale = eventsFemale;
    }

    public Person getUser() {
        return user;
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public Person getFather() {
        return father;
    }

    public Person getMother() {
        return mother;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public boolean isStoryLine() {
        return isStoryLine;
    }

    public void setStoryLine(boolean storyLine) {
        isStoryLine = storyLine;
    }

    public boolean isFamilyTreeLine() {
        return isFamilyTreeLine;
    }

    public void setFamilyTreeLine(boolean familyTreeLine) {
        isFamilyTreeLine = familyTreeLine;
    }

    public boolean isFatherLine() {
        return isFatherLine;
    }

    public void setFatherLine(boolean fatherLine) {
        isFatherLine = fatherLine;
    }

    public boolean isMotherLine() {
        return isMotherLine;
    }

    public void setMotherLine(boolean motherLine) {
        isMotherLine = motherLine;
    }

    public boolean isMaleLine() {
        return isMaleLine;
    }

    public void setMaleLine(boolean maleLine) {
        isMaleLine = maleLine;
    }

    public boolean isFemaleLine() {
        return isFemaleLine;
    }

    public void setFemaleLine(boolean femaleLine) {
        isFemaleLine = femaleLine;
    }

    public boolean isSpouseLine() {
        return isSpouseLine;
    }

    public void setSpouseLine(boolean spouseLine) {
        isSpouseLine = spouseLine;
    }

    public boolean isLogout() {
        return isLogout;
    }

    public void setLogout(boolean logout) {
        isLogout = logout;
    }
}