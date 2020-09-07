package Result;

public class EventID extends Response {
    private String associatedUsername;
    private String eventID;
    private String personID;
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private Integer year;

    /**
     * Default Constructor
     */
    public EventID(String message) {
        this.message = message;
        this.success =  false;
    }
    public EventID(){};
    //SETTERS


    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //GETTERS

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getYear() {
        return year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventType() {
        return eventType;
    }
}