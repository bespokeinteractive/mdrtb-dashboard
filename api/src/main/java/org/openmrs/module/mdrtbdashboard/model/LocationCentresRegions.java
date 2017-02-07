package org.openmrs.module.mdrtbdashboard.model;

import org.openmrs.Person;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by daugm on 12/24/2016.
 */
public class LocationCentresRegions implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private Person creator;
    private Date createdOn;

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

    public Person getCreator() {
        return creator;
    }
    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Date getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }
}
