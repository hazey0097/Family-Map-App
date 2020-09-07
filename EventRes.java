package Result;

import Domain.Event;

public class EventRes extends Response {
    private Event[] data;
    /**
     * Default Constructor
     */
    public EventRes(){}
    //SETTERS

    public void setData(Event[] data) {
        this.data = data;
    }

    //GETTERS

    public Event[] getData() {
        return data;
    }
}
