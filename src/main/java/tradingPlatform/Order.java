package main.java.tradingPlatform;

/**
 * TODO
 */
public class Order {

    private int id;
    private String organisation;
    private String asset;
    private int amount;
    private int credits;
    private String dateTime;
    private String type;

    public Order(){}

    public Order(int id, String organisation,String asset, int amount,
                 int credits, String dateTime, String type) {

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
    public String getOrganisation() {
        return organisation;
    }

    /**
     *
     * @param organisation
     */
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
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

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }
}
