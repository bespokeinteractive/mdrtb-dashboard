package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.ConceptAnswer;
import org.openmrs.Patient;
import java.util.Collection;

import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.model.UserLocation;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.LocationCentres;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Dennis Henry on 12/19/2016.
 */
public class EnrollPageController {
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    public String get(
            @RequestParam(value = "patient") Patient patient,
            PageModel model,
            UiUtils ui,
            UiSessionContext session) {
        MdrtbPatientProgram mostRecentProgram = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        if (mostRecentProgram != null && mostRecentProgram.getActive()){
            return "redirect:" + ui.pageLink("mdrtbdashboard", "main")+"?patient="+patient.getId();
        }

        Collection<ProgramWorkflowState> enrollmentPreviousTreatment = mdrtbService.getPossibleClassificationsAccordingToPreviousTreatment();
        Collection<ProgramWorkflowState> enrollmentClassifications = mdrtbService.getPossibleClassificationsAccordingToPreviousDrugUse();
        Collection<ProgramWorkflowState> enrollmentPatientType = mdrtbService.getPossibleClassificationsAccordingToPatientType();
        Collection<ProgramWorkflowState> enrollmentTreatmentCategory = mdrtbService.getPossibleClassificationsAccordingToTreatmentCategory();
        Collection<ConceptAnswer> anatomicalSites = mdrtbService.getPossibleAnatomicalSites();
        Collection<ConceptAnswer> siteConfirmation = mdrtbService.getPossibleAnatomicalSitesConfirmation();

        LocationCentres usl = Context.getService(MdrtbDashboardService.class).getCentresByLocation(session.getSessionLocation());
        String gender = "Male";
        if (patient.getGender().equals("F")){
            gender = "Female";
        }

        model.addAttribute("patient", patient);
        model.addAttribute("agency", usl.getAgency().getName());
        model.addAttribute("gender", gender);
        model.addAttribute("anatomicalSites", anatomicalSites);
        model.addAttribute("siteConfirmation", siteConfirmation);
        model.addAttribute("enrollmentPreviousTreatment", enrollmentPreviousTreatment);
        model.addAttribute("enrollmentClassifications", enrollmentClassifications);
        model.addAttribute("enrollmentPatientType", enrollmentPatientType);
        model.addAttribute("enrollmentTreatmentCategory", enrollmentTreatmentCategory);

        return null;
    }
}
