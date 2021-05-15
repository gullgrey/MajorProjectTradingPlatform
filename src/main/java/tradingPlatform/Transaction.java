package main.java.tradingPlatform;

/**
 * TODO
 */
public class Transaction {

    private int id;
    private String buyingOrganisation;
    private String sellingOrganisation;
    private String asset;
    private int amount;
    private int credits;
    private String dateTime;

    public Transaction(){}

    public Transaction(int id, String buyingOrganisation, String sellingOrganisation, String asset, int amount,
                 int credits, String dateTime) {

    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getBuyingOrganisation() {
        return buyingOrganisation;
    }

    /**
     *
     * @param buyingOrganisation
     */
    public void setBuyingOrganisation(String buyingOrganisation) {
        this.buyingOrganisation = buyingOrganisation;
    }

    /**
     *
     * @return
     */
    public String getSellingOrganisation() {
        return sellingOrganisation;
    }

    /**
     *
     * @param sellingOrganisation
     */
    public void setSellingOrganisation(String sellingOrganisation) {
        this.sellingOrganisation = sellingOrganisation;
    }

    /**
     *
     * @return
     */
    public String getAsset() {
        return asset;
    }

    /**
     *
     * @param asset
     */
    public void setAsset(String asset) {
        this.asset = asset;
    }

    /**
     *
     * @return
     */
    public int getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     *
     * @return
     */
    public int getCredits() {
        return credits;
    }

    /**
     *
     * @param credits
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     *
     * @return
     */
    public String getDateTime() {
        return dateTime;
    }

    /**
     *
     * @param dateTime
     */
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
