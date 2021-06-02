package main.java.tradingPlatform;

import java.sql.SQLException;

/**
 * TODO
 */
public class ItAdministration extends TPUser {

    String adminName;

    public ItAdministration(String adminName){
        this.adminName = adminName;
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
    public void addStandardUser(String userName, String password, String organisation) throws DuplicationException, NullValueException, InvalidValueException, SQLException {

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
    public void addItUser(String userName, String password) throws DuplicationException, WrongCredentialException, InvalidValueException {

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
    public void addOrganisation(String organisation) throws DuplicationException, NullValueException, InvalidValueException {

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

    /**
     * Increases the amount of credits to a specified organisation.
     *
     * @param organisation the name of the organisation.
     * @param credits the value of credits to be added to the organisation.
     * @throws NullValueException
     * @throws InvalidValueException
     */
    public void increaseCredits(String organisation, int credits) throws NullValueException, InvalidValueException {

    }

    /**
     * Reduces the amount of  credits from the specified organisation.
     *
     * @param organisation the name of the organisation to remove credits from.
     * @param credits the value of credits to be removed from the organisation.
     * @throws NullValueException
     * @throws InvalidValueException
     */
    public void reduceCredits(String organisation, int credits) throws NullValueException, InvalidValueException{
        // throws credits to big exception
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
    public void addAsset(String organisation, String asset, int amount) throws DuplicationException, NullValueException, InvalidValueException {

    }

    /**
     *
     * Increases the amount of a pre-existing asset belonging to a specified organisation.
     *
     * @param organisation the name of the organisation that the asset belongs too.
     * @param asset the name of the asset that is being updated.
     * @param amount the amount of the asset that is being updated.
     * @throws DuplicationException
     * @throws NullValueException
     * @throws InvalidValueException
     */
    public void addAssetAmount(String organisation, String asset, int amount) throws DuplicationException, NullValueException, InvalidValueException {
        //excepts negative amount? If so remove asset from organisation.
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

    }
}
