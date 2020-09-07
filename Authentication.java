package Domain;

import java.util.Objects;

/** Authentication Class for each person in the server
 * @author hazarhandal */

public class Authentication {
    private String userName;
    private String password;
    private String authenticationToken;

    //CONSTRUCTORS
    /**
     * Default constructor
     */
    public Authentication(){}
    /** Constructor - parameterized with all the required variables for the authentication class
     * @param userName username of the user
     * @param password password of the user
     * @param authToken unique auth token for the log in */
    public Authentication(String userName, String password, String authToken){
        this.password = password;
        this.userName = userName;
        this.authenticationToken = authToken;
    }

    //SETTERS
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAuthenticationToken(String authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    //GETTERS
    public String getPassword(){
        return password;
    }
    public String getAuthenticationToken() {
        return authenticationToken;
    }
    public String getUserName(){
        return userName;
    }

    /**
     * Return method that returns all the information of the currently logged in user
     * @return userInfo - information of the user
     */
    public String toString(){
        return null;
    }

    /**
     * Return method that return if the authentication is valid by matching the password and username together, if they match then return true otherwise false
     * @param o, the current user logged in
     * @return isValid, false or true
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authentication that = (Authentication) o;
        return Objects.equals(userName, that.userName) &&
                Objects.equals(password, that.password) &&
                Objects.equals(authenticationToken, that.authenticationToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, authenticationToken);
    }


}
