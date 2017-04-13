package org.openmrs.module.mdrtbdashboard.fragment.controller;

import org.openmrs.Patient;
import org.openmrs.PatientProgram;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.model.UserLocation;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.util.LocationModel;
import org.openmrs.ui.framework.SimpleObject;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.fragment.FragmentConfiguration;
import org.openmrs.ui.framework.fragment.FragmentModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Dennis Henry
 * Created on 1/27/2017.
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

    public SimpleObject getLoginLocations(UiUtils ui){
        List<UserLocation> locales = Context.getService(MdrtbService.class).getUserLocations();
        List<LocationModel> models = new ArrayList<LocationModel>();

        for (UserLocation locale : locales){
            LocationModel lm = new LocationModel();
            lm.setId(locale.getLocation().getId());
            lm.setName(locale.getLocation().getName());

            models.add(lm);
        }

        Collections.sort(models, new Comparator<LocationModel>() {
            @Override
            public int compare(LocationModel o1, LocationModel o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return SimpleObject.create("locations", models, "status", "success");
    }
}
