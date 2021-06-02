package main.java.tradingPlatform;

import java.io.Serializable;

/**
 * TODO
 */
public class TPOrder implements Comparable<TPOrder>, Serializable {

    private int id;
    private String organisation;
    private String asset;
    private int amount;
    private int credits;
    private String dateTime;
    private String type;

    public TPOrder() {
    }

    public TPOrder(int id, String organisation, String asset, int amount,
                   int credits, String dateTime, String type) {
        this.id = id;
        this.organisation = organisation;
        this.asset = asset;
        this.amount = amount;
        this.credits = credits;
        this.dateTime = dateTime;
        this.type = type;
    }

    /**
     * Gets the ID value of a transaction.
     * @return int ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the ID value of a transaction.
     * @param id The id number of the buy or sell order.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method is used to get the organisation making the order.
     * @return String Organisation.
     */
    public String getOrganisation() {
        return organisation;
    }

    /**
     * Methods used to set the organisation making an order.
     * @param organisation The current organisation making the order.
     */
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    /**
     * Method used to get the Asset of an order.
     * @return name of Asset.
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Method used to set the Asset of an order.
     * @param asset is the name of the asset being added.
     */
    public void setAsset(String asset) {
        this.asset = asset;
    }

    /**
     * Gets the amount of a specific order.
     * @return the integer value of the amount.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the amounts of an order.
     * @param amount Integer amount to be set.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Gets the credentials of the organisation making the order
     * @return Integer value of credits available.
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the amount of credits for an organisation.
     * @param credits amount of credits to be increased or decreased.
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Gets the date and time of the server hosting that database.
     * @return current date and time as a string.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets the data and time that an order was made.
     * @param dateTime date and time as a string representation.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    //TODO Check that this is okay to be set as a boolean     ########
    /**
     * Is used to advise whether or not this the order is a buy or sell.
     * @return boolean indicating True = Sell | False = Buy.
     */
    public String getType() {
        return type;
    }

    /**
     * Sets whether or not this the order is a buy or sell.
     * @param type String of either "BUY" or "SELL"
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Compares the ID supplied with the ID of another order in the database to see if they match
     * @param order 0 if they do, -1 the ID is before it and 1 the ID supplied is after it.
     * @return int value indicating if ID was found.
     */
    //TODO Does this need a java docs
    @Override
    public int compareTo(TPOrder order) {
        return this.getId().compareTo(order.getId());
    }
}

