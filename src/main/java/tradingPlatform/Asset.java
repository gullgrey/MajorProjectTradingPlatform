package main.java.tradingPlatform;

public class Asset {

    String asset;
    String amount;

    public Asset() {}

    public Asset(String asset, String amount) {
        this.asset = asset;
        this.amount = amount;
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
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }
}
