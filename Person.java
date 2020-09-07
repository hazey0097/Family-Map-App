package Domain;

import java.util.Objects;

/** Person Class for each person in the server
 * @author hazarhandal */
public class Person {
    private String personID;
    private String associatedUsername;
    private String firstName;
    private String lastName;
    private char gender;
    private String fatherID = null;
    private String motherID = null;
    private String spouseID = null;

    //CONSTRUCTORS
    /**
     * Default constructor
     */
    public Person(){}
    /** Constructor - parameterized with all the required variables for the person class
     * @param userID   unique user ID for each person
     * @param userName username of the user
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param gender user - female or male*/
    public Person(String userID, String userName, String firstName, String lastName, char gender){
        this.personID = userID;
        this.associatedUsername = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    //SETTERS
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    //GETTERS
    public String getPersonID() {
        return personID;
    }

    public char getGender() {
        return gender;
    }

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() { return motherID; }

    public String getSpouseID() {
        return spouseID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }


    /** Return method that print all the information of the current user
     * @return personInfo - all the information of the current user
     */
    public String toString(){
        return null;
    }

    /**
     * Return method that checks if the object passed in matches a person requested by the user from the persons table
     * @param o, random object
     * @return isEquals, true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return gender == person.gender &&
                Objects.equals(personID, person.personID) &&
                Objects.equals(associatedUsername, person.associatedUsername) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName) &&
                Objects.equals(fatherID, person.fatherID) &&
                Objects.equals(motherID, person.motherID) &&
                Objects.equals(spouseID, person.spouseID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID);
    }
}
