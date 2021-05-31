package main.java.tradingPlatform;

/**
 * TODO
 */
public class Transaction implements Comparable<Transaction>{

    private int id;
    private String buyingOrganisation;
    private String sellingOrganisation;
    private String asset;
    private int amount;
    private int credits;
    private String dateTime;

    public Transaction() {
    }

    public Transaction(int id, String buyingOrganisation, String sellingOrganisation, String asset, int amount,
                       int credits, String dateTime) {

    }

    /**
     * Gets the ID of the order that is being bought/sold
     * @return integer ID for a translation
     */
    public Integer getId() {
        return id;
    }

    /**
     * Method is used to set the id of a transaction.
     * @param id The id number of the selected order.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Method is used to get the buying organisation of a transaction.
     * @return Returns the organisation that is purchasing and order.
     */
    public String getBuyingOrganisation() {
        return buyingOrganisation;
    }

    /**
     * Method is used to set the buying organisation of a transaction.
     * @param buyingOrganisation Current organisation of the Member logged in.
     */
    public void setBuyingOrganisation(String buyingOrganisation) {
        this.buyingOrganisation = buyingOrganisation;
    }

    /**
     * Method is used to get the selling organisation of a transaction.
     * @return the selling organisation of the asset
     */
    public String getSellingOrganisation() {
        return sellingOrganisation;
    }

    /**
     * Method is used to set the selling organisation of a transaction.
     * @param sellingOrganisation selling organisation of the asset
     */
    public void setSellingOrganisation(String sellingOrganisation) {
        this.sellingOrganisation = sellingOrganisation;
    }

    /**
     * Method is used to get the Asset name.
     * @return returns the asset of an organisation
     */
    public String getAsset() {

        return asset;
    }

    /**
     * Method is used to set the Asset name.
     * @param asset
     */
    public void setAsset(String asset) {

        this.asset = asset;
    }

    /**
     * Method is used to get the Amount of an Asset
     * @return Returns the current amount
     */
    public int getAmount() {

        return amount;
    }

    /**
     * Method is used to set the Amount of an Asset
     * @param amount new amount of the Asset.
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Method gets the current value the members is supplying for the translations.
     * @return Credit value of the transaction.
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Method is used to set credits to the current value provided by the member.
     * @param credits credit value the member is supplying for the Transaction.
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Gets the current date and time that the the transaction was made.
     * @return the date and time.
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     * Sets the current date and time that the the transaction was made.
     * @param dateTime the date and time.
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    /**
     *Compares the ID supplied with the ID of another order in the database to see if they match.
     * @param transaction 0 if they do, -1 the ID is before it and 1 the ID supplied is after it.
     * @return int value indicating if ID was found.
     */
    @Override
    public int compareTo(Transaction transaction) {
        return this.getId().compareTo(transaction.getId());
    }
}
