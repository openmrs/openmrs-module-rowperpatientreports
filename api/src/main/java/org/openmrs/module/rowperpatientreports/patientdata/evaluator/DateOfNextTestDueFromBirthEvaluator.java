package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Calendar;
import java.util.Date;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfNextTestDueFromBirth;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={DateOfNextTestDueFromBirth.class})
public class DateOfNextTestDueFromBirthEvaluator implements RowPerPatientDataEvaluator{

	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateResult par = new DateResult(patientData, context);
		DateOfNextTestDueFromBirth pd = (DateOfNextTestDueFromBirth)patientData;
		par.setFormat(pd.getDateFormat());
		
		Date dob = pd.getPatient().getBirthdate();
		
		if(dob != null)
		{
			Calendar date = Calendar.getInstance();
			date.setTime(dob);
			
			date.add(pd.getTimeUnit(), pd.getTimeIncrement());
			
			par.setValue(date.getTime());
		}
		
		return par;
    }

}
