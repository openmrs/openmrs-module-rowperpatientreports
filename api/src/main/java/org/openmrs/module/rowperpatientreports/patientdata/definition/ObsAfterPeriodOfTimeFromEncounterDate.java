package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Form;

/**
 * This PatientData will return the first observation for the patient 
 */
public class ObsAfterPeriodOfTimeFromEncounterDate extends BasePatientData implements RowPerPatientData {
	
	private Concept question;
	
	private List<Form> forms=null;
	
	private List<EncounterType> encounterTypes=null;
	
	private long timeDiff;
	
	
	private ResultFilter filter = null;
	
    
    public ObsAfterPeriodOfTimeFromEncounterDate() {
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

	public List<Form> getForms() {
		return forms;
	}

	public void setForms(List<Form> forms2) {
		this.forms = forms2;
	}


	public List<EncounterType> getEncounterTypes() {
		return encounterTypes;
	}

	public void setEncounterTypes(List<EncounterType> encounterTypes2) {
		this.encounterTypes = encounterTypes2;
	}

	public long getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(long timeDiff) {
		this.timeDiff = timeDiff;
	}

}
