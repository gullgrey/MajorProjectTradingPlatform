package test.java.mockups;

public class UserMockup{

    private final String username;
    private String password;
    private String type;
    private String organisation;
    private String asset;
    private int assetAmount;
    private String isType;
    private int credits;


    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public int getAssetAmount() {
        return assetAmount;
    }

    public void setAssetAmount(int assetAmount) {
        this.assetAmount = assetAmount;
    }

    public String getIsType() {
        return isType;
    }

    public void setIsType(String isType) {
        this.isType = isType;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public String getOrganisation() {
        return organisation;
    }

    //This is for a user
    public UserMockup(String username, String password, String type, String organisation){
        this.username = username;
        this.password = password;
        this.type = type;
        this.organisation = organisation;
    }

    //This is for an order
    public UserMockup(String organisation, String asset, int assetAmount, int credits, String isType){
        this.username = organisation;
        this.password = asset;
        this.assetAmount = assetAmount;
        this.credits = credits;
        this.isType = isType;

    }


}