package test.java.mockups;

import main.java.common.PlatformGlobals;
import main.java.common.TPOrder;

/**
 * This extends the datasource mockup and is used by the New transaction class
 * when adding and removing orders from the mock database.
 */
public class NewTransactionDataSourceMockup extends DataSourceMockup{


    public NewTransactionDataSourceMockup(DatabaseMockup database) {
        super(database);
    }

    @Override
    public int addOrder(String organisation, String asset, int amount, int credits, boolean isBuyOrder) {

        TPOrder order = new TPOrder();
        order.setId(database.orderCount);
        order.setOrganisation(organisation);
        order.setAsset(asset);
        order.setAmount(amount);
        order.setCredits(credits);
        String type;
        if (isBuyOrder){
            type = PlatformGlobals.getBuyOrder();
        } else {
            type = PlatformGlobals.getSellOrder();
        }
        order.setType(type);
        order.setDateTime("");

        database.orderList.add(order);

        database.orderCount ++;
        return 1;
    }

    @Override
    public int deleteOrder(int idx) {

        for (TPOrder order : database.orderList) {
            if (order.getId() == idx) {
                database.orderList.remove(order);
                return 1;
            }
        }
        return PlatformGlobals.getNoRowsAffected();
    }
}
