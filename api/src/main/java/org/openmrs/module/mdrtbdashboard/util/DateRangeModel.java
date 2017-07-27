package org.openmrs.module.mdrtbdashboard.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Dennis Henry
 * Created on 5/9/2017.
 */
public class DateRangeModel {
    Integer quarter;
    Integer year;
    Date startDate;
    Date endDate;

    public DateRangeModel(Integer qtr, Integer year){
        this.year = year;
        this.quarter = qtr;

        Integer mnth = (qtr * 3) - 3;
        Calendar cal = Calendar.getInstance();
        cal.set(year, mnth, 1,0,0);
        this.startDate = cal.getTime();

        cal.add(Calendar.MONTH, 3);
        cal.add(Calendar.DATE, -1);
        this.endDate = cal.getTime();
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
