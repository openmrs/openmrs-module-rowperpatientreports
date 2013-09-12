package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Program;



public class RetrievePersonByRelationshipAndByProgram extends BasePatientData implements PersonData {

	private int relationshipTypeId;
	
	private String retrievePersonAorB;
	
	private  Program program;
	
	private ResultFilter filter = null;
	
	public int getRelationshipTypeId() {
		return relationshipTypeId;
	}

	public void setRelationshipTypeId(int relationshipTypeId) {
		this.relationshipTypeId = relationshipTypeId;
	}

    /**
     * @return the personAorB
     */
    public String getRetrievePersonAorB() {
    	return retrievePersonAorB;
    }
	
    /**
     * @param personAorB the personAorB to set
     */
    public void setRetrievePersonAorB(String retrievePersonAorB) {
    	this.retrievePersonAorB = retrievePersonAorB;
    }

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public ResultFilter getFilter() {
		return filter;
	}

	public void setFilter(ResultFilter filter) {
		this.filter = filter;
	}
    
    
    
}
