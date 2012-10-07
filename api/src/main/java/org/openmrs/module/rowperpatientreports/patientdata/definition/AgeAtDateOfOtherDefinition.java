package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Map;

import org.openmrs.module.reporting.evaluation.parameter.Mapped;



/**
 * This PatientData will return the age of the patient based on the date retrieved
 * by another PatientData definition (for example the date of an observation).
 * 
 * The PatientData definition taken in must implement DateOfPatient to ensure the 
 * result from this PatientData is a date.
 */
public class AgeAtDateOfOtherDefinition extends BasePatientData implements RowPerPatientData {
	
	private Mapped<RowPerPatientData> dateOfPatientData;

    /**
     * Constructor sets name and description to Age
     */
    public AgeAtDateOfOtherDefinition() {
	    super();
	    setName("Age");
	    setDescription("Age");
    }



	/**
     * @return the dateOfPatientData
     */
    public Mapped<RowPerPatientData> getDateOfPatientData() {
		return dateOfPatientData;
	}



	public void setDateOfPatientData(Mapped<RowPerPatientData> dateOfPatientData) {
		this.dateOfPatientData = dateOfPatientData;
	}

    /**
     * Sets the DateOfPatientData that once evaluated will return the date
     * that the patients age will be calculated for
     * 
     * @param dateOfPatientData the dateOfPatientData to set
     */
    public void setDateOfPatientData(RowPerPatientData dateOfPatientData, Map<String, Object> mappings) {
    	this.dateOfPatientData = new Mapped<RowPerPatientData>(dateOfPatientData, mappings);
    }
}
