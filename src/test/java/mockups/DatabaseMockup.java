package test.java.mockups;

import main.java.common.*;

import java.util.HashSet;
import java.util.Set;

public class DatabaseMockup {

    Set<UserMockup> userMock = new HashSet<>();
    Set<UserOrganisation> userOrganisationList = new HashSet<>();
    Set<Organisation> organisationsList = new HashSet<>();
    Set<Asset> assetsList = new HashSet<>();
    final Set<TPOrder> orderList = new HashSet<>();
    final Set<Transaction> transactionList = new HashSet<>();

    int orderCount = 0;
    int historyCount = 0;
    String admin = PlatformGlobals.getAdminOrganisation();
    Organisation org = new Organisation(admin, 0);
    UserMockup aUser = new UserMockup(admin, admin, admin, admin);
    UserOrganisation initialUser = new UserOrganisation(admin, admin);

    public DatabaseMockup() {
        userMock.add(aUser);
        userOrganisationList.add(initialUser);
        organisationsList.add(org);
    }
}
