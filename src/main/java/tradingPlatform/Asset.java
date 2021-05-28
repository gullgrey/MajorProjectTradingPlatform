package main.java.tradingPlatform;

/**
 * Retrieves and sets information associated with assets.
 */
public class Asset implements Comparable<Asset>{

    String organisation;
    String asset;
    int amount;

    public Asset() {}

    /**
     * The constructor sets the organisation name, asset and amount that is being created by the admin user.
     * @param organisation The organisation the asset belongs too.
     * @param asset The name of the asset.
     * @param amount The number of a certain asset.
     */
    public Asset(String organisation, String asset, int amount) {
        this.organisation = organisation;
        this.asset = asset;
        this.amount = amount;
    }

    /**
     * Retrieves the organisation name.
     *
     * @return the organisation
     */
    public String getOrganisation() {
        return organisation;
    }

    /**
     * Sets the organisation to the specified organisation.
     *
     * @param organisation The organisation to set.
     */
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    /**
     * @return the asset
     */
    public String getAsset() {
        return asset;
    }

    /**
     * Sets the asset name to the specified asset.
     *
     * @param asset the asset to set
     */
    public void setAsset(String asset) {
        this.asset = asset;
    }

    /**
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Compares the current asset with another asset to determine if they are the same.
     * @param asset the asset being compared
     * @return an integer value of 0 if they are the same, else false
     */
    @Override
    public int compareTo(Asset asset) {
        return this.getAsset().compareTo(asset.getAsset());
    }
}
