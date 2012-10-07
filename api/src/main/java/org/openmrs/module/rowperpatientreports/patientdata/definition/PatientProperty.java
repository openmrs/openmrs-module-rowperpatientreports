package org.openmrs.module.rowperpatientreports.patientdata.definition;



public class PatientProperty extends BasePatientData implements RowPerPatientData {

	private String property;

	
    public PatientProperty(String property) {
	    super();
	    setProperty(property);
    }


	public PatientProperty() {
	    super();
    }



	/**
     * @return the property
     */
    public String getProperty() {
    	return property;
    }

	
    /**
     * @param property the property to set
     */
    public void setProperty(String property) {
    	this.property = property;
    	setName(property);
    	setDescription(property);
    	
    }
	
}
