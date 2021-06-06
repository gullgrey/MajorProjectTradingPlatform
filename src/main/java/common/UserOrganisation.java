package main.java.common;

import java.io.Serializable;

/**
 * This class is responsible for handling all the variables associated with
 * the organisation and user relationship. It holds the variables of the object and
 * the navigation.
 */
public class UserOrganisation implements Comparable<UserOrganisation>, Serializable {
    private String user;
    private String organisation;

    public UserOrganisation(){}

    public UserOrganisation(String user, String organisation) {
        this.user = user;
        this.organisation = organisation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    @Override
    public int compareTo(UserOrganisation user) {
        return this.getUser().compareTo(user.getUser());
    }
}
