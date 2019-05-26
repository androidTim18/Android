package android.projekat;

import java.util.Random;

class User {
    public String userId;
    public Double rating;
    public String rated;
    public String userName;
    public String firstName;
    public String lastName;
    public String email;
    public String fullName;

    public User(String userId, Double rating, String rated, String firstName, String lastName, String userName, String email) {
        this.userId = userId;
        this.rating = rating;
        this.rated = rated;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fullName = firstName + " " + lastName;
    }
    public User(String firstName, String lastName, String userName, String email) {

        this.rating = 0.0;
        this.rated = "false";
        this.userName = userName;

        Random r = new Random();
        Integer a = (r.nextInt((9999-1000)+1)+1000);

        this.userId = a.toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fullName = firstName + " " + lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

