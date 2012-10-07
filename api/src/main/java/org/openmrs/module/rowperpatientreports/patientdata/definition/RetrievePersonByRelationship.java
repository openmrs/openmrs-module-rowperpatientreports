package org.openmrs.module.rowperpatientreports.patientdata.definition;



public class RetrievePersonByRelationship extends BasePatientData implements PersonData {

	private int relationshipTypeId;
	
	private String retrievePersonAorB;

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
}
