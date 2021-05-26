package main.java.tradingPlatform;

public class Asset implements Comparable<Asset>{

    String organisation;
    String asset;
    int amount;

    public Asset() {}

    public Asset(String organisation, String asset, int amount) {
        this.organisation = organisation;
        this.asset = asset;
        this.amount = amount;
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

    @Override
    public int compareTo(Asset asset) {
        return this.getAsset().compareTo(asset.getAsset());
    }
}
