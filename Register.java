package Result;

public class Register extends Response {
    private String authToken;
    private String personID;
    private String userName;

    /**
     * Default Constructor
     */
    public Register(){}

    //SETTERS
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //GETTERS
    public String getAuthToken() {
        return authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public String getUserName() {
        return userName;
    }
}
