package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Date;

import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

public class FirstStateOfPatient extends BasePatientData implements RowPerPatientData {

	private Program patientProgram;
	private ProgramWorkflow patienProgramWorkflow;
	
	private ResultFilter filter = null;
	
	private boolean includeCompleted = false;
	
	@ConfigurationProperty(group="onDate")
	private Date onDate = new Date();

	public Program getPatientProgram() {
		return patientProgram;
	}

	public void setPatientProgram(Program patientProgram) {
		this.patientProgram = patientProgram;
	}

	public ProgramWorkflow getPatienProgramWorkflow() {
		return patienProgramWorkflow;
	}

	public void setPatienProgramWorkflow(ProgramWorkflow patienProgramWorkflow) {
		this.patienProgramWorkflow = patienProgramWorkflow;
	}

	
    /**
     * @return the filter
     */
    public ResultFilter getFilter() {
    	return filter;
    }

	
    /**
     * @param filter the filter to set
     */
    public void setFilter(ResultFilter filter) {
    	this.filter = filter;
    }

	public Date getOnDate() {
		return onDate;
	}

	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}

	
    public boolean isIncludeCompleted() {
    	return includeCompleted;
    }

	
    public void setIncludeCompleted(boolean includeCompleted) {
    	this.includeCompleted = includeCompleted;
    }
}
