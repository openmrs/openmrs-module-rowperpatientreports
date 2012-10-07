package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Concept;



public class ReturnVisitDate extends BasePatientData implements RowPerPatientData {

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
    	setName(concept.getName().getName());
    	setDescription(concept.getDisplayString());
    }


	
    /**
     * @return the dateFormat
     */
    public String getDateFormat() {
    	return dateFormat;
    }


	
    /**
     * @param dateFormat the dateFormat to set
     */
    public void setDateFormat(String dateFormat) {
    	this.dateFormat = dateFormat;
    }
    
    
}
