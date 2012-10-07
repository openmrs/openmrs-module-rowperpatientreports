package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.Map;

import org.openmrs.module.reporting.evaluation.parameter.Mapped;





public class EvaluateDefinitionForOtherPersonData extends BasePatientData implements RowPerPatientData {
	
	private Mapped<PersonData> personData;
	
	private Mapped<RowPerPatientData> definition;

	
    /**
     * @return the personData
     */
    public Mapped<PersonData> getPersonData() {
    	return personData;
    }

	
    /**
     * @param personData the personData to set
     */
    public void setPersonData(PersonData personData, Map<String, Object> mappings) {
    	this.personData = new Mapped<PersonData>(personData, mappings);
    }

	
    /**
     * @return the definition
     */
    public Mapped<RowPerPatientData> getDefinition() {
    	return definition;
    }

	
    /**
     * @param definition the definition to set
     */
    public void setDefinition(RowPerPatientData definition, Map<String, Object> mappings) {
    	this.definition = new Mapped<RowPerPatientData>(definition, mappings);
    }

    
}
