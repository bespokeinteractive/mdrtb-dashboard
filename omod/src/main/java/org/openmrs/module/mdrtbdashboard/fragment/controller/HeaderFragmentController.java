package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Dennis Henry on 1/27/2017.
 */
public class HeaderFragmentController {
    public void controller(FragmentModel model,
                           FragmentConfiguration config,
                           UiUtils ui) {
        config.require("patientId");
        config.require("programId");

        Patient patient = Context.getPatientService().getPatient(Integer.parseInt(config.get("patientId").toString()));
        PatientProgram pp = Context.getProgramWorkflowService().getPatientProgram(Integer.parseInt(config.get("programId").toString()));
        PatientProgramDetails patientDetails = Context.getService(MdrtbDashboardService.class).getPatientProgramDetails(pp);

        model.addAttribute("patientDetails", patientDetails);
    }

}
