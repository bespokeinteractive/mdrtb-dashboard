package org.openmrs.module.mdrtbdashboard;

import org.openmrs.Encounter;
import org.openmrs.api.context.Context;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtb.model.PatientProgramVisits;

/**
 * Created by Dennis Henry
 * Created on 8/3/2017.
 */
public class VisitDetailsWrapper {
    private Encounter encounter;
    private String wrapperVisitName;

    public VisitDetailsWrapper(Encounter encounter){
        this.encounter = encounter;

        PatientProgramVisits ppv = Context.getService(MdrtbService.class).getPatientProgramVisit(encounter);
        if (ppv != null){
            wrapperVisitName = ppv.getVisitType().getName();
        }
        else {
            wrapperVisitName = encounter.getEncounterType().getDescription();
        }
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }

    public String getWrapperVisitName() {
        return wrapperVisitName;
    }

    public void setWrapperVisitName(String wrapperVisitName) {
        this.wrapperVisitName = wrapperVisitName;
    }
}
