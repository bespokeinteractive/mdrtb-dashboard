package org.openmrs.module.mdrtbdashboard.db;

import org.openmrs.Location;
import org.openmrs.PatientProgram;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.model.*;

import java.util.List;

/**
 * Created by Dennis Henry on 12/24/2016.
 */
public interface MdrtbDashboardServiceDAO {
    List<LocationCentres> getCentres();
    List<LocationCentresAgencies> getAgencies();
    List<LocationCentresRegions> getRegions();

    LocationCentres getCentresByLocation(Location location);
    LocationCentres saveLocationCentres(LocationCentres centre);

    LocationCentresAgencies getAgency(Integer agentId);
    LocationCentresRegions getRegion(Integer regionId);

    PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram);
    PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails);
    Integer getNextTbmuNumberCount(String header);

    List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId, List<Location> locations);
    List<LocationFacilities> getFacilities(Location location, String status);
}
