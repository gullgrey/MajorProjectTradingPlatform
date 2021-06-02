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

    public int removeOrder(TPOrder order) {
       return 0;
    }
}
