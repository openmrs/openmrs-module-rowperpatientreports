package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;

import org.openmrs.Concept;
import org.openmrs.Form;

/**
 * This PatientData will return the first observation for the patient 
 */
public class FirstRecordedObservation extends BasePatientData implements RowPerPatientData {
	
	private Concept question;
	
	
	private ResultFilter filter = null;
	
    
    public FirstRecordedObservation() {
	    super();
    }

	public Concept getQuestion() {
		return question;
	}

	public void setQuestion(Concept question) {
		this.question = question;
	}
	
	
	public ResultFilter getFilter() {
		return filter;
	}

	public void setFilter(ResultFilter filter) {
		this.filter = filter;
	}

}
