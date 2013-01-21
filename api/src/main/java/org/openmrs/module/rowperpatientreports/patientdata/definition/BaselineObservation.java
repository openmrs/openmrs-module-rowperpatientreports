package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;



public class BaselineObservation extends BasePatientData implements RowPerPatientData, DateOfPatientData {

private Concept concept;
	
	private Mapped<RowPerPatientData> dateOfPatientData;
	
	private int before;
	
	private int after;
	
	private int offset = 0;
	
	private int offsetType;
	
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


	
    public int getBefore() {
    	return before;
    }


	
    public void setBefore(int before) {
    	this.before = before;
    }


	
    public int getAfter() {
    	return after;
    }


	
    public void setAfter(int after) {
    	this.after = after;
    }


	
    public Concept getGroupConcept() {
    	return groupConcept;
    }


	
    public void setGroupConcept(Concept groupConcept) {
    	this.groupConcept = groupConcept;
    }
    
    public int getOffset() {
    	return offset;
    }

    public int getOffsetType() {
    	return offsetType;
    }

	
    public void setOffset(int offset, int offsetType) {
    	this.offset = offset;
    	this.offsetType = offsetType;
    }

    
}
