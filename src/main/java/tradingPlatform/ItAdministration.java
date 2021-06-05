package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;

import java.security.NoSuchAlgorithmException;


/**
 * This class handles the administration applications sides of the system such as creating
 * users, updating organisation attributes, updating organisation attributes as well as
 * removing fields.
 */
public class ItAdministration extends TPUser {

    private final String errorMessage = "Encountered an error, please refresh.";


    public ItAdministration(TradingPlatformDataSource dataSource, String adminName){
        super(dataSource, adminName, PlatformGlobals.getAdminOrganisation());
    }


    /**
     * Creates a new standard user account if the user doesn't already exist.
     *
     * @param userName the name of the username of the user.
     * @param password the password the of the user.
     * @param organisation the organisation the user belongs too.
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    public void addStandardUser(String userName, String password, String organisation) throws DuplicationException, NullValueException, UnknownDatabaseException {
        String hashedPassword;
        try {
            hashedPassword = HashPassword.hashedPassword(userName, password);
        } catch (NoSuchAlgorithmException e) {
            throw new UnknownDatabaseException(errorMessage);
        }
        int rowsAffected = dataSource.addUser(userName, hashedPassword, PlatformGlobals.getStandardOrganisation(), organisation);
        if (rowsAffected == PlatformGlobals.getPrimaryKeyFail()) {
            String message = "User already exists.";
            throw new DuplicationException(message);
        }
        else if (rowsAffected == PlatformGlobals.getForeignKeyFail()) {
            String message = "Organisation doesn't exist.";
            throw new NullValueException(message);
        }
        else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(errorMessage);
        }
    }

    /**
     * Creates a new staff admin account if the staff user doesn't already exist.
     *
     * @param userName the username of the admin.
     * @param password the password of the admin.
     * @throws DuplicationException the specified field already exists.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    public void addItUser(String userName, String password) throws DuplicationException, UnknownDatabaseException {
        String hashedPassword;
        try {
            hashedPassword = HashPassword.hashedPassword(userName, password);
        } catch (NoSuchAlgorithmException e) {
            throw new UnknownDatabaseException(errorMessage);
        }
        int rowsAffected = dataSource.addUser(userName, hashedPassword, PlatformGlobals.getAdminOrganisation(),
                PlatformGlobals.getAdminOrganisation());
        if (rowsAffected == PlatformGlobals.getPrimaryKeyFail()) {
            String message = "Admin user already exists.";
            throw new DuplicationException(message);
        } else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(errorMessage);
        }
    }

    /**
     * Checks the database to see if the user exists and if so, deletes the user
     * from the system.
     *
     * @param userName the name of the user being removed.
     * @throws NullValueException specified field does not exist in the database.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    public void removeUser(String userName) throws NullValueException, UnknownDatabaseException {
        int rowsAffected = dataSource.deleteUser(userName);
        if (rowsAffected == PlatformGlobals.getForeignKeyFail()) {
            String message = "User doesn't exist.";
            throw new NullValueException(message);
        } else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(generalMessage);
        }
    }

    /**
     * Creates a new organisation if the organisation doesn't already exist.
     *
     * @param organisation the name of the organisation being created.
     * @throws DuplicationException the specified field already exists.
     * @throws InvalidValueException the field doesn't exist.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    public void addOrganisation(String organisation, int credits) throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        if (organisation.equals("ADMIN")) {
            String message = "This organisation name is reserved.";
            throw new InvalidValueException(message);
        }
        int rowsAffected = dataSource.addOrganisation(organisation, credits);
        if (rowsAffected == PlatformGlobals.getPrimaryKeyFail()) {
            String message = "Organisation already exists.";
            throw new DuplicationException(message);
        } else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(generalMessage);
        }
    }

    /**
     * Checks to see if the organisation exists and if so, deletes the organisation
     * from the system.
     *
     * @param organisation the organisation to be removed.
     * @throws NullValueException specified field does not exist in the database.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     * @throws InvalidValueException the field doesn't exist.
     */
    public void removeOrganisation(String organisation) throws NullValueException, UnknownDatabaseException, InvalidValueException {
        if (organisation.equals("ADMIN")) {
            String message = "This organisation name is reserved.";
            throw new InvalidValueException(message);
        }
        int rowsAffected = dataSource.deleteOrganisation(organisation);
        if (rowsAffected == PlatformGlobals.getForeignKeyFail()) {
            String message = "Organisation doesn't exist.";
            throw new NullValueException(message);
        } else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(generalMessage);
        }
    }

    /** Updates the amount of credits an organisation has to a specified amount.
     *
     * @param organisation the name of the organisation to remove credits from.
     * @param credits the value of credits to be removed from the organisation.
     * @throws InvalidValueException the field doesn't exist.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    public void updateCreditAmount(String organisation, int credits) throws InvalidValueException, UnknownDatabaseException {
        int rowsAffected = dataSource.updateCredits(organisation, credits);
        if (rowsAffected == PlatformGlobals.getForeignKeyFail()){
            String message = "This organisation doesn't exist.";
            throw new InvalidValueException(message);
        }
        else if(rowsAffected == PlatformGlobals.getGeneralSQLFail()){
            throw new UnknownDatabaseException(errorMessage);
        }
    }

    /**
     * Adds a new asset and asset amount to the specified organisation.
     *
     * @param organisation the organisation the asset is being added too.
     * @param asset the name of the asset being added.
     * @param amount the amount of the asset that is being added.
     * @throws DuplicationException the specified field already exists.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     * @throws NullValueException specified field does not exist in the database.
     */
    public void addAsset(String organisation, String asset, int amount) throws DuplicationException, UnknownDatabaseException, NullValueException {
        int rowsAffected = dataSource.addAsset(organisation,asset,amount);
        if (rowsAffected == PlatformGlobals.getPrimaryKeyFail()) {
            String message = "Asset already exists.";
            throw new DuplicationException(message);
        }
        else if (rowsAffected == PlatformGlobals.getForeignKeyFail()) {
            String message = "Organisation doesn't exist.";
            throw new NullValueException(message);
        }
        else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(errorMessage);
        }
    }

    /**
     *
     * Adjusts the amount of a pre-existing asset belonging to a specified organisation.
     *
     * @param organisation the name of the organisation that the asset belongs too.
     * @param asset the name of the asset that is being updated.
     * @param amount the amount of the asset that is being updated.
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    public void updateAssetAmount(String organisation, String asset, int amount) throws UnknownDatabaseException, NullValueException, DuplicationException {
        int rowsAffected = dataSource.updateAssetAmount(organisation,asset,amount);
        if (rowsAffected == PlatformGlobals.getNoRowsAffected()) {
            String message = "Field entered doesn't exist.";
            throw new NullValueException(message);
        }
        else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(errorMessage);
        }
    }

    /**
     * Removes an asset from a specified organisation.
     *
     * @param organisation the name of the organisation the asset belongs too.
     * @param asset the name of the asset being removed.
     * @throws NullValueException specified field does not exist in the database.
     * @throws UnknownDatabaseException update to the database was unsuccessful.
     */
    public void removeAsset(String organisation, String asset) throws NullValueException, UnknownDatabaseException {
        int rowsAffected = dataSource.deleteAsset(organisation,asset);
        if (rowsAffected == PlatformGlobals.getForeignKeyFail()) {
            String message = "Entered field doesn't exist.";
            throw new NullValueException(message);
        } else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(generalMessage);
        }

    }
}
