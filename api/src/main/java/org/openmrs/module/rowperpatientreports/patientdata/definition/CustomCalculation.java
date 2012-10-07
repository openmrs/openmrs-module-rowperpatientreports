package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.List;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

public interface CustomCalculation {
	
	public PatientDataResult calculateResult(List<PatientDataResult> results, EvaluationContext context);

}
