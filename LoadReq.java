package Request;

import Domain.Event;
import Domain.Person;
import Domain.User;

public class LoadReq {
    private User[] users;
    private Person[] persons;
    private Event[] events;

    /**
     * Default constructor
     */

    public LoadReq(){}
    //SETTERS

    public void setEvents(Event[] events) {
        this.events = events;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }
    //GETTERS

    public Event[] getEvents() {
        return events;
    }

    public Person[] getPersons() {
        return persons;
    }

    public User[] getUsers() {
        return users;
    }
}
