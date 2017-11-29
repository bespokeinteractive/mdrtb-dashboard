package org.openmrs.module.mdrtbdashboard.db;

import org.openmrs.*;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import java.util.List;

/**
 * Created by Dennis Henry
 * Created on 12/24/2016.
 */
public interface MdrtbDashboardServiceDAO {
    Integer getNextTbmuNumberCount(String header);
    List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId, List<Location> locations);
    List<MdrtbPatientProgram> getMdrtbPatients(String gender, int age, int rangeAge, int programId, List<Location> locations);
}
