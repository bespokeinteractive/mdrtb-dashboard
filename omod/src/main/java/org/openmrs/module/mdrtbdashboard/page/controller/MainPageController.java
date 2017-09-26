package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramRegimen;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.util.PersonByNameComparator;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Dennis Henry on 12/19/2016.
 */
public class MainPageController {
    MdrtbDashboardService dashboard = Context.getService(MdrtbDashboardService.class);
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    public String get(
            @RequestParam(value = "patient", required = true) Patient patient,
            @RequestParam(value = "programId", required = false) Integer programId,
            @RequestParam(value = "tabs", required = false) String tabs,
            PageModel model,
            UiUtils ui) {
        Collection<ProgramWorkflowState> tbbOutcomes = mdrtbService.getPossibleTbProgramOutcomes();
        Collection<ProgramWorkflowState> mdrOutcomes = mdrtbService.getPossibleMdrtbProgramOutcomes();
        Collection<ConceptAnswer> regimenTypes = mdrtbService.getPossibleTbTreatmentTypes();

        MdrtbPatientProgram current = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        if (programId != null){
            current = mdrtbService.getMdrtbPatientProgram(programId);
        }

        PatientProgramDetails details = dashboard.getPatientProgramDetails(current);

        List<Location> locations = Context.getLocationService().getAllLocations();
        List<PatientProgramRegimen> regimens = dashboard.getPatientProgramRegimens(details, false);
        Collections.reverse(regimens);
        Collection<ConceptAnswer> anatomicalSites = mdrtbService.getPossibleAnatomicalSites();
        Collection<ConceptAnswer> siteConfirmation = mdrtbService.getPossibleAnatomicalSitesConfirmation();

        // Test if patient is Enrolled in Any program
        if (!(current != null && current.getActive())){
            return "redirect:" + ui.pageLink("mdrtbdashboard", "enroll")+"?patient="+patient.getId();
        }

        // Test if Intake Form was ever filled
        if (details.getFacility() == null){
            if (programId == null){
                return "redirect:" + ui.pageLink("mdrtbdashboard", "intake")+"?patient="+patient.getId();
            }
            else {
                return "redirect:" + ui.pageLink("mdrtbdashboard", "intake")+"?patient="+patient.getId()+"&programId="+programId;
            }
        }

        model.addAttribute("patient", patient);
        model.addAttribute("current", current);
        model.addAttribute("details", details);
        model.addAttribute("regimens", regimens);
        model.addAttribute("locations", locations);
        model.addAttribute("regimenTypes", regimenTypes);
        model.addAttribute("program", current.getPatientProgram());
        model.addAttribute("tabs", tabs);
        model.addAttribute("tbbOutcomes", tbbOutcomes);
        model.addAttribute("mdrOutcomes", mdrOutcomes);

        model.addAttribute("anatomicalSites", anatomicalSites);
        model.addAttribute("siteConfirmation", siteConfirmation);

        return null;
    }
}
