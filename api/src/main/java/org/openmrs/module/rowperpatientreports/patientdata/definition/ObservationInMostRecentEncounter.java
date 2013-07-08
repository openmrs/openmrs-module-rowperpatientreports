package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Form;



public class ObservationInMostRecentEncounter extends BasePatientData implements RowPerPatientData {

	private List<EncounterType> encounterTypes = new ArrayList<EncounterType>();
	
	private List<Form> forms = new ArrayList<Form>();   

	private Concept observationConcept;
	
	private ResultFilter filter;
	
    public List<EncounterType> getEncounterTypes() {
		return encounterTypes;
	}

	public void setEncounterTypes(List<EncounterType> encounterTypes) {
		this.encounterTypes = encounterTypes;
		
	}
	
	public void addEncounterType(EncounterType encounterType)
	{
		encounterTypes.add(encounterType);
	}
	
	public List<Form> getForms() {
	    	return forms;
	    }

		
	public void setForms(List<Form> forms) {
	    	this.forms = forms;
	    }
	
	public void addform(Form form)
	{
		forms.add(form);
	}

	public Concept getObservationConcept() {
		return observationConcept;
	}

	public void setObservationConcept(Concept observationConcept) {
		this.observationConcept = observationConcept;
	}

	public ResultFilter getFilter() {
		return filter;
	}

	public void setFilter(ResultFilter filter) {
		this.filter = filter;
	}
}
