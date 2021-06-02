package main.java.tradingPlatform;

import main.java.database.TradingPlatformDataSource;

import java.util.Set;


/**
 * TODO
 */
public class ItAdministration extends TPUser {




    public ItAdministration(TradingPlatformDataSource dataSource, String adminName){
        super(dataSource);

    }

    /**
     * Creates a new standard user account if the user doesn't already exist.
     *
     * @param userName the name of the username of the user.
     * @param password the password the of the user.
     * @param organisation the organisation the user belongs too.
     * @throws DuplicationException the specified field already exists.
     * @throws NullValueException specified field does not exist in the database.
     * @throws InvalidValueException value entered is incorrect type.
     */
    public void addStandardUser(String userName, String password, String organisation) throws DuplicationException, NullValueException, InvalidValueException {

    }

    /**
     * Creates a new staff admin account if the staff user doesn't already exist.
     *
     * @param userName the username of the admin.
     * @param password the password of the admin.
     * @throws DuplicationException
     * @throws WrongCredentialException
     * @throws InvalidValueException
     */
    public void addItUser(String userName, String password) throws DuplicationException, WrongCredentialException, InvalidValueException, UnknownDatabaseException {
        String adminUser = "ADMIN";
        String organisation = "ADMIN";
        int rowsAffected = dataSource.addUser(userName, password, adminUser, organisation);
        if (rowsAffected == PlatformGlobals.getPrimaryKeyFail()) {
            String message = "Organisation already exists.";
            throw new DuplicationException(message);
        } else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(generalMessage);
        }
    }

    /**
     * Checks the database to see if the user exists and if so, deletes the user
     * from the system.
     *
     * @param userName the name of the user being removed.
     * @throws NullValueException
     * @throws UnknownDatabaseException
     */
    public void removeUser(String userName) throws NullValueException, UnknownDatabaseException {

    }

    /**
     * Creates a new organisation if the organisation doesn't already exist.
     *
     * @param organisation the name of the organisation being created.
     * @throws DuplicationException
     * @throws NullValueException
     * @throws InvalidValueException
     */
    public void addOrganisation(String organisation, int credits) throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        if (organisation.equals("ADMIN")) {
            String message = "This organisation name is reserved.";
            throw new InvalidValueException(message);
        }
        int rowsAffected = dataSource.addOrganisation(organisation, credits);
        if (rowsAffected == PlatformGlobals.getNoRowsAffected()) {
            String message = "Organisation already exists.";
            throw new DuplicationException(message);
        } else if (rowsAffected == PlatformGlobals.getGeneralSQLFail()) {
            throw new UnknownDatabaseException(generalMessage);
        }
        //        if (!organisationList.contains(organisation)) {
//            int rowsAffected = dataSource.addOrganisation(organisation, )
//        }
    }

    /**
     * Checks to see if the organisation exists and if so, deletes the organisation
     * from the system.
     *
     * @param organisation the organisation to be removed.
     * @throws NullValueException
     * @throws UnknownDatabaseException
     */
    public void removeOrganisation(String organisation) throws NullValueException, UnknownDatabaseException {

    }

//    /**
//     * Increases the amount of credits to a specified organisation.
//     *
//     * @param organisation the name of the organisation.
//     * @param credits the value of credits to be added to the organisation.
//     * @throws NullValueException
//     * @throws InvalidValueException
//     */
//    public void increaseCredits(String organisation, int credits) throws NullValueException, InvalidValueException {
//
//    }
//
//    /**
//     * Reduces the amount of  credits from the specified organisation.
//     *
//     * @param organisation the name of the organisation to remove credits from.
//     * @param credits the value of credits to be removed from the organisation.
//     * @throws NullValueException
//     * @throws InvalidValueException
//     */
//    public void reduceCredits(String organisation, int credits) throws NullValueException, InvalidValueException{
//        // throws credits to big exception
//    }

    /** Updates the amount of credits an organisation has to a specified amount.
     *
     * @param organisation the name of the organisation to remove credits from.
     * @param credits the value of credits to be removed from the organisation.
     * @throws NullValueException
     * @throws InvalidValueException
     */
    public void updateCredits(String organisation, int credits) throws NullValueException, InvalidValueException, DuplicationException, UnknownDatabaseException {
        int rowsAffected = dataSource.updateCredits(organisation, credits);
        if (rowsAffected == PlatformGlobals.getNoRowsAffected()){
            String message = "This organisation already has this asset.";
            throw new NullValueException(message);
        }
        else if(rowsAffected == PlatformGlobals.getGeneralSQLFail()){
            String message = "Update did not go through. Please refresh page.";
            throw new UnknownDatabaseException(message);
        }

    }

    /**
     * Adds a new asset and asset amount to the specified organisation.
     *
     * @param organisation the organisation the asset is being added too.
     * @param asset the name of the asset being added.
     * @param amount the amount of the asset that is being added.
     * @throws DuplicationException
     * @throws NullValueException
     * @throws InvalidValueException
     */
    public void addAsset(String organisation, String asset, int amount) throws DuplicationException, InvalidValueException, UnknownDatabaseException {
        int rowsAffected = dataSource.addAsset(organisation, asset, amount);
        switch (rowsAffected) {
            case primaryKeyFail -> {
                String message = "This organisation already has this asset.";
                throw new DuplicationException(message);
            }
            case foreignKeyFail -> {
                String message = "Organisation does not exist.";
                throw new InvalidValueException(message);
            }
            case generalSQLFail -> {
                String message = "Update did not go through. Please refresh page.";
                throw new UnknownDatabaseException(message);
            }
        }
    }

    /**
     *
     * Adjusts the amount of a pre-existing asset belonging to a specified organisation.
     *
     * @param organisation the name of the organisation that the asset belongs too.
     * @param asset the name of the asset that is being updated.
     * @param amount the amount of the asset that is being updated.
     * @throws DuplicationException
     * @throws NullValueException
     * @throws InvalidValueException
     */
    public void updateAssetAmount(String organisation, String asset, int amount) throws InvalidValueException, UnknownDatabaseException, NullValueException {
        int rowsAffected = dataSource.updateAssetAmount(organisation,asset,amount);
        switch (rowsAffected) {
            case primaryKeyFail -> {
                String message = "This organisation already has this asset.";
                throw new NullValueException(message);
            }
            case foreignKeyFail -> {
                String message = "Organisation does not exist.";
                throw new InvalidValueException(message);
            }
            case generalSQLFail -> {
                String message = "Update did not go through. Please refresh page.";
                throw new UnknownDatabaseException(message);
            }
        }
        //0 didnt work
        // +number a table was updated
    }


    /**
     * Removes an asset from a specified organisation.
     *
     * @param organisation the name of the organisation the asset belongs too.
     * @param asset the name of the asset being removed.
     * @throws NullValueException
     * @throws InvalidValueException
     * @throws UnknownDatabaseException
     */
    public void removeAsset(String organisation, String asset) throws NullValueException, InvalidValueException, UnknownDatabaseException {
        //SOmething == PlatformGlobals.getNoRowsAffected() (which is 0)
    }
}
