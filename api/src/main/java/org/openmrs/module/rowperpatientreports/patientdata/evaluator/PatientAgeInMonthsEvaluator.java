package org.openmrs.module.rowperpatientreports.patientdata.evaluator;


import java.util.Calendar;
import java.util.Date;

import org.joda.time.Days;
import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.PatientAgeInMonths;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.AgeResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={PatientAgeInMonths.class})
public class PatientAgeInMonthsEvaluator implements RowPerPatientDataEvaluator{
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
	    	
		AgeResult result = new AgeResult(patientsData, context);
		PatientAgeInMonths pd = (PatientAgeInMonths)patientsData;

		Date birthDate = pd.getPatient().getBirthdate();
		if(birthDate != null)
		{
			result.setValue(calculateMonthsDifference(pd.getOnDate(), birthDate));
		}
		
		return result;
    }

	private int calculateMonthsDifference(Date observation, Date startingDate)
	{
		int diff = 0;
	
		Calendar obsDate = Calendar.getInstance();	
		obsDate.setTime(observation);

		Calendar startDate = Calendar.getInstance();
		startDate.setTime(startingDate);
	
		//find out if there is any difference in years first
		diff = obsDate.get(Calendar.YEAR) - startDate.get(Calendar.YEAR);
		diff = diff * 12;
		int monthDiff = 0;
		if (obsDate.get(Calendar.DAY_OF_MONTH) >= startDate.get(Calendar.DAY_OF_MONTH)) {
			monthDiff = obsDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH);
		}else{
			monthDiff = (obsDate.get(Calendar.MONTH) - startDate.get(Calendar.MONTH))-1;
		}
		diff = diff + monthDiff;

		return diff;
	}
}
