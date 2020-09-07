package Request;

public class LoginReq {
    private String userName;
    private String password;

    /**
     * Default Constructor
     */
    public LoginReq(){}

    //SETTERS
    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    //GETTERS
    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }
}
