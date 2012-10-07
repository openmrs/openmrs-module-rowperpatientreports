package org.openmrs.module.rowperpatientreports.patientdata.result;

import org.openmrs.module.reporting.evaluation.Evaluated;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public interface PatientDataResult extends Evaluated<RowPerPatientData>{
	
	public Class<?> getColumnClass();
	
	public Object getValue();
	
	public boolean isMultiple();
	
	public String getName();
	
	public String getDescription();
	
	public String getValueAsString();
	
	public EvaluationContext getContext();

	public void setDefinition(String definition); 
	
	public void setName(String name); 

	public void setEc(EvaluationContext ec);
	
	public void setPatientData(RowPerPatientData patientData); 

	public EvaluationContext getEc();
	
	public RowPerPatientData getPatientData();

}
