package org.openmrs.module.mdrtbdashboard.util;

/**
 * Created by Dennis Henry
 * Created on 3/22/2017.
 */
public class DrugTestingResults {
    private String drugName;
    private String testResult;

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }
}
