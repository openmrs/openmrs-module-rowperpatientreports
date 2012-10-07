package org.openmrs.module.rowperpatientreports.patientdata.result;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public abstract class BasePatientDataResult implements PatientDataResult {

	private String definition;
	private String name;
	private EvaluationContext ec;
	private RowPerPatientData patientData;
	
	public BasePatientDataResult(RowPerPatientData patientData, EvaluationContext ec)
	{
		super();
		this.patientData = patientData;
		this.ec = ec;
		
		if(patientData != null)
		{
			definition = patientData.getDescription();
			name = patientData.getName();
		}
	}
	
	public RowPerPatientData getDefinition() {
	   return patientData;
    }

	public EvaluationContext getContext() {
	    return ec;
    }


	public String getName() {
	    return name;
    }

	public String getDescription() {
	   return definition;
    }

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEc(EvaluationContext ec) {
		this.ec = ec;
	}

	public void setPatientData(RowPerPatientData patientData) {
		this.patientData = patientData;
	}

	public EvaluationContext getEc() {
		return ec;
	}

	public RowPerPatientData getPatientData() {
		return patientData;
	}

}
