package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by
 * Dennis on 4/10/2017.
 */

public class LocationsPageController {
    public String get(@RequestParam(value = "locationId", required = false) Integer locationId,
                      PageModel model,
                      UiUtils ui){
        if (locationId == null){
            model.addAttribute("locationId", 0);
        }
        else {
            Location location = Context.getLocationService().getLocation(locationId);

            model.addAttribute("location", location);
            model.addAttribute("locationId", locationId);
        }

        return null;
    }
}

