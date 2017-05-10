package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Obs;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtbdashboard.reports.CaseFindingReport;
import org.openmrs.module.mdrtbdashboard.util.DateRangeModel;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Dennis Henry
 * Created on 5/9/2017.
 */
public class ReportCasefindingFragmentController {
    public String get(FragmentModel model,
                      FragmentConfiguration config,
                      UiUtils ui,
                      UiSessionContext session){
        CaseFindingReport report = new CaseFindingReport();

        config.require("qtr");
        config.require("year");
        config.require("facility");

        Integer quarter = Integer.parseInt(config.get("qtr").toString());
        Integer year = Integer.parseInt(config.get("year").toString());
        Integer facilityId = Integer.parseInt(config.get("facility").toString());

        DateRangeModel dates = new DateRangeModel(quarter, year);
        Collection<EncounterType> encounterTypes = new ArrayList<EncounterType>();
        encounterTypes.add(new EncounterType(1));
        List<Encounter> encounters = Context.getEncounterService().getEncounters(null, session.getSessionLocation(), dates.getStartDate(), dates.getEndDate(), null, encounterTypes, null, null, null, true);

        for (Encounter encounter: encounters){

            for (Obs obs : encounter.getAllObs()){

            }
        }

        model.addAttribute("report", report);
        return null;
    }
}
