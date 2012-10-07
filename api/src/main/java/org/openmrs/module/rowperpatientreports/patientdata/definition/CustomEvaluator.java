package org.openmrs.module.rowperpatientreports.patientdata.definition;

import org.openmrs.Patient;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

public interface CustomEvaluator {
	
	public PatientDataResult evaluate(Patient patient, String name, EvaluationContext context);

}
