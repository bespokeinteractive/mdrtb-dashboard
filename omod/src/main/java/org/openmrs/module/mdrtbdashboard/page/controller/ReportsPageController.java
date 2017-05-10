package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationFacilities;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dennis Henry
 * Created on 5/4/2017.
 */
public class ReportsPageController {
    public String get( @RequestParam(value = "report") String report,
                       @RequestParam(value = "period", required = false) String period,
                       @RequestParam(value = "facility", required = false) Integer facilityId,
                       PageModel model,
                       UiUtils ui,
                       UiSessionContext session){
        LocationFacilities facility = Context.getService(MdrtbDashboardService.class).getFacilityById(facilityId);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        Integer year = Integer.parseInt(period.substring(0,4));
        Integer mnth = Integer.parseInt(period.substring(4));

        if (mnth == 1){
            model.addAttribute("sups", "ST");
        }
        else if (mnth == 2){
            model.addAttribute("sups", "ND");
        }
        else if (mnth == 3){
            model.addAttribute("sups", "RD");
        }
        else {
            model.addAttribute("sups", "TH");
        }

        model.addAttribute("report", report);
        model.addAttribute("period", period);
        model.addAttribute("mnth", mnth);
        model.addAttribute("year", year);
        model.addAttribute("facility", facility);
        model.addAttribute("date", df.format(new Date()));

        if (report.equals("cohort") ){
            model.addAttribute("title","Cohort Report");
        } else if (report.equals("casefinding")){
            model.addAttribute("title","Case Finding Report");
        } else if (report.equals("distribution")){
            model.addAttribute("title","Distribution Report");
        } else if (report.equals("defaulting")) {
            model.addAttribute("title", "Defaulters Report");
        } else {
            model.addAttribute("title", "Report Module");
        }

        return null;
    }
}
