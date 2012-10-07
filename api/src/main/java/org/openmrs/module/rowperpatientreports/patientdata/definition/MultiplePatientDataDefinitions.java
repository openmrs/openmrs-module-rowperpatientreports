package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openmrs.module.reporting.evaluation.parameter.Mapped;



public class MultiplePatientDataDefinitions extends BasePatientData implements RowPerPatientData {

	private List<Mapped<RowPerPatientData>> patientDataDefinitions;

	
    /**
     * @return the concept
     */
    public List<Mapped<RowPerPatientData>> getPatientDataDefinitions() {
    	if(patientDataDefinitions == null)
    	{
    		patientDataDefinitions = new ArrayList<Mapped<RowPerPatientData>>();
    	}
    	return patientDataDefinitions;
    }

	
    /**
     * @param concept the concept to set
     */
    public void setPatientDataDefinitions(List<Mapped<RowPerPatientData>> patientDataDefinitions) {
    	this.patientDataDefinitions = patientDataDefinitions;
    }

    public void addPatientDataDefinition(RowPerPatientData definition, Map<String, Object> mappings)
    {
    	getPatientDataDefinitions().add(new Mapped<RowPerPatientData>(definition, mappings));
    }
}
