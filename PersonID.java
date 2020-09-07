package Result;

public class PersonID extends Response {
    private String associatedUsername;
    private String personID;
    private String firstName;
    private String lastName;
    private Character gender;
    private String fatherID = null;
    private String motherID = null ;
    private String spouseID = null;

    /**
     * Default Constructor
     */
    public PersonID(){}
    //SETTERS

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    //GETTERS

    public String getPersonID() {
        return personID;
    }

    public char getGender() {
        return gender;
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

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }
}
