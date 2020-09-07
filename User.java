package Domain;

import java.util.Objects;

/** User Class for each Person Entry
 * @author hazarhandal */

public class User {
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private char gender;

    /** Default constructor for the user class */
    public User(){}

    /** Constructor - parameterized constructor for all the required private members of a user object
     * @param userName username of the user
     * @param password password of the user
     * @param firstName first name of the user
     * @param lastName last name of the user
     * @param emailAddress email address of the user
     * @param gender user - female or male*/
    public User(String userName, String password,String emailAddress, String firstName, String lastName, char gender){
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = emailAddress;
        this.gender = gender;
    }

    //SETTERS
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setGender(char gender) {
        this.gender = gender;
    }

    //GETTERS
    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public String getEmail(){
        return email;
    }
    public String getUserFirstName(){
        return firstName;
    }
    public String getUserLastName(){
        return lastName;
    }
    public char getGender(){
        return gender;
    }

    /** Method that prints all the information of the current user
     * @return userInfo - all the information of the user
     */
    public String toString(){return null;}
    /**
     * Return method that compares the object to the the person object, return false if are not equal (ex: not same class type, or different user ID), and true otherwise
     * @param o, random object
     * @return isEquals, false or true
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return gender == user.gender &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userName, password, email, firstName, lastName, gender);
    }
}
