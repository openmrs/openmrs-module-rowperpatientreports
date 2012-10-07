package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Concept;



public class DateOfWorkflowStateChange extends BasePatientData implements DateOfPatientData {

	private Concept concept;
	
	private String dateFormat = "yyyy-MM-dd";

	
    /**
     * @return the concept
     */
    public Concept getConcept() {
    	return concept;
    }

	
    /**
     * @param concept the concept to set
     */
    public void setConcept(Concept concept) {
    	this.concept = concept;
    }

    public void setDateFormat(String dateFormat) {
	    this.dateFormat = dateFormat;
    }


	public String getDateFormat() {
	    return dateFormat;
    }
}
