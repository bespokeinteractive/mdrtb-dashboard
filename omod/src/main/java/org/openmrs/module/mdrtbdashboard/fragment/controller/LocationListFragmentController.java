package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.model.LocationCentres;
import org.openmrs.module.mdrtb.model.LocationCentresAgencies;
import org.openmrs.module.mdrtb.model.LocationCentresRegions;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationFacilities;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Dennis Henry
 * Created on 4/10/2017.
 */

public class LocationListFragmentController {
    MdrtbDashboardService dashboardSvc = Context.getService(MdrtbDashboardService.class);
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    public String get(FragmentModel model, UiUtils ui){
        List<LocationCentresRegions> regions = mdrtbService.getRegions();
        List<LocationCentresAgencies> agencies = mdrtbService.getAgencies();

        model.addAttribute("regions", regions);
        model.addAttribute("agencies", agencies);

        return null;
    }

    public List<SimpleObject> getMdrtbLocations(UiUtils ui) {
        List<LocationCentres> centres = mdrtbService.getCentres();
        Collections.sort(centres, new Comparator<LocationCentres>() {
            @Override
            public int compare(LocationCentres o1, LocationCentres o2) {
                return o1.getSerialNumber().compareTo(o2.getSerialNumber());
            }
        });
        return SimpleObject.fromCollection(centres, ui, "serialNumber", "location.id", "location.name", "agency.name", "region.name");
    }

    public SimpleObject getLocationDetails(@RequestParam(value = "locationId") Location location){
        LocationCentres centre = mdrtbService.getCentresByLocation(location);
        LocationFacilities facility = Context.getService(MdrtbDashboardService.class).getLocationFacility(location);

        SimpleObject locations = SimpleObject.create("names", location.getName(), "serial", centre.getSerialNumber(), "agency", centre.getAgency().getId(), "region", centre.getRegion().getId(),"facility", facility.getName());
        return SimpleObject.create("location", locations);
    }

    public SimpleObject addLocationDetails(@RequestParam(value = "names") String names,
                                           @RequestParam(value = "serial") String serial,
                                           @RequestParam(value = "agency") Integer agentId,
                                           @RequestParam(value = "region") Integer regionId,
                                           @RequestParam(value = "facility") String facilityName,
                                           UiSessionContext session,
                                           UiUtils ui){

        if (Context.getLocationService().getLocation(names) != null){
            return SimpleObject.create("status", "failed", "message", "Location already exsists!");
        }

        LocationCentresRegions region = mdrtbService.getRegion(regionId);
        LocationCentresAgencies agency = mdrtbService.getAgency(agentId);

        Location location = new Location();
        location.setName(names);
        location = Context.getLocationService().saveLocation(location);

        LocationCentres centre = new LocationCentres();
        centre.setLocation(location);
        centre.setSerialNumber(serial);
        centre.setAgency(agency);
        centre.setRegion(region);
        centre.setCreatedOn(new Date());
        centre.setCreator(Context.getAuthenticatedUser().getPerson());
        mdrtbService.saveLocationCentres(centre);

        LocationFacilities facility = new LocationFacilities();
        facility.setLocation(location);
        facility.setName(facilityName);
        facility.setStatus("active");
        facility.setCreatedOn(new Date());
        facility.setCreator(session.getCurrentUser().getPerson());
        dashboardSvc.saveLocationFacilities(facility);

        return SimpleObject.create("status", "success", "message", "Location successfully added!");
    }

    public SimpleObject updateLocationDetails(@RequestParam(value = "location") Location location,
                                              @RequestParam(value = "names") String names,
                                              @RequestParam(value = "serial") String serial,
                                              @RequestParam(value = "agency") Integer agentId,
                                              @RequestParam(value = "facility") String facilityName,
                                              @RequestParam(value = "region") Integer regionId,
                                              UiSessionContext session){

        LocationFacilities facility = dashboardSvc.getLocationFacility(location);
        LocationCentresRegions region = mdrtbService.getRegion(regionId);
        LocationCentresAgencies agency = mdrtbService.getAgency(agentId);
        LocationCentres centre = mdrtbService.getCentresByLocation(location);
        centre.setSerialNumber(serial);
        centre.setAgency(agency);
        centre.setRegion(region);

        if(facility == null){
            facility = new LocationFacilities();
            facility.setStatus("active");
            facility.setLocation(location);
            facility.setCreatedOn(new Date());
            facility.setCreator(session.getCurrentUser().getPerson());
        }

        facility.setName(facilityName);
        location.setName(names);

        mdrtbService.saveLocationCentres(centre);
        dashboardSvc.saveLocationFacilities(facility);
        Context.getLocationService().saveLocation(location);

        return SimpleObject.create("status", "success", "message", "Location successfully updated!");
    }
}
