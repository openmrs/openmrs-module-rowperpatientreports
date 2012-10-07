package org.openmrs.module.rowperpatientreports.patientdata.definition;



public class PatientAttribute extends BasePatientData implements RowPerPatientData {

	private String attribute;

	
    /**
     * @return the attribute
     */
    public String getAttribute() {
    	return attribute;
    }

	
    /**
     * @param attribute the attribute to set
     */
    public void setAttribute(String attribute) {
    	setName(attribute);
    	setDescription(attribute);
    	this.attribute = attribute;
    }
	
}
