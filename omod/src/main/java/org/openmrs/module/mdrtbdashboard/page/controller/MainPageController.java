package org.openmrs.module.mdrtbdashboard.page.controller;

import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.MdrtbConcepts;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

/**
 * Created by Dennis Henry on 12/19/2016.
 */
public class MainPageController {
    MdrtbDashboardService dashboard = Context.getService(MdrtbDashboardService.class);

    public String get(
            @RequestParam(value = "patient", required = true) Patient patient,
            @RequestParam(value = "tabs", required = false) String tabs,
            PageModel model,
            UiUtils ui) {
        Collection<ProgramWorkflowState> tbbOutcomes = Context.getService(MdrtbService.class).getPossibleTbProgramOutcomes();
        Collection<ProgramWorkflowState> mdrOutcomes = Context.getService(MdrtbService.class).getPossibleMdrtbProgramOutcomes();

        MdrtbPatientProgram current = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
        PatientProgramDetails details = dashboard.getPatientProgramDetails(current);
        // Test if patient is Enrolled in Any program
        if (!(current != null && current.getActive())){
            return "redirect:" + ui.pageLink("mdrtbdashboard", "enroll")+"?patient="+patient.getId();
        }

        // Test if Intake Form was ever filled
        if (details.getFacility() == null){
            return "redirect:" + ui.pageLink("mdrtbdashboard", "intake")+"?patient="+patient.getId();
        }

        model.addAttribute("patient", patient);
        model.addAttribute("current", current);
        model.addAttribute("program", current.getPatientProgram());
        model.addAttribute("tabs", tabs);
        model.addAttribute("tbbOutcomes", tbbOutcomes);
        model.addAttribute("mdrOutcomes", mdrOutcomes);

        return null;
    }
}
