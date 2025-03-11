package com.flowershop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Model class that contains information on a registered user's profile
 */
public class UserProfile {

    private int profileId;

    @JsonIgnore
    private int userId;

    private String firstName;
    private String lastName;
    private String email;

    public UserProfile(int profileId, int userId, String firstName, String lastName, String email) {
        this.profileId = profileId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public int getProfileId() {
        return profileId;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
