package org.openmrs.module.mdrtbdashboard.api;

import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.PatientProgram;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.model.LocationCentres;
import org.openmrs.module.mdrtbdashboard.model.LocationCentresAgencies;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;
import org.openmrs.module.mdrtbdashboard.util.DrugTestingResults;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dennis Henry on 12/24/2016.
 */

@Transactional
public interface MdrtbDashboardService
        extends OpenmrsService {
    List<LocationCentres> getCentres();
    LocationCentres getCentresByLocation(Location location);
    List<LocationCentresAgencies> getAgencies();

    PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram);
    PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails);
    Integer getNextTbmuNumberCount(String header);

    List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId);
    List<DrugTestingResults> getDrugSensitivityOutcome(Encounter encounter);
}
