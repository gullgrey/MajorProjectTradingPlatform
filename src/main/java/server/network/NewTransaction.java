package main.java.server.network;

import main.java.server.database.TradingPlatformDataSource;
import main.java.common.PlatformGlobals;
import main.java.common.TPOrder;

import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.abs;

/**
 * This class is responsible for handling all the buy and sell orders which are
 * performed on the system. These include buy, sell and removing trade orders.
 */
public class NewTransaction {

    private final TradingPlatformDataSource dataSource;

    public NewTransaction(TradingPlatformDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * This method is responsible for adding a buy order transaction to the market
     * place and database.
     *
     * @param buyOrder the order being processed.
     * @return int - The number of assets that were placed up to buy, OR
     * PlatformGlobals.getGeneralSQLFail() if sellOrder did not go through.
     */
    public int addBuyOrder(TPOrder buyOrder) {

        // If the buying organisation doesn't have enough credits, this stops the order
        // being added to the database.
        int organisationCredits = dataSource.getCredits(buyOrder.getOrganisation());
        int transactionCredits = buyOrder.getCredits() * buyOrder.getAmount();
        if (transactionCredits > organisationCredits) {
            return PlatformGlobals.getPrimaryKeyFail();
        }

        // Removes the order credits from the buying organisation.
        dataSource.updateCredits(buyOrder.getOrganisation(), -transactionCredits);

        // Creates the set of sell orders that can be automatically resolved with the sell order.
        Set<TPOrder> sellOrders = dataSource.getOrders(false);
        Set<TPOrder> transactionOrders = new TreeSet<>();
        for (TPOrder sellOrder : sellOrders) {
            if(sellOrder.getAsset().equals(buyOrder.getAsset())
                    && sellOrder.getCredits() <= buyOrder.getCredits()) {
                transactionOrders.add(sellOrder);
            }
        }

        // Counter used to track how many assets have been sold.
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

            if (amountUsed == 0) break;
        }

        // Adds the remaining order to the trading platform.
        if (amountUsed > 0) {
            dataSource.addOrder(buyOrder.getOrganisation(), buyOrder.getAsset(), amountUsed,
                    buyOrder.getCredits(), true);
        }

        //return number of assets bought.
        return buyOrder.getAmount() - amountUsed;
    }

    /**
     * This method is responsible for placing a sell order onto the market.
     *
     * @param sellOrder the asset order being sold.
     * @return the number of assets that were immediately sold, OR
     * PlatformGlobals.getGeneralSQLFail() if sellOrder did not go through.
     */
    public int addSellOrder(TPOrder sellOrder) {

        // If the selling organisation doesn't have enough assets, this stops the order
        // being added to the database.
        int organisationAssets = dataSource.getAssetAmount(sellOrder.getOrganisation(), sellOrder.getAsset());
        if (sellOrder.getAmount() > organisationAssets) {
            return PlatformGlobals.getPrimaryKeyFail();
        }

        // Removes the order asset amount from the selling organisation.
        dataSource.updateAssetAmount(sellOrder.getOrganisation(),
                sellOrder.getAsset(), -sellOrder.getAmount());

        // Creates the set of buy orders that can be automatically resolved with the sell order.
        Set<TPOrder> buyOrders = dataSource.getOrders(true);
        Set<TPOrder> transactionOrders = new TreeSet<>();
        for (TPOrder buyOrder : buyOrders) {
            if(sellOrder.getAsset().equals(buyOrder.getAsset())
                    && sellOrder.getCredits() <= buyOrder.getCredits()) {
                transactionOrders.add(buyOrder);
            }
        }

        // Counter used to track how many assets have been sold.
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

            // Adds the asset to the buy organisation if it doesn't exist.
            dataSource.addAsset(buyOrder.getOrganisation(), buyOrder.getAsset(), 0);

            // Adds the sold assets to the buying organisation.
            dataSource.updateAssetAmount(buyOrder.getOrganisation(), buyOrder.getAsset(),
                    buyOrder.getAmount());

            // Update the selling organisation's credits.
            int sellProfit = sellOrder.getCredits() * buyOrder.getAmount();
            dataSource.updateCredits(sellOrder.getOrganisation(), sellProfit);

            // Add the completed transaction to the history.
            dataSource.addTransaction(buyOrder.getOrganisation(), sellOrder.getOrganisation(),
                    buyOrder.getAsset(), buyOrder.getAmount(), sellOrder.getCredits());

            //If the buy order was listed higher then the sell order, refund the buying
            //organisation by the difference.
            int creditDifference = buyOrder.getCredits() - sellOrder.getCredits();
            int creditRefund = creditDifference * buyOrder.getAmount();
            dataSource.updateCredits(buyOrder.getOrganisation(), creditRefund);

            if (amountUsed == 0) break;
        }

        // Adds the remaining order to the trading platform.
        if (amountUsed > 0) {
            dataSource.addOrder(sellOrder.getOrganisation(), sellOrder.getAsset(), amountUsed,
                    sellOrder.getCredits(), false);
        }

        //return number of assets sold.
        return sellOrder.getAmount() - amountUsed;
    }

    /**
     * This method is responsible for removing an order placed by the user.
     * @param id the identification number of the order.
     * @return int - the number of rows affected from the query, OR
     * PlatformGlobals.getGeneralSQLFail() if sellOrder did not go through.
     */
    public int removeOrder(int id) {
       TPOrder order = dataSource.getOrder(id);
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
