package Result;

public class Login extends Response {
    private String authToken;
    private String personID;
    private String userName;
    /**
     * Default Constructor
     */
    public Login(){}

    //SETTERS

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    //GETTERS

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }

    public String getAuthToken() {
        return authToken;
    }
}
