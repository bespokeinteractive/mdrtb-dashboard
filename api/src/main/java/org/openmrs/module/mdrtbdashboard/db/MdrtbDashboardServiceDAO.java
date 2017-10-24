package org.openmrs.module.mdrtbdashboard.db;

import org.openmrs.*;
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
    PatientProgramDetails saveParentProgramOutcome(PatientProgramDetails ppd, Concept outcome, Date completedOn);
    PatientProgramVisits savePatientProgramVisits(PatientProgramVisits patientProgramVisit);

    PatientProgramTransfers savePatientProgramTransfers(PatientProgramTransfers patientProgramTransfers);
    PatientProgramTransfers getPatientProgramTransfers(Integer transferId);
    List<PatientProgramTransfers> getPatientProgramTransfers(Location location, Boolean status);
    List<PatientProgramTransfers> getActivePatientTransfers(PatientProgram patientProgram);

    RegimentType getRegimenType(Concept concept, Program program);
    List<RegimentType> getRegimenTypes(Concept concept, Program program);
    List<PatientProgramRegimen> getPatientProgramRegimens(PatientProgramDetails pd, Boolean active);

    PatientProgramVisits getPatientProgramVisit(PatientProgram patientProgram, VisitTypes visitType);
    PatientProgramVisits getPatientProgramVisit(Encounter encounter);
    List<PatientProgramVisits> getPatientProgramVisits(PatientProgram patientProgram);

    List<VisitTypes> getVisitTypes(Program program, Boolean initial, Boolean finals, Boolean voided);
    VisitTypes getVisitType(Program program, String name);
    VisitTypes getVisitType(Integer id);

    PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram);
    Integer getNextTbmuNumberCount(String header);
    LocationFacilities getFacilityById(Integer facilityId);

    List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId, List<Location> locations);
    List<MdrtbPatientProgram> getMdrtbPatients(String gender, int age, int rangeAge, int programId, List<Location> locations);

    List<LocationFacilities> getFacilities(Location location, String status);
    List<PatientProgramDetails> getPatientsFromDetails(Location location, Date startDate, Date endDate, LocationFacilities facility);
}
