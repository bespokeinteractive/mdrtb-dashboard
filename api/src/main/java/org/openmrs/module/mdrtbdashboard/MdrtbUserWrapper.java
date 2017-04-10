package org.openmrs.module.mdrtbdashboard;

import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.service.MdrtbService;

/**
 * Created by Dennis Henry
 * Created on 4/1/2017.
 */

public class MdrtbUserWrapper {
    private User user;
    private String wrapperNames = "N/A";
    private String wrapperLocations = "N/A";

    public MdrtbUserWrapper(User user){
        this.user = user;
        wrapperNames = user.getPersonName().getFullName().toUpperCase();
        wrapperLocations = Context.getService(MdrtbService.class).getUserLocationsAsString(user);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getWrapperNames() {
        return wrapperNames;
    }

    public void setWrapperNames(String wrapperNames) {
        this.wrapperNames = wrapperNames;
    }

    public String getWrapperLocations() {
        return wrapperLocations;
    }

    public void setWrapperLocations(String wrapperLocations) {
        this.wrapperLocations = wrapperLocations;
    }

}
