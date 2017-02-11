package org.openmrs.module.mdrtbdashboard.db;

import org.openmrs.Location;
import org.openmrs.PatientProgram;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtbdashboard.model.LocationCentres;
import org.openmrs.module.mdrtbdashboard.model.LocationCentresAgencies;
import org.openmrs.module.mdrtbdashboard.model.PatientProgramDetails;

import java.util.List;

/**
 * Created by Dennis Henry on 12/24/2016.
 */
public interface MdrtbDashboardServiceDAO {
    List<LocationCentres> getCentres();
    LocationCentres getCentresByLocation(Location location);
    List<LocationCentresAgencies> getAgencies();

    PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram);
    PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails);
    Integer getNextTbmuNumberCount(String header);

    List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId);
}
