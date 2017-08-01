package org.openmrs.module.mdrtbdashboard.api;

import org.openmrs.*;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.model.*;
import org.openmrs.module.mdrtbdashboard.util.DrugTestingResults;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    PatientProgramDetails getPatientProgramDetails(Integer ppid);
    PatientProgramDetails getPatientProgramDetails(PatientProgram pp);
    PatientProgramDetails getPatientProgramDetails(MdrtbPatientProgram mpp);

    PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails);
    PatientProgramRegimen savePatientProgramRegimen(PatientProgramRegimen patientProgramRegimen);
    PatientProgramDetails saveParentProgramOutcome(PatientProgramDetails ppd, Concept outcome, Date completedOn);

    List<PatientProgramRegimen> getPatientProgramRegimens(PatientProgramDetails pd, Boolean active);
    List<RegimentType> getRegimenTypes(Concept concept, Program program);
    RegimentType getRegimenType(Concept concept, Program program);

    Integer getNextTbmuNumberCount(String header);
    LocationFacilities getFacilityById(Integer facilityId);

    List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId, List<Location> locations);
    List<MdrtbPatientProgram> getMdrtbPatients(String gender, int age, int rangeAge, int programId, List<Location> locations);

    List<DrugTestingResults> getDrugSensitivityOutcome(Encounter encounter);
    List<PatientProgramDetails> getPatientsFromDetails(Location location, Date startDate, Date endDate, LocationFacilities facility);

    List<LocationFacilities> getFacilities(Location location, String status);
}
