package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;



public class ObsValueBeforeDateOfOtherDefinition extends BasePatientData implements RowPerPatientData {

	private Concept concept;
	
	private Mapped<RowPerPatientData> dateOfPatientData;
	
	private String dateFormat = "yyyy-MM-dd";
	
	private Concept groupConcept;

	
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
    	if(concept != null)
    	{
    		setName(concept.getName().getName());
    		setDescription(concept.getDisplayString());
    	}
    }


	
    /**
     * @return the dateOfPatientData
     */
    public Mapped<RowPerPatientData> getDateOfPatientData() {
    	return dateOfPatientData;
    }


	
    /**
     * @param dateOfPatientData the dateOfPatientData to set
     */
    public void setDateOfPatientData(DateOfPatientData dateOfPatientData, Map<String, Object> mappings) {
    	this.dateOfPatientData = new Mapped<RowPerPatientData>(dateOfPatientData, mappings);
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


	
    public Concept getGroupConcept() {
    	return groupConcept;
    }


	
    public void setGroupConcept(Concept groupConcept) {
    	this.groupConcept = groupConcept;
    }
    
}
