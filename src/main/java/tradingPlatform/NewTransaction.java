package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;

public class NewTransaction {

    private TradingPlatformDataSource dataSource;

    public NewTransaction(TradingPlatformDataSource dataSource) {
        this.dataSource = dataSource;
    }

    //TODO Might need helper method for similar code in buy order and sell order.

    public TPOrder addBuyOrder(TPOrder buyOrder) {
        return null;
    }

    public TPOrder addSellOrder(TPOrder sellOrder) {
        return null;
    }

    private TPOrder automaticTransaction(TPOrder order) {
        //Could do message at GUI level that states what transaction took place.
        return null;
    }

    public int removeOrder(int id) {
       TPOrder order = dataSource.getOrder(id);
       if (order == null) {
           return PlatformGlobals.getGeneralSQLFail();
       }
       String organisation = order.getOrganisation();
       String asset = order.getAsset();
       int amount = order.getAmount();
       int credits = order.getCredits();
       boolean isBuyOrder;
       isBuyOrder = order.getType().equals(PlatformGlobals.getBuyOrder());
       dataSource.deleteOrder(id);
       if (isBuyOrder) {
           dataSource.updateCredits(organisation, credits * amount);
       } else { //is Sell Order
           if (dataSource.getAssetAmount(organisation, asset) < PlatformGlobals.getNoRowsAffected()) {
               dataSource.addAsset(organisation, asset, amount);
           } else {
                dataSource.updateAssetAmount(organisation, asset, amount);
           }
       }

       return 0;
    }
}
