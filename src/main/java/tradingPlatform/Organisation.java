package main.java.tradingPlatform;

public class Organisation implements Comparable<Organisation>{

    private String organisation;
    private int credits;

    public Organisation(){}

    public Organisation(String organisation, int credits) {
        this.organisation = organisation;
        this.credits = credits;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public int compareTo(Organisation organisation) {
        return this.getOrganisation().compareTo(organisation.getOrganisation());
    }
}
