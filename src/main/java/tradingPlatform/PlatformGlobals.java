package main.java.tradingPlatform;

/**
 * This class is responsible for handling commonly used variables
 * and exception parameters.
 */
public class PlatformGlobals {
    private static final int noRowsAffected = 0;
    private static final int primaryKeyFail = -1;
    private static final int foreignKeyFail = -2;
    private static final int generalSQLFail = -3;
    private static final String unknownSQLMessage = "Encountered an error, please refresh.";
    private static final String adminOrganisation = "ADMIN";
    private static final String standardOrganisation = "STANDARD";
    private static final String buyOrder = "BUY";
    private static final String sellOrder = "SELL";

    public static int getNoRowsAffected() {
        return noRowsAffected;
    }

    public static int getPrimaryKeyFail() {
        return primaryKeyFail;
    }

    public static int getForeignKeyFail() {
        return foreignKeyFail;
    }

    public static int getGeneralSQLFail() {
        return generalSQLFail;
    }

    public static String getUnknownSQLMessage() {return unknownSQLMessage;}

    public static String getAdminOrganisation() {
        return adminOrganisation;
    }

    public static String getStandardOrganisation() {
        return standardOrganisation;
    }

    public static String getBuyOrder() {
        return buyOrder;
    }

    public static String getSellOrder() {
        return sellOrder;
    }
}
