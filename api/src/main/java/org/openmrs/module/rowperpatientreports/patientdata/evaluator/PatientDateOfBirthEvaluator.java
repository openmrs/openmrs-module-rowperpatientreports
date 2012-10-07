package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.PatientDateOfBirth;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientAttributeResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports = { PatientDateOfBirth.class })
public class PatientDateOfBirthEvaluator implements RowPerPatientDataEvaluator {
	
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
		
		PatientAttributeResult result = new PatientAttributeResult(patientsData, context);
		PatientDateOfBirth pd = (PatientDateOfBirth) patientsData;
		
		Date birthDate = (Date) (pd.getPatient()).getBirthdate();
		String date = "";
		SimpleDateFormat sdf = new SimpleDateFormat(pd.getDateFormat());
		if (birthDate != null) {
			date = sdf.format(birthDate);
		}
		result.setValue(date);
		
		return result;
	}
}
