package org.openmrs.module.mdrtbdashboard.util;

import org.openmrs.Location;

/**
 * Created by Dennis Henry
 * Created on 4/4/2017.
 */
public class UserLocationModel {
    Integer id;
    String name;
    Boolean checked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
