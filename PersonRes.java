package Result;

import Domain.Person;

public class PersonRes extends Response {
    private Domain.Person[] data;
    /**
     * Default Constructor
     */
    public PersonRes(){}
    //SETTERS

    public void setData(Person[] data) {
        this.data = data;
    }

    //GETTERS
    public Person[] getData() {
        return data;
    }
}
