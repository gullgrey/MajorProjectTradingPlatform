package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class NewTransaction {

    private final TradingPlatformDataSource dataSource;

    public NewTransaction(TradingPlatformDataSource dataSource) {
        this.dataSource = dataSource;
    }

    //TODO Might need helper method for similar code in buy order and sell order.

    /**
     *
     * @param buyOrder
     * @return int - The number of assets that were immediately bought, OR
     *      PlatformGlobals.getGeneralSQLFail() if buyOrder did not go through.
     */
    public int addBuyOrder(TPOrder buyOrder) {
        int organisationCredits = dataSource.getCredits(buyOrder.getOrganisation());
        if (buyOrder.getCredits() > organisationCredits) {
            return PlatformGlobals.getGeneralSQLFail();
        }

        Set<TPOrder> sellOrders = dataSource.getOrders(false);
        Set<TPOrder> transactionOrders = new TreeSet<>();
        for (TPOrder sellOrder : sellOrders) {
            if(sellOrder.getAsset().equals(buyOrder.getAsset())
                    && sellOrder.getCredits() <= buyOrder.getCredits()) {
                transactionOrders.add(sellOrder);
            }
        }
        int amountUsed = buyOrder.getAmount();
        for (Iterator<TPOrder> iterator = transactionOrders.iterator(); iterator.hasNext();) {
            TPOrder sellOrder = iterator.next();
            amountUsed -= sellOrder.getAmount();
            if (amountUsed <= 0) break;
            dataSource.deleteOrder(sellOrder.getId());

        }
        if (amountUsed >= 0) {

        }

        //return number of assets bought
        return dataSource.addOrder(buyOrder.getOrganisation(), buyOrder.getAsset(), buyOrder.getAmount(),
                buyOrder.getCredits(), true);
    }

    /**
     *
     * @param order
     * @return int - The number of assets that were immediately sold, OR
     *      PlatformGlobals.getGeneralSQLFail() if order did not go through.
     */
    public int addSellOrder(TPOrder order) {
        //return number of assets sold
        return dataSource.addOrder(order.getOrganisation(), order.getAsset(), order.getAmount(),
                order.getCredits(), false);
    }

    private TPOrder automaticTransaction(TPOrder order) {
        //Could do message at GUI level that states what transaction took place.
        return null;
    }

    /**
     *
     * @param id
     * @return
     */
    public int removeOrder(int id) {
       TPOrder order = dataSource.getOrder(id);
       System.out.println(order);
       if (order == null) {
           return PlatformGlobals.getNoRowsAffected();
       }
       String organisation = order.getOrganisation();
       String asset = order.getAsset();
       int amount = order.getAmount();
       int credits = order.getCredits();
       boolean isBuyOrder;
       isBuyOrder = order.getType().equals(PlatformGlobals.getBuyOrder());
       int rowsAffected = dataSource.deleteOrder(id);
       if (isBuyOrder) {
           dataSource.updateCredits(organisation, credits * amount);
       } else { //is Sell Order
           if (dataSource.getAssetAmount(organisation, asset) < PlatformGlobals.getNoRowsAffected()) {
               dataSource.addAsset(organisation, asset, amount);
           } else {
                dataSource.updateAssetAmount(organisation, asset, amount);
           }
       }

       return rowsAffected;
    }
}
