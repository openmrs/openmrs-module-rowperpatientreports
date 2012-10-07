package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.EncounterType;



public class MostRecentEncounterOfType extends BasePatientData implements RowPerPatientData {

	private List<EncounterType> encounterTypes = new ArrayList<EncounterType>();
	
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
}
