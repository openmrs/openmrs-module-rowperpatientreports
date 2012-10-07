package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientProgram;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfAllProgramEnrolment;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.StringResult;

@Handler(supports={DateOfAllProgramEnrolment.class})
public class DateOfAllProgramEnrolmentEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		StringResult result = new StringResult(patientData, context);
		
		DateOfAllProgramEnrolment pd = (DateOfAllProgramEnrolment)patientData;
		
		List<PatientProgram> programs = Context.getProgramWorkflowService().getPatientPrograms(pd.getPatient(),
			    pd.getPatientProgram(), null, null, null, null, false);
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(pd.getDateFormat());
		
		String resu = "";
		for (PatientProgram pp : programs) {
					
			if (resu.length() > 0) {
				
				resu = resu + ", Start Date: ";
				if(pp.getDateEnrolled() != null)
				{
				  resu = resu + dateFormatter.format(pp.getDateEnrolled());
				}
				resu = resu + " End Date: ";
				
				if(pp.getDateCompleted() != null)
				{
					resu = resu + dateFormatter.format(pp.getDateCompleted());
				}
			} else {
				resu = "Start Date: ";
				if(pp.getDateEnrolled() != null)
				{
				  resu = resu + dateFormatter.format(pp.getDateEnrolled());
				}
				resu = resu + " End Date: ";
				if(pp.getDateCompleted() != null)
				{
					resu = resu + dateFormatter.format(pp.getDateCompleted());
				}
			}
		}
		if(resu.length() > 0)
		{
			result.setValue(resu);
		}
		
		return result;
    }
}
