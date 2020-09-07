package Domain;

import java.util.Objects;

/** Event Class for each person in the server
 * @author hazarhandal */
public class Event {

    private String personID;
    private String associatedUsername;
    private String eventID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private int year;

    // CONSTRUCTORS
    /**
     * Default constructor
     */
    public Event(){}
    /** Constructor - parameterized with all the required variables for the event class
     * @param userID   unique user ID for each person
     * @param eventID  unique event ID
     * @param userName username of the user
     * @param latitudeOfEvent latitude of the location of the event
     * @param longitudeOfEvent longtitude if the location of the event
     * @param country country of the event
     * @param city city of event
     * @param typeOfEvent type of the event (marriage, death, birth, baptism ...)
     * @param yearOfEvent date of event in years */
    public Event(String eventID,String userID, String userName, Double longitudeOfEvent, Double latitudeOfEvent, String country, String city,
                 String typeOfEvent, int yearOfEvent){
        this.personID = userID;
        this.eventID = eventID;
        this.associatedUsername = userName;
        this.latitude = latitudeOfEvent;
        this.longitude = longitudeOfEvent;
        this.country = country;
        this.city = city;
        this.eventType = typeOfEvent;
        this.year = yearOfEvent;
    }
    //SETTERS
    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //GETTERS
    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() { return personID; }

    public String getEventID() {
        return eventID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public int getYear() {
        return year;
    }

    public String getCity() { return city; }

    public String getCountry() {
        return country;
    }

    public String getEventType() {
        return eventType;
    }

    /**
     * Return method that returns all of the event's information
     * @return eventInfo - information of event
     */
    public String toString(){
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Double.compare(event.latitude, latitude) == 0 &&
                Double.compare(event.longitude, longitude) == 0 &&
                year == event.year &&
                Objects.equals(personID, event.personID) &&
                Objects.equals(associatedUsername, event.associatedUsername) &&
                Objects.equals(eventID, event.eventID) &&
                Objects.equals(country, event.country) &&
                Objects.equals(city, event.city) &&
                Objects.equals(eventType, event.eventType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, eventID, latitude, longitude, country, city, eventType, year);
    }
}

