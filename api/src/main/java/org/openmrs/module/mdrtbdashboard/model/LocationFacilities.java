package org.openmrs.module.mdrtbdashboard.model;

import org.openmrs.Location;
import org.openmrs.Person;

import java.util.Date;

/**
 * Created by Dennis Henry
 * Created on 5/4/2017.
 */
public class LocationFacilities {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String status;
    private Location location;
    private Person creator;
    private Date createdOn;
    private String description;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
