package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.List;

import org.openmrs.EncounterType;

public class RecentEncounterType extends BasePatientData implements RowPerPatientData {

	private ResultFilter filter = null;
	private List<EncounterType> encounterTypes=null;
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
}
