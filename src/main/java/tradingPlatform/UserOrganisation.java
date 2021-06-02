package main.java.tradingPlatform;

/**
 * todo
 */
public class UserOrganisation implements Comparable<UserOrganisation>{
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
