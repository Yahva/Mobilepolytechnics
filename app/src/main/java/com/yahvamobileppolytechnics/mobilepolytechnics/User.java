package com.yahvamobileppolytechnics.mobilepolytechnics;

/**
 * Created by Yahva on 30.09.2017.
 */

public class User {

    private String firstName;
    private String secondName;

    public User() {
    }

    public User(String firstName, String secondName) {
        this.firstName = firstName;
        this.secondName = secondName;
    }

    public String getFirstName() {
        return this.firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSecondName() {
        return this.secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
