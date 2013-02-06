package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.IsEnrolledInProgram;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.StringResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={IsEnrolledInProgram.class})
public class IsEnrolledInProgramEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		StringResult result = new StringResult(patientData, context);
		
		IsEnrolledInProgram pd = (IsEnrolledInProgram)patientData;

		if(pd.getProgramId() > -1)
		{
			Date programEnrolment = Context.getService(RowPerPatientDataService.class).getDao().getDateOfProgramEnrolment(pd.getPatientId(), pd.getProgramId(), null, null);
			if(programEnrolment == null)
			{
				result.setValue("Not Enrolled");
			}
			else
			{
				result.setValue("Enrolled");
			}
		}
		
		if(pd.getFilter() != null)
		{
			result.setValue((String)pd.getFilter().filter(result.getValue()));
		}
	
		return result;
    }
}
