package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.Patient;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.context.Context;
import org.openmrs.module.appui.UiSessionContext;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by Dennis Henry
 * Created on 4/15/2017.
 */
public class TransferInPageController {
    public String get(
            @RequestParam(value = "patient") Patient patient,
            PageModel model,
            UiUtils ui) {
        MdrtbPatientProgram pp = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
        String gender = "Male";
        if (patient.getGender().equals("F")){
            gender = "Female";
        }

        Collection<ProgramWorkflowState> enrollmentPreviousTreatment = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousTreatment();
        Collection<ProgramWorkflowState> enrollmentClassifications = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPreviousDrugUse();
        Collection<ProgramWorkflowState> enrollmentPatientType = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToPatientType();
        Collection<ProgramWorkflowState> enrollmentTreatmentCategory = Context.getService(MdrtbService.class).getPossibleClassificationsAccordingToTreatmentCategory();

        model.addAttribute("patient", patient);
        model.addAttribute("gender", gender);
        model.addAttribute("pp", pp);
        model.addAttribute("program", pp.getPatientProgram());

        if (pp.getPatientProgram().getProgram().getName().equals(Context.getAdministrationService().getGlobalProperty("mdrtb.program_name"))){
            model.addAttribute("class01", pp.getClassificationAccordingToPreviousDrugUse());
            model.addAttribute("class02", pp.getClassificationAccordingToPreviousTreatment());
        }
        else {
            model.addAttribute("class01",pp.getClassificationAccordingToPatientType());
            model.addAttribute("class02",pp.getClassificationAccordingToTreatmentCategory());
        }

        model.addAttribute("enrollmentPreviousTreatment", enrollmentPreviousTreatment);
        model.addAttribute("enrollmentClassifications", enrollmentClassifications);
        model.addAttribute("enrollmentPatientType", enrollmentPatientType);
        model.addAttribute("enrollmentTreatmentCategory", enrollmentTreatmentCategory);

        return null;
    }
}
