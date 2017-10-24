package org.openmrs.module.mdrtbdashboard.db.hibernate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.openmrs.*;
import org.openmrs.api.context.Context;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.mdrtb.program.MdrtbPatientProgram;
import org.openmrs.module.mdrtb.service.MdrtbService;
import org.openmrs.module.mdrtbdashboard.api.MdrtbDashboardService;
import org.openmrs.module.mdrtbdashboard.db.MdrtbDashboardServiceDAO;
import org.openmrs.module.mdrtbdashboard.model.*;

import java.util.*;

/**
 * Created by Dennis Henry on 12/24/2016.
 */
public class HibernateMdrtbDashboardServiceDAO implements MdrtbDashboardServiceDAO {
    protected final Log log = LogFactory.getLog(getClass());
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) throws DAOException {
        this.sessionFactory = sessionFactory;
    }
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<LocationCentres> getCentres(){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        List list = criteria.list();
        return list;
    }

    @Override
    public List<LocationCentresAgencies> getAgencies(){
        Criteria criteria = getSession().createCriteria(LocationCentresAgencies.class);
        List list = criteria.list();
        return list;
    }

    @Override
    public List<LocationCentresRegions> getRegions(){
        Criteria criteria = getSession().createCriteria(LocationCentresRegions.class);
        List list = criteria.list();
        return list;
    }

    @Override
    public LocationCentres getCentresByLocation(Location location){
        Criteria criteria = getSession().createCriteria(LocationCentres.class);
        criteria.add(Restrictions.eq("location", location));
        return (LocationCentres) criteria.uniqueResult();
    }

    @Override
    public LocationCentres saveLocationCentres(LocationCentres centre){
        return (LocationCentres)getSession().merge(centre);
    }

    @Override
    public LocationFacilities saveLocationFacilities(LocationFacilities facility){
        return (LocationFacilities)getSession().merge(facility);
    }

    @Override
    public LocationCentresAgencies getAgency(Integer agentId){
        Criteria criteria = getSession().createCriteria(LocationCentresAgencies.class);
        criteria.add(Restrictions.eq("id", agentId));
        return (LocationCentresAgencies) criteria.uniqueResult();
    }

    @Override
    public LocationCentresRegions getRegion(Integer regionId){
        Criteria criteria = getSession().createCriteria(LocationCentresRegions.class);
        criteria.add(Restrictions.eq("id", regionId));
        return (LocationCentresRegions) criteria.uniqueResult();
    }

    @Override
    public PatientProgramDetails getPatientProgramDetails(Integer ppid){
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.add(Restrictions.eq("id", ppid));
        return (PatientProgramDetails) criteria.uniqueResult();
    }

    @Override
    public PatientProgramDetails getPatientProgramDetails(PatientProgram patientProgram){
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.add(Restrictions.eq("patientProgram", patientProgram));
        return (PatientProgramDetails) criteria.uniqueResult();
    }

    @Override
    public Integer getNextTbmuNumberCount(String header) {
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.add(Restrictions.like("tbmuNumber", header, MatchMode.START));
        return criteria.list().size() + 1;
    }

    @Override
    public PatientProgramDetails savePatientProgramDetails(PatientProgramDetails patientProgramDetails) {
        return (PatientProgramDetails)getSession().merge(patientProgramDetails);
    }

    @Override
    public RegimentType getRegimenType(Concept concept, Program program){
        Criteria criteria = getSession().createCriteria(RegimentType.class);
        criteria.add(Restrictions.eq("concept", concept));
        criteria.add(Restrictions.eq("program", program));
        criteria.add(Restrictions.eq("voided", 0));

        return (RegimentType)criteria.uniqueResult();
    }

    @Override
    public List<RegimentType> getRegimenTypes(Concept concept, Program program){
        Criteria criteria = getSession().createCriteria(RegimentType.class);
        criteria.add(Restrictions.eq("concept", concept));
        criteria.add(Restrictions.eq("program", program));
        criteria.add(Restrictions.eq("voided", 0));

        return criteria.list();
    }

    @Override
    public List<PatientProgramRegimen> getPatientProgramRegimens(PatientProgramDetails pd, Boolean active){
        Criteria criteria = getSession().createCriteria(PatientProgramRegimen.class);
        criteria.add(Restrictions.eq("programDetails", pd));
        if (active){
            criteria.add(Restrictions.isNull("finishedOn"));
        }

        return criteria.list();
    }

    @Override
    public List<VisitTypes> getVisitTypes(Program program, Boolean initial, Boolean finals, Boolean voided){
        Criteria criteria = getSession().createCriteria(VisitTypes.class);
        criteria.add(Restrictions.eq("program", program));
        if (initial != null){
            criteria.add(Restrictions.eq("initialVisit", initial));
        }
        if (finals != null){
            criteria.add(Restrictions.eq("finalVisit", finals));
        }
        criteria.add(Restrictions.eq("voided", voided));

        return criteria.list();
    }

    @Override
    public VisitTypes getVisitType(Program program, String name){
        Criteria criteria = getSession().createCriteria(VisitTypes.class);
        criteria.add(Restrictions.eq("program", program));
        criteria.add(Restrictions.eq("name", name));

        return (VisitTypes)criteria.uniqueResult();
    }

    @Override
    public VisitTypes getVisitType(Integer id){
        Criteria criteria = getSession().createCriteria(VisitTypes.class);
        criteria.add(Restrictions.eq("id", id));

        return (VisitTypes)criteria.uniqueResult();
    }

    @Override
    public PatientProgramVisits getPatientProgramVisit(PatientProgram patientProgram, VisitTypes visitType){
        Criteria criteria = getSession().createCriteria(PatientProgramVisits.class);
        criteria.add(Restrictions.eq("patientProgram", patientProgram));
        criteria.add(Restrictions.eq("visitType", visitType));

        return (PatientProgramVisits)criteria.uniqueResult();
    }

    @Override
    public PatientProgramVisits getPatientProgramVisit(Encounter encounter){
        Criteria criteria = getSession().createCriteria(PatientProgramVisits.class);
        criteria.add(Restrictions.eq("encounter", encounter));

        return (PatientProgramVisits)criteria.uniqueResult();
    }

    @Override
    public List<PatientProgramVisits> getPatientProgramVisits(PatientProgram patientProgram){
        Criteria criteria = getSession().createCriteria(PatientProgramVisits.class);
        criteria.add(Restrictions.eq("patientProgram", patientProgram));

        return criteria.list();
    }

    @Override
    public PatientProgramRegimen savePatientProgramRegimen(PatientProgramRegimen patientProgramRegimen){
        return (PatientProgramRegimen)getSession().merge(patientProgramRegimen);
    }

    @Override
    public PatientProgramDetails saveParentProgramOutcome(PatientProgramDetails ppd, Concept outcome, Date completedOn){
        while (ppd.getReferringProgram() != null){
            ppd = Context.getService(MdrtbDashboardService.class).getPatientProgramDetails(ppd.getReferringProgram());
            ppd.setOutcome(outcome);

            PatientProgram pp = ppd.getPatientProgram();
            pp.setOutcome(outcome);
            pp.setDateCompleted(completedOn);

            Context.getProgramWorkflowService().savePatientProgram(pp);
            Context.getService(MdrtbDashboardService.class).savePatientProgramDetails(ppd);
        }

        return ppd;
    }

    @Override
    public PatientProgramVisits savePatientProgramVisits(PatientProgramVisits patientProgramVisit){
        return (PatientProgramVisits)getSession().merge(patientProgramVisit);
    }

    @Override
    public LocationFacilities getFacilityById(Integer facilityId){
        Criteria criteria = getSession().createCriteria(LocationFacilities.class);
        criteria.add(Restrictions.eq("id", facilityId));
        return (LocationFacilities)criteria.uniqueResult();
    }

    @Override
    public List<LocationFacilities> getFacilities(Location location, String status){
        Criteria criteria = getSession().createCriteria(LocationFacilities.class);
        criteria.add(Restrictions.eq("location", location));
        if (StringUtils.isNotBlank(status)){
            criteria.add(Restrictions.eq("status", status));
        }
        return criteria.list();
    }

    @Override
    public  List<PatientProgramDetails> getPatientsFromDetails(Location location, Date startDate, Date endDate, LocationFacilities facility){
        Criteria criteria = getSession().createCriteria(PatientProgramDetails.class);
        criteria.createAlias("patientProgram", "pp");
        criteria.add(Restrictions.eq("pp.location", location));
        criteria.add(Restrictions.le("pp.voided", false));
        criteria.add(Restrictions.ge("pp.dateEnrolled", startDate));
        criteria.add(Restrictions.le("pp.dateEnrolled", endDate));
        if (facility != null){
            criteria.add(Restrictions.eq("facility", facility));
        }

        return criteria.list();
    }

    @Override
    public PatientProgramTransfers savePatientProgramTransfers(PatientProgramTransfers patientProgramTransfers){
        return (PatientProgramTransfers)getSession().merge(patientProgramTransfers);
    }

    @Override
    public PatientProgramTransfers getPatientProgramTransfers(Integer transferId){
        Criteria criteria = getSession().createCriteria(PatientProgramTransfers.class);
        criteria.add(Restrictions.eq("id", transferId));
        return (PatientProgramTransfers)criteria.uniqueResult();
    }

    @Override
    public List<PatientProgramTransfers> getPatientProgramTransfers(Location location, Boolean status){
        Criteria criteria = getSession().createCriteria(PatientProgramTransfers.class);
        criteria.add(Restrictions.eq("location", location));
        criteria.add(Restrictions.eq("voided", false));
        if (status != null){
            criteria.add(Restrictions.eq("processed", status));
        }

        return criteria.list();
    }

    @Override
    public List<PatientProgramTransfers> getActivePatientTransfers(PatientProgram patientProgram){
        Criteria criteria = getSession().createCriteria(PatientProgramTransfers.class);
        criteria.add(Restrictions.eq("patientProgram", patientProgram));
        criteria.add(Restrictions.eq("processed", false));
        criteria.add(Restrictions.eq("voided", false));

        return criteria.list();
    }

    @Override
    public List<MdrtbPatientProgram> getMdrtbPatients(String nameOrIdentifier, String gender, int age, int rangeAge, String lastDayOfVisit, int lastVisit, int programId, List<Location> locations){
        Vector search = new Vector();

        String[] searchSplit = nameOrIdentifier.split("\\s+");
        String hql = "SELECT DISTINCT p.patient_id,pi.identifier,pn.given_name ,pn.middle_name ,pn.family_name ,ps.gender,ps.birthdate,ps.dead,0, pn.person_name_id ,EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) age FROM patient p INNER JOIN person ps ON p.patient_id = ps.person_id INNER JOIN patient_identifier PI ON p.patient_id = pi.patient_id INNER JOIN person_name pn ON p.patient_id = pn.person_id INNER JOIN encounter en ON p.patient_id = en.patient_id LEFT OUTER JOIN patient_program pp ON pp.patient_id=p.patient_id LEFT OUTER JOIN patient_program_details ppd ON pp.patient_program_id=ppd.patient_program_id WHERE (ppd.tbmu_number like \'%" + nameOrIdentifier + "%\'";

        if(searchSplit.length == 1) {
            String[] query = searchSplit;
            int l = searchSplit.length;

            for(int i$ = 0; i$ < l; ++i$) {
                String obj = query[i$];
                hql = hql + "OR pn.given_name like \'%" + obj + "%\' " + "OR pn.middle_name like \'%" + obj + "%\' " + "OR pn.family_name like \'%" + obj + "%\' ";
            }
        } else if(searchSplit.length == 2) {
            hql = hql + "OR pn.given_name like \'%" + searchSplit[0] + "%\' " + "AND (pn.middle_name like \'%" + searchSplit[1] + "%\' " + "OR pn.family_name like \'%" + searchSplit[1] + "%\') ";
        } else if(searchSplit.length == 3) {
            hql = hql + "OR pn.given_name like \'%" + searchSplit[0] + "%\' " + "AND pn.middle_name like \'%" + searchSplit[2] + "%\' " + "AND pn.family_name like \'%" + searchSplit[1] + "%\'";
        }

        hql += ") ";

        if(StringUtils.isNotBlank(gender)) {
            hql = hql + " AND ps.gender = \'" + gender + "\' ";
        }

        if(age > 0) {
            hql = hql + " AND EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) BETWEEN " + (age - rangeAge) + " AND " + (age + rangeAge) + " ";
        }

        if(StringUtils.isNotBlank(lastDayOfVisit)) {
            hql = hql + " AND (DATE_FORMAT(DATE(en.encounter_datetime),\'%Y/%m/%d\') = \'" + lastDayOfVisit + "\')";
        }

        if(lastVisit > 0) {
            hql = hql + " AND (DATEDIFF(NOW(), en.date_created) <= " + lastVisit + ")";
        }

        if (locations.size()>0){
            String oLocation = "0";
            for (Location location: locations){
                oLocation += ","+location.getId();
            }

            hql += " AND pp.location_id IN (" + oLocation + ")";
        }

        hql += " ORDER BY pn.given_name ASC, pn.family_name ASC, pn.middle_name ASC LIMIT 0, 50";

        SQLQuery sqlquery = this.sessionFactory.getCurrentSession().createSQLQuery(hql);
        List listquery = sqlquery.list();
        if(CollectionUtils.isNotEmpty(listquery)) {
            Iterator iterator = listquery.iterator();

            while(iterator.hasNext()) {
                Object o = iterator.next();
                Object[] obss = (Object[])((Object[])o);
                if(obss != null && obss.length > 0) {
                    Person person = new Person((Integer)obss[0]);
                    PersonName personName = new PersonName((Integer)obss[9]);
                    personName.setGivenName((String)obss[2]);
                    personName.setMiddleName((String)obss[3]);
                    personName.setFamilyName((String)obss[4]);
                    personName.setPerson(person);
                    HashSet names = new HashSet();
                    names.add(personName);
                    person.setNames(names);
                    Patient patient = new Patient(person);
                    PatientIdentifier patientIdentifier = new PatientIdentifier();
                    patientIdentifier.setPatient(patient);
                    patientIdentifier.setIdentifier((String)obss[1]);
                    HashSet identifier = new HashSet();
                    identifier.add(patientIdentifier);
                    patient.setIdentifiers(identifier);
                    patient.setGender((String)obss[5]);
                    patient.setBirthdate((Date)obss[6]);
                    if(obss[7] != null) {
                        if(obss[7].toString().equals("1")) {
                            patient.setDead(Boolean.valueOf(true));
                        } else if(obss[7].toString().equals("0")) {
                            patient.setDead(Boolean.valueOf(false));
                        }
                    }

                    if(obss[8] != null) {
                        if(obss[8].toString().equals("1")) {
                            patient.setVoided(Boolean.valueOf(true));
                        } else if(obss[8].toString().equals("0")) {
                            patient.setVoided(Boolean.valueOf(false));
                        }
                    }

                    MdrtbPatientProgram pp = Context.getService(MdrtbService.class).getMostRecentMdrtbPatientProgram(patient);
                    if (pp == null){
                        continue;
                    }
                    else if (programId == -1){
                        //Return only active patients, either MDRTB or TB
                        if (pp.getPatientProgram() != null && pp.getActive()){
                            search.add(pp);
                        }
                    }
                    else if (programId > 0){
                        if (pp.getPatientProgram() != null && pp.getActive() && pp.getPatientProgram().getProgram() == Context.getProgramWorkflowService().getProgram(programId)){
                            search.add(pp);
                        }
                    }
                    else{
                        search.add(pp);
                    }
                }
            }
        }

        //System.out.println(hql);

        return search;
    }

    @Override
    public List<MdrtbPatientProgram> getMdrtbPatients(String gender, int age, int rangeAge, int programId, List<Location> locations){
        Vector search = new Vector();

        String[] searchSplit = "".split("\\s+");
        String hql = "SELECT p.patient_id,pi.identifier,pn.given_name ,pn.middle_name ,pn.family_name ,ps.gender,ps.birthdate,ps.dead,pp.patient_program_id, pn.person_name_id, EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) age FROM patient_program pp INNER JOIN patient p ON pp.patient_id=p.patient_id INNER JOIN person ps ON p.patient_id = ps.person_id INNER JOIN patient_identifier PI ON p.patient_id = pi.patient_id INNER JOIN person_name pn ON p.patient_id = pn.person_id WHERE p.patient_id>0 ";
        if(StringUtils.isNotBlank(gender)) {
            hql = hql + " AND ps.gender = \'" + gender + "\' ";
        }
        if(age > 0) {
            hql = hql + " AND EXTRACT(YEAR FROM (FROM_DAYS(DATEDIFF(NOW(),ps.birthdate)))) BETWEEN " + (age - rangeAge) + " AND " + (age + rangeAge) + " ";
        }

        if (locations.size()>0){
            String oLocation = "0";
            for (Location location: locations){
                oLocation += ","+location.getId();
            }

            hql += " AND pp.location_id IN (" + oLocation + ")";
        }

        hql += " ORDER BY pn.given_name ASC, pn.family_name ASC, pn.middle_name ASC";

        SQLQuery sqlquery = this.sessionFactory.getCurrentSession().createSQLQuery(hql);
        List listquery = sqlquery.list();
        if(CollectionUtils.isNotEmpty(listquery)) {
            Iterator iterator = listquery.iterator();

            while(iterator.hasNext()) {
                Object o = iterator.next();
                Object[] obj = (Object[])((Object[])o);
                if(obj != null && obj.length > 0) {
                    MdrtbPatientProgram pp = Context.getService(MdrtbService.class).getMdrtbPatientProgram((Integer)obj[8]);
                    if (pp == null){
                        continue;
                    }

                    if (programId == -1){
                        if (pp.getPatientProgram() != null && pp.getActive()){
                            search.add(pp);
                        }
                    }
                    else if (programId > 0){
                        if (pp.getPatientProgram() != null && pp.getActive() && pp.getPatientProgram().getProgram() == Context.getProgramWorkflowService().getProgram(programId)){
                            search.add(pp);
                        }
                    }
                    else{
                        search.add(pp);
                    }
                }
            }
        }

        return search;
    }
}
