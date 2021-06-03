package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.abs;

/**
 * todo
 */
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
        int transactionCredits = buyOrder.getCredits() * buyOrder.getAmount();
        if (transactionCredits > organisationCredits) {
            return PlatformGlobals.getGeneralSQLFail();
        }
        dataSource.updateCredits(buyOrder.getOrganisation(), -transactionCredits);

        Set<TPOrder> sellOrders = dataSource.getOrders(false);
        Set<TPOrder> transactionOrders = new TreeSet<>();
        for (TPOrder sellOrder : sellOrders) {
            if(sellOrder.getAsset().equals(buyOrder.getAsset())
                    && sellOrder.getCredits() <= buyOrder.getCredits()) {
                transactionOrders.add(sellOrder);
            }
        }
        int amountUsed = buyOrder.getAmount();
        for (TPOrder sellOrder : transactionOrders) {
            amountUsed -= sellOrder.getAmount();
            if (amountUsed < 0) {

                // Relist a sell order that is partially completed by a buy order.
                sellOrder.setAmount(sellOrder.getAmount() + amountUsed);
                dataSource.addOrder(sellOrder.getOrganisation(), sellOrder.getAsset(),
                        abs(amountUsed), sellOrder.getCredits(), false);
                amountUsed = 0;
            }
            // Remove the sell order from the market.
            dataSource.deleteOrder(sellOrder.getId());

            automaticTransaction(buyOrder, sellOrder);

            if (amountUsed == 0) break;
        }
        if (amountUsed > 0) {
            dataSource.addOrder(buyOrder.getOrganisation(), buyOrder.getAsset(), amountUsed,
                    buyOrder.getCredits(), true);
        }

        //return number of assets bought
        return buyOrder.getAmount() - amountUsed;
    }

    /**
     *
     * @param sellOrder
     * @return int - The number of assets that were immediately sold, OR
     *      PlatformGlobals.getGeneralSQLFail() if sellOrder did not go through.
     */
    public int addSellOrder(TPOrder sellOrder) {
        int organisationAssets = dataSource.getAssetAmount(sellOrder.getOrganisation(), sellOrder.getAsset());
        if (sellOrder.getAmount() > organisationAssets) {
            return PlatformGlobals.getGeneralSQLFail();
        }
        dataSource.updateAssetAmount(sellOrder.getOrganisation(),
                sellOrder.getAsset(), sellOrder.getAmount());

        Set<TPOrder> buyOrders = dataSource.getOrders(true);
        Set<TPOrder> transactionOrders = new TreeSet<>();
        for (TPOrder buyOrder : buyOrders) {
            if(sellOrder.getAsset().equals(buyOrder.getAsset())
                    && sellOrder.getCredits() <= buyOrder.getCredits()) {
                transactionOrders.add(buyOrder);
            }
        }

        int amountUsed = sellOrder.getAmount();
        for (TPOrder buyOrder : transactionOrders) {
            amountUsed -= buyOrder.getAmount();
            if (amountUsed < 0) {

                // Relist a buy order that is partially completed by a sell order.
                buyOrder.setAmount(buyOrder.getAmount() + amountUsed);
                dataSource.addOrder(buyOrder.getOrganisation(), buyOrder.getAsset(),
                        abs(amountUsed), buyOrder.getCredits(), true);
                amountUsed = 0;
            }
            // Remove the buy order from the market.
            dataSource.deleteOrder(buyOrder.getId());

            automaticTransaction(buyOrder, sellOrder);

            if (amountUsed == 0) break;
        }

        if (amountUsed > 0) {
            dataSource.addOrder(sellOrder.getOrganisation(), sellOrder.getAsset(), amountUsed,
                    sellOrder.getCredits(), false);
        }

        //return number of assets sold
        return sellOrder.getAmount() - amountUsed;
    }

    /**
     *
     * @param buyOrder
     * @param sellOrder
     */
    private void automaticTransaction(TPOrder buyOrder, TPOrder sellOrder) {
        // Adds the asset to the buy organisation if it doesn't exist.
        dataSource.addAsset(buyOrder.getOrganisation(), buyOrder.getAsset(), 0);

        // Adds the sold assets to the buying organisation.
        dataSource.updateAssetAmount(buyOrder.getOrganisation(), buyOrder.getAsset(),
                sellOrder.getAmount());

        // Update the selling organisation's credits.
        int sellProfit = sellOrder.getCredits() * sellOrder.getAmount();
        dataSource.updateCredits(sellOrder.getOrganisation(), sellProfit);

        // Add the completed transaction to the history.
        dataSource.addTransaction(buyOrder.getOrganisation(), sellOrder.getOrganisation(),
                sellOrder.getAsset(), sellOrder.getAmount(), sellOrder.getCredits());

        //If the buy order was listed higher then the sell order, refund the buying
        //organisation by the difference.
        int creditDifference = buyOrder.getCredits() - sellOrder.getCredits();
        int creditRefund = creditDifference * sellOrder.getAmount();
        dataSource.updateCredits(buyOrder.getOrganisation(), creditRefund);
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
