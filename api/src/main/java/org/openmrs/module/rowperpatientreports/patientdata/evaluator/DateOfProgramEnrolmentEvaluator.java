package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Program;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfProgramEnrolment;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={DateOfProgramEnrolment.class})
public class DateOfProgramEnrolmentEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateResult result = new DateResult(patientData, context);
		
		DateOfProgramEnrolment pd = (DateOfProgramEnrolment)patientData;
		if (pd.getProgramId() == 0) { //This is only for crown report, it should be improved to make it more generic
			Program program=(Program)context.getParameterValue("programs");
			pd.setProgramId(program.getProgramId());
		}
		
		result.setFormat(pd.getDateFormat());

		Date programEnrolment = null;
		if(pd.isReturnEarliest())
		{
			programEnrolment = Context.getService(RowPerPatientDataService.class).getDao().getDateOfProgramEnrolmentAscending(pd.getPatientId(), pd.getProgramId(), pd.getStartDate(), pd.getEndDate());
		}
		else
		{
			programEnrolment = Context.getService(RowPerPatientDataService.class).getDao().getDateOfProgramEnrolment(pd.getPatientId(), pd.getProgramId(), pd.getStartDate(), pd.getEndDate());
		}
		
		result.setValue(programEnrolment);
	
		return result;
    }
}
