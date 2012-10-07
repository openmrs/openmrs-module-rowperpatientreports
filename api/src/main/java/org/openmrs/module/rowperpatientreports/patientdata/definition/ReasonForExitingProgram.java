package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Date;

import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;

public class ReasonForExitingProgram extends BasePatientData implements RowPerPatientData {

	private ResultFilter filter = null;
	
	private Program patientProgram;
	
	private ProgramWorkflow reasonWorkflow;
	
	private Date onDate = null;

	
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


	public Program getPatientProgram() {
		return patientProgram;
	}


	public void setPatientProgram(Program patientProgram) {
		this.patientProgram = patientProgram;
	}

	
	public ProgramWorkflow getReasonWorkflow() {
		return reasonWorkflow;
	}


	public void setReasonWorkflow(ProgramWorkflow reasonWorkflow) {
		this.reasonWorkflow = reasonWorkflow;
	}

	public Date getOnDate() {
		return onDate;
	}

	public void setOnDate(Date onDate) {
		this.onDate = onDate;
	}
}
