package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Concept;

/**
 * This PatientData will return the first observation for the patient (based on the passed in 
 * question concept) with the correct answer which is defined by the answerRequired concept
 */
public class FirstRecordedObservationWithCodedConceptAnswer extends BasePatientData implements RowPerPatientData {
	
	private Concept question;
	private Concept answerRequired;
	
	private ResultFilter filter = null;
	
    /**
     * Constructor sets name and description to Age
     */
    public FirstRecordedObservationWithCodedConceptAnswer() {
	    super();
    }

	public Concept getQuestion() {
		return question;
	}

	public void setQuestion(Concept question) {
		this.question = question;
	}

	public Concept getAnswerRequired() {
		return answerRequired;
	}

	public void setAnswerRequired(Concept answerRequired) {
		this.answerRequired = answerRequired;
	}

	public ResultFilter getFilter() {
		return filter;
	}

	public void setFilter(ResultFilter filter) {
		this.filter = filter;
	}

}
