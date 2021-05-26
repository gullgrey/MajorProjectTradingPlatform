package main.java.tradingPlatform;

/**
 * TODO
 */
public class TPOrder implements Comparable<TPOrder>{

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
     * Get the ID value of a transaction.
     *
     * @return int ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method is used to get the organisation making the order.
     *
     * @return String Organisation
     */
    public String getOrganisation() {
        return organisation;
    }

    /**
     * Methods used to set the organisation making an order.
     *
     * @param organisation The current organisation making the order
     */
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    /**
     * Method used to get the Asset of an order
     *
     * @return name of Asset
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Method used to set the Asset of an order
     *
     * @param asset
     */
    public void setAsset(String asset) {
        this.asset = asset;
    }

    /**
     * Get the amount of a specific order
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return
     */
    public int getCredits() {
        return credits;
    }

    /**
     * @param credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * @return
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * @param dateTime
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * @return
     */
    public String getType() {
        return type;
    }

    @Override
    public int compareTo(TPOrder order) {
        return this.getId().compareTo(order.getId());
    }
}

