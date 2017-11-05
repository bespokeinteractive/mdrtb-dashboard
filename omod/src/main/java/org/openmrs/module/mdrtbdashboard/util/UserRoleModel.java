package org.openmrs.module.mdrtbdashboard.util;

/**
 * Created by kimani on 11/5/2017.
 */
public class UserRoleModel {
    Integer id;

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

    String name;
    Boolean checked;
}
