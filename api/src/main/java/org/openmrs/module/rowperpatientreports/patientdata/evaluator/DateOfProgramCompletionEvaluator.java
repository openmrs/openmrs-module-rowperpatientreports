package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Program;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfProgramCompletion;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={DateOfProgramCompletion.class})
public class DateOfProgramCompletionEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateResult result = new DateResult(patientData, context);
		
		DateOfProgramCompletion pd = (DateOfProgramCompletion)patientData;
		if (pd.getProgramId() == 0) { //This is only for crown report, it should be improved to make it more generic
			Program program=(Program)context.getParameterValue("programs");
			pd.setProgramId(program.getProgramId());
		}
		result.setFormat(pd.getDateFormat());

		Date programCompletion = null;
		if(pd.isReturnEarliest())
		{
			programCompletion = Context.getService(RowPerPatientDataService.class).getDao().getDateOfProgramCompletionAscending(pd.getPatientId(), pd.getProgramId());
		}
		else
		{
			programCompletion = Context.getService(RowPerPatientDataService.class).getDao().getDateOfProgramCompletion(pd.getPatientId(), pd.getProgramId());
		}
		
		result.setValue(programCompletion);
	
		return result;
    }
}
