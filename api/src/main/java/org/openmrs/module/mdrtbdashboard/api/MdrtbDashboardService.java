package org.openmrs.module.mdrtbdashboard.api;

import org.openmrs.*;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.util.DrugTestingResults;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Created by Dennis Henry
 * Created on 12/24/2016.
 */

@Transactional
public interface MdrtbDashboardService
        extends OpenmrsService {
    Integer getNextTbmuNumberCount(String header);
    List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId, List<Location> locations);
    List<MdrtbPatientProgram> getMdrtbPatients(String gender, int age, int rangeAge, int programId, List<Location> locations);
    List<DrugTestingResults> getDrugSensitivityOutcome(Encounter encounter);


}
