package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.List;

import org.openmrs.ProgramWorkflow;

/**
 * 
 * @author Lara Kellett
 */
public class FullHistoryOfProgramWorkflowStates extends BasePatientData implements RowPerPatientData {
	
	private List<ProgramWorkflow> workflows;
	
	private String locale = "en";
	
	public List<ProgramWorkflow> getWorkflows() {
		return workflows;
	}
	
	public void setWorkflows(List<ProgramWorkflow> workflows) {
		this.workflows = workflows;
		
	}
	
	public void addWorkflow(ProgramWorkflow workflow) {
		workflows.add(workflow);
	}

	
    public String getLocale() {
    	return locale;
    }

	
    public void setLocale(String locale) {
    	this.locale = locale;
    }
	
}
