package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.Patient;
import java.util.Collection;

import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Dennis Henry on 12/19/2016.
 */
public class EnrollPageController {
    public String get(
            @RequestParam(value = "patient") Patient patient,
            PageModel model,
            UiUtils ui) {
        MdrtbPatientProgram mostRecentProgram = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
        if (mostRecentProgram != null && mostRecentProgram.getActive()){
            return "redirect:" + ui.pageLink("mdrtbdashboard", "main")+"?patient="+patient.getId();
        }

        Collection<ProgramWorkflowState> enrollmentPreviousTreatment = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousTreatment();
        Collection<ProgramWorkflowState> enrollmentClassifications = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousDrugUse();

        Collection<ProgramWorkflowState> enrollmentPatientType = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPatientType();
        Collection<ProgramWorkflowState> enrollmentTreatmentCategory = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToTreatmentCategory();

        String gender = "Male";
        if (patient.getGender().equals("F")){
            gender = "Female";
        }

        model.addAttribute("patient", patient);
        model.addAttribute("gender", gender);
        model.addAttribute("enrollmentPreviousTreatment", enrollmentPreviousTreatment);
        model.addAttribute("enrollmentClassifications", enrollmentClassifications);
        model.addAttribute("enrollmentPatientType", enrollmentPatientType);
        model.addAttribute("enrollmentTreatmentCategory", enrollmentTreatmentCategory);

        return null;
    }
}
