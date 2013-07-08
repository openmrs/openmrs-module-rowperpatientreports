package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.EncounterType;
import org.openmrs.Form;

public class RecentEncounter extends BasePatientData implements RowPerPatientData {

	private ResultFilter filter = null;
	private List<EncounterType> encounterTypes=new ArrayList<EncounterType>();
	
	private List<Form> forms=new ArrayList<Form>();
    
    /**
     * @return the filter
     */
    public ResultFilter getFilter() {
    	return filter;
    }

	
    /**
     * @param filter the filter to set
     */
    public void setFilter(ResultFilter filter) {
    	this.filter = filter;
    }
    public List<EncounterType> getEncounterTypes() {
		return encounterTypes;
	}


	public void setEncounterTypes(List<EncounterType> encounterTypes) {
		this.encounterTypes = encounterTypes;
	}


	
    public List<Form> getForms() {
    	return forms;
    }


	
    public void setForms(List<Form> forms) {
    	this.forms = forms;
    }
	
	
}
