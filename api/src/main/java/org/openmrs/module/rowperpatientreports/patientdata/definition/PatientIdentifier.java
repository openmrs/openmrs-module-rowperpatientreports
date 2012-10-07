package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.PatientIdentifierType;



public class PatientIdentifier extends BasePatientData implements RowPerPatientData {

	private PatientIdentifierType identifierType;

	
    public PatientIdentifier(PatientIdentifierType identifierType) {
	    super();
	    setIdentifierType(identifierType);
    }

	public PatientIdentifier() {
	    super();
    }



	/**
     * @return the identifierType
     */
    public PatientIdentifierType getIdentifierType() {
    	return identifierType;
    }

	
    /**
     * @param identifierType the identifierType to set
     */
    public void setIdentifierType(PatientIdentifierType identifierType) {
    	this.identifierType = identifierType;
    	if(identifierType != null)
    	{
    		setName(identifierType.getName());
    		setDescription(identifierType.getDescription());
    	}
    }

	
}
