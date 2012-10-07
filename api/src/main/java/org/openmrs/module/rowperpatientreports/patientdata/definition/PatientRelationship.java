package org.openmrs.module.rowperpatientreports.patientdata.definition;



public class PatientRelationship extends BasePatientData implements RowPerPatientData {

	private int relationshipTypeId;
	private String retrievePersonAorB = "A";
	
	private ResultFilter resultFilter;

	public int getRelationshipTypeId() {
		return relationshipTypeId;
	}

	public void setRelationshipTypeId(int relationshipTypeId) {
		this.relationshipTypeId = relationshipTypeId;
	}

	
    /**
     * @return the retrievePersonAorB
     */
    public String getRetrievePersonAorB() {
    	return retrievePersonAorB;
    }

	
    /**
     * @param retrievePersonAorB the retrievePersonAorB to set
     */
    public void setRetrievePersonAorB(String retrievePersonAorB) {
    	this.retrievePersonAorB = retrievePersonAorB;
    }

	
    public ResultFilter getResultFilter() {
    	return resultFilter;
    }

	
    public void setResultFilter(ResultFilter resultFilter) {
    	this.resultFilter = resultFilter;
    }
 
}
