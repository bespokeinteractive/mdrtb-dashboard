package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.Concept;
import org.openmrs.PatientProgram;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by Dennis Henry
 * Created on 9/26/2017.
 */
public class DashboardOverviewFragmentController {
    MdrtbDashboardService dashboardService = Context.getService(MdrtbDashboardService.class);

    public void controller(FragmentModel model,
                           FragmentConfiguration config,
                           UiUtils ui) {

    }

    public SimpleObject updatePatientProgramDetails(@RequestParam(value = "programId") PatientProgram pp,
                                                    @RequestParam(value = "enrolledOn") Date enrolledOn,
                                                    @RequestParam(value = "detailsType") Concept site,
                                                    @RequestParam(value = "detailsClass") Concept confirmation,
                                                    @RequestParam(value = "detailsRemarks",required=false) String detailsRemarks){
        PatientProgramDetails ppd = dashboardService.getPatientProgramDetails(pp);
        ppd.setDiseaseSite(site);
        ppd.setConfirmationSite(confirmation);
        pp.setDateEnrolled(enrolledOn);

        pp = Context.getProgramWorkflowService().savePatientProgram(pp);
        ppd = dashboardService.savePatientProgramDetails(ppd);

        return SimpleObject.create("status", "success", "message", "Patient details successfully updated!");
    }
}
