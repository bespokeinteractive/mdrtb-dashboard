package org.openmrs.module.mdrtbdashboard.util;

/**
 * Created by daugm
 * Created on 4/8/2017.
 */
public class NameModel {
    String givenName = "";
    String familyName = "";
    String otherNames = "";

    public NameModel(String names){
        String[] nameList = names.split("\\s+");
        for (int i=0; i<nameList.length; i++){
            if (i ==0){
                givenName = nameList[i];
            }
            else if (i==1){
                familyName = nameList[i];
            }
            else{
                otherNames += nameList[i] + " ";
            }
        }
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }


}

