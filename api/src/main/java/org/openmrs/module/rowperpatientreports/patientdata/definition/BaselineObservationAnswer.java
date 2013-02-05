package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;



public class BaselineObservationAnswer extends BasePatientData implements RowPerPatientData, DateOfPatientData {

	private Concept answer;
	
	private List<Concept> questions;
	
	private Mapped<RowPerPatientData> dateOfPatientData;
	
	private int before;
	
	private int after;
	
	private int offset;
	
	private int offsetType;
	
	private String dateFormat = "yyyy-MM-dd";
	
	@ConfigurationProperty
	private Date startDate;
	
	@ConfigurationProperty
	private Date endDate;
	

    /**
     * @return the concept
     */
    public Concept getAnswer() {
    	return answer;
    }

	
    /**
     * @param concept the concept to set
     */
    public void setAnswer(Concept answer) {
    	this.answer = answer;
    	if(answer != null)
    	{
    		setName(answer.getName().getName());
    		setDescription(answer.getDisplayString());
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


	
    public List<Concept> getQuestions() {
    	return questions;
    }

    public void setQuestions(List<Concept> questions) {
    	this.questions = questions;
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


	
    public Date getStartDate() {
    	return startDate;
    }


	
    public void setStartDate(Date startDate) {
    	this.startDate = startDate;
    }


	
    public Date getEndDate() {
    	return endDate;
    }


	
    public void setEndDate(Date endDate) {
    	this.endDate = endDate;
    }

    
}
