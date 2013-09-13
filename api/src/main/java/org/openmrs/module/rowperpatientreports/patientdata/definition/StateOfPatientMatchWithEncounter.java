package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.Date;

import org.openmrs.Form;
import org.openmrs.Program;
import org.openmrs.ProgramWorkflow;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;

public class StateOfPatientMatchWithEncounter extends BasePatientData implements RowPerPatientData {

	private Program program;
	private ProgramWorkflow patienProgramWorkflow;
	private ArrayList<Form> forms=new ArrayList<Form>();
	
	private ResultFilter filter = null;
	
	private boolean includeCompleted = false;
	
	@ConfigurationProperty(group="onDate")
	private Date onDate = new Date();

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program patientProgram) {
		this.program = patientProgram;
	}

	public ProgramWorkflow getPatienProgramWorkflow() {
		return patienProgramWorkflow;
	}

	public void setPatienProgramWorkflow(ProgramWorkflow patienProgramWorkflow) {
		this.patienProgramWorkflow = patienProgramWorkflow;
	}

	
	
    public ArrayList<Form> getForms() {
		return forms;
	}

	public void setForms(ArrayList<Form> forms) {
		this.forms = forms;
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
