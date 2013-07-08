package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.EncounterType;
import org.openmrs.Form;



public class MostRecentEncounter extends BasePatientData implements RowPerPatientData {

	private List<EncounterType> encounterTypes = new ArrayList<EncounterType>();
	
	private List<Form> forms = new ArrayList<Form>();
	
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
	
    public void addForm(Form form)
	{
		forms.add(form);
	}
}
