package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Calendar;
import java.util.Date;

public class PatientAgeInMonths extends BasePatientData implements RowPerPatientData{

	private Date onDate = Calendar.getInstance().getTime();

	
    public PatientAgeInMonths() {
	    super();
	    setName("Age in months");
	    setDescription("Age in months");
    }


	/**
     * @return the onDate
     */
    public Date getOnDate() {
    	return onDate;
    }

	
    /**
     * @param onDate the onDate to set
     */
    public void setOnDate(Date onDate) {
    	this.onDate = onDate;
    }
	
	
}
