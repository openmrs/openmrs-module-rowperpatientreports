package org.openmrs.module.rowperpatientreports.patientdata.definition;

public class PatientDateOfBirth extends BasePatientData implements RowPerPatientData{

	private String dateFormat = "yyyy-MM-dd";

	
    /**
     * @return the dateFormat
     */
    public String getDateFormat() {
    	return dateFormat;
    }

	
    /**
     * @param dateFormat the dateFormat to set
     */
    public void setDateFormat(String dateFormat) {
    	this.dateFormat = dateFormat;
    }
	
	
}
