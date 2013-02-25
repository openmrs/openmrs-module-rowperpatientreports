package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;



public class ObsValueInSameEncounterAsOtherDefinition extends BasePatientData implements RowPerPatientData {

	private Concept concept;
	
	private Mapped<RowPerPatientData> patientData;
	
	private String dateFormat = "yyyy-MM-dd";
	
	private String outputWhenObsPresentButNoValue = "";

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
    public Mapped<RowPerPatientData> getPatientData() {
    	return patientData;
    }
	
    /**
     * @param dateOfPatientData the dateOfPatientData to set
     */
    public void setDateOfPatientData(RowPerPatientData patientData, Map<String, Object> mappings) {
    	this.patientData = new Mapped<RowPerPatientData>(patientData, mappings);
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

    public String getOutputWhenObsPresentButNoValue() {
    	return outputWhenObsPresentButNoValue;
    }
	
    public void setOutputWhenObsPresentButNoValue(String outputWhenObsPresentButNoValue) {
    	this.outputWhenObsPresentButNoValue = outputWhenObsPresentButNoValue;
    }
}
