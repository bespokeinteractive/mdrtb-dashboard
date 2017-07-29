package org.openmrs.module.mdrtbdashboard.db;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.PatientProgram;
import org.openmrs.Program;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.model.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Dennis Henry
 * Created on 12/24/2016.
 */
public interface MdrtbDashboardServiceDAO {
    List<LocationCentres> getCentres();
    List<LocationCentresAgencies> getAgencies();
    List<LocationCentresRegions> getRegions();

    LocationCentres getCentresByLocation(Location location);
    LocationCentres saveLocationCentres(LocationCentres centre);

    LocationCentresAgencies getAgency(Integer agentId);
    LocationCentresRegions getRegion(Integer regionId);

    PatientProgramDetails getPatientProgramDetails(Integer ppid);
    PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails);
    PatientProgramRegimen savePatientProgramRegimen(PatientProgramRegimen patientProgramRegimen);

    RegimentType getRegimenType(Concept concept, Program program);
    List<RegimentType> getRegimenTypes(Concept concept, Program program);
    List<PatientProgramRegimen> getPatientProgramRegimens(PatientProgramDetails pd, Boolean active);

    PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram);
    Integer getNextTbmuNumberCount(String header);
    LocationFacilities getFacilityById(Integer facilityId);

    List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId, List<Location> locations);
    List<MdrtbPatientProgram> getMdrtbPatients(String gender, int age, int rangeAge, int programId, List<Location> locations);

    List<LocationFacilities> getFacilities(Location location, String status);
    List<PatientProgramDetails> getPatientsFromDetails(Location location, Date startDate, Date endDate, LocationFacilities facility);
}
