package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Date;

import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;




public class DateOfProgramEnrolment extends BasePatientData implements DateOfPatientData {

	private int ProgramId;
	
	private String dateFormat = "yyyy-MM-dd";
	
	private boolean returnEarliest = false;
	
	@ConfigurationProperty
	private Date startDate = null;
	
	@ConfigurationProperty
	private Date endDate = null;


    public void setDateFormat(String dateFormat) {
	    this.dateFormat = dateFormat;
    }


	public String getDateFormat() {
	    return dateFormat;
    }


	public int getProgramId() {
		return ProgramId;
	}


	public void setProgramId(int programId) {
		ProgramId = programId;
	}
	
    public boolean isReturnEarliest() {
    	return returnEarliest;
    }
	
    public void setReturnEarliest(boolean returnEarliest) {
    	this.returnEarliest = returnEarliest;
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
