package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfBirth;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={DateOfBirth.class})
public class DateOfBirthEvaluator implements RowPerPatientDataEvaluator{

	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateResult par = new DateResult(patientData, context);
		DateOfBirth pd = (DateOfBirth)patientData;
		
		par.setFormat(pd.getDateFormat());
		par.setValue(pd.getPatient().getBirthdate());
		
		return par;
    }

}
