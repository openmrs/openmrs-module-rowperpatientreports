package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfBirthShowingEstimation;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientAttributeResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={DateOfBirthShowingEstimation.class})
public class DateOfBirthShowingEstimationEvaluator implements RowPerPatientDataEvaluator{

	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		PatientAttributeResult par = new PatientAttributeResult(patientData, context);
		DateOfBirthShowingEstimation pd = (DateOfBirthShowingEstimation)patientData;
		
		Date dob = pd.getPatient().getBirthdate();
		
		String formattedDate = "";
		
		if(dob != null)
		{
			if(pd.getPatient().isBirthdateEstimated())
			{
				formattedDate = "~ ";
				formattedDate = formattedDate + new SimpleDateFormat(pd.getEstimatedDateFormat()).format(dob);
			}
			else
			{
				formattedDate = new SimpleDateFormat(pd.getDateFormat()).format(dob);
			}
		}
		
		par.setValue(formattedDate);
		
		return par;
    }

}
