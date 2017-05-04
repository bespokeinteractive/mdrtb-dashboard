package org.openmrs.module.mdrtbdashboard.api;

import org.openmrs.Encounter;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.PatientProgram;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.model.*;
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
    List<LocationCentres> getCentres();
    List<LocationCentresAgencies> getAgencies();
    List<LocationCentresRegions> getRegions();

    LocationCentres getCentresByLocation(Location location);
    LocationCentres saveLocationCentres(LocationCentres centre);

    LocationCentresAgencies getAgency(Integer agentId);
    LocationCentresRegions getRegion(Integer regionId);

    PatientProgramDetails getPatientProgramDetails(PatientProgram pp);
    PatientProgramDetails getPatientProgramDetails(MdrtbPatientProgram mpp);

    PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails);
    Integer getNextTbmuNumberCount(String header);

    List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId, List<Location> locations);
    List<DrugTestingResults> getDrugSensitivityOutcome(Encounter encounter);

    List<LocationFacilities> getFacilities(Location location, String status);
}
