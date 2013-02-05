package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Date;
import java.util.Map;

import org.openmrs.Program;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;



public class BaselineProgramEnrollment extends BasePatientData implements RowPerPatientData, DateOfPatientData {

	private Mapped<RowPerPatientData> dateOfPatientData;
	
	private int before;
	
	private int after;
	
	private int offset = 0;
	
	private int offsetType;
	
	private String dateFormat = "yyyy-MM-dd";
	
	private Program program;
	
	@ConfigurationProperty
	private Date startDate;
	
	@ConfigurationProperty
	private Date endDate;

    /**
     * @return the dateOfPatientData
     */
    public Mapped<RowPerPatientData> getDateOfPatientData() {
    	return dateOfPatientData;
    }


	
    /**
     * @param dateOfPatientData the dateOfPatientData to set
     */
    public void setDateOfPatientData(DateOfPatientData dateOfPatientData, Map<String, Object> mappings) {
    	this.dateOfPatientData = new Mapped<RowPerPatientData>(dateOfPatientData, mappings);
    }


	
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


	
    public int getBefore() {
    	return before;
    }


	
    public void setBefore(int before) {
    	this.before = before;
    }


	
    public int getAfter() {
    	return after;
    }


	
    public void setAfter(int after) {
    	this.after = after;
    }
    
    public int getOffset() {
    	return offset;
    }

    public int getOffsetType() {
    	return offsetType;
    }

	
    public void setOffset(int offset, int offsetType) {
    	this.offset = offset;
    	this.offsetType = offsetType;
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
	
    public Program getProgram() {
    	return program;
    }

    public void setProgram(Program program) {
    	this.program = program;
    }  
}
