package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.ConceptAnswer;
import org.openmrs.Patient;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtb.model.PatientProgramDetails;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * Created by Dennis Henry
 * Created on 4/15/2017.
 */
public class TransferInPageController {
    MdrtbService mdrtbService = Context.getService(MdrtbService.class);

    public String get(
            @RequestParam(value = "patient") Patient patient,
            @RequestParam(value = "programId", required = false) Integer programId,
            PageModel model,
            UiUtils ui) {
        MdrtbPatientProgram pp = mdrtbService.getMostRecentMdrtbPatientProgram(patient);
        PatientProgramDetails pd = mdrtbService.getPatientProgramDetails(pp);

        String gender = "Male";
        if (patient.getGender().equals("F")){
            gender = "Female";
        }

        Collection<ProgramWorkflowState> enrollmentPreviousTreatment = mdrtbService.getPossibleClassificationsAccordingToPreviousTreatment();
        Collection<ProgramWorkflowState> enrollmentClassifications = mdrtbService.getPossibleClassificationsAccordingToPreviousDrugUse();
        Collection<ProgramWorkflowState> enrollmentPatientType = mdrtbService.getPossibleClassificationsAccordingToPatientType();
        Collection<ProgramWorkflowState> enrollmentTreatmentCategory = mdrtbService.getPossibleClassificationsAccordingToTreatmentCategory();
        Collection<ConceptAnswer> anatomicalSites = mdrtbService.getPossibleAnatomicalSites();
        Collection<ConceptAnswer> siteConfirmation = mdrtbService.getPossibleAnatomicalSitesConfirmation();

        model.addAttribute("programId", programId);
        model.addAttribute("patient", patient);
        model.addAttribute("gender", gender);
        model.addAttribute("pp", pp);
        model.addAttribute("pd", pd);
        model.addAttribute("program", pp.getPatientProgram());

        if (pp.getPatientProgram().getProgram().getName().equals(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name"))){
            model.addAttribute("class01", pp.getClassificationAccordingToPreviousDrugUse());
            model.addAttribute("class02", pp.getClassificationAccordingToPreviousTreatment());
        }
        else {
            model.addAttribute("class01",pp.getClassificationAccordingToPatientType());
            model.addAttribute("class02",pp.getClassificationAccordingToTreatmentCategory());
        }

        model.addAttribute("anatomicalSites", anatomicalSites);
        model.addAttribute("siteConfirmation", siteConfirmation);
        model.addAttribute("enrollmentPreviousTreatment", enrollmentPreviousTreatment);
        model.addAttribute("enrollmentClassifications", enrollmentClassifications);
        model.addAttribute("enrollmentPatientType", enrollmentPatientType);
        model.addAttribute("enrollmentTreatmentCategory", enrollmentTreatmentCategory);

        return null;
    }
}
