package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationCentres;
import org.openmrs.module.mdrtbdashboard.model.LocationCentresAgencies;
import org.openmrs.module.mdrtbdashboard.model.LocationCentresRegions;
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

    public String get(FragmentModel model, UiUtils ui){
        MdrtbDashboardService dashboard = Context.getService(MdrtbDashboardService.class);

        List<LocationCentresRegions> regions = dashboard.getRegions();
        List<LocationCentresAgencies> agencies = dashboard.getAgencies();

        model.addAttribute("regions", regions);
        model.addAttribute("agencies", agencies);

        return null;
    }

    public List<SimpleObject> getMdrtbLocations(UiUtils ui) {
        List<LocationCentres> centres = Context.getService(MdrtbDashboardService.class).getCentres();
        Collections.sort(centres, new Comparator<LocationCentres>() {
            @Override
            public int compare(LocationCentres o1, LocationCentres o2) {
                return o1.getSerialNumber().compareTo(o2.getSerialNumber());
            }
        });
        return SimpleObject.fromCollection(centres, ui, "serialNumber", "location.id", "location.name", "agency.name", "region.name");
    }

    public SimpleObject getLocationDetails(@RequestParam(value = "locationId") Location location){
        LocationCentres centre = Context.getService(MdrtbDashboardService.class).getCentresByLocation(location);
        SimpleObject locations = SimpleObject.create("names", location.getName(), "serial", centre.getSerialNumber(), "agency", centre.getAgency().getId(), "region", centre.getRegion().getId());
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

        LocationCentresRegions region = dashboardSvc.getRegion(regionId);
        LocationCentresAgencies agency = dashboardSvc.getAgency(agentId);

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
        dashboardSvc.saveLocationCentres(centre);

        LocationFacilities facility = new LocationFacilities();
        facility.setLocation(location);
        facility.setName(facilityName);
        facility.setStatus("active");
        facility.setCreatedOn(new Date());
        facility.setCreator(session.getCurrentUser().getPerson());
        //dashboardSvc.saveLocationCentres()




        return SimpleObject.create("status", "success", "message", "Location successfully added!");
    }

    public SimpleObject updateLocationDetails(@RequestParam(value = "location") Location location,
                                              @RequestParam(value = "names") String names,
                                              @RequestParam(value = "serial") String serial,
                                              @RequestParam(value = "agency") Integer agentId,
                                              @RequestParam(value = "region") Integer regionId){
        LocationCentresRegions region = dashboardSvc.getRegion(regionId);
        LocationCentresAgencies agency = dashboardSvc.getAgency(agentId);
        LocationCentres centre = dashboardSvc.getCentresByLocation(location);
        centre.setSerialNumber(serial);
        centre.setAgency(agency);
        centre.setRegion(region);
        dashboardSvc.saveLocationCentres(centre);

        return SimpleObject.create("status", "success", "message", "Location successfully updated!");
    }
}
