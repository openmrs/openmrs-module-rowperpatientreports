package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientProgram;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.EnrolledInProgram;
import org.openmrs.module.rowperpatientreports.patientdata.definition.IsEnrolledInProgram;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.StringResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={EnrolledInProgram.class})
public class EnrolledInProgramEvaluator implements RowPerPatientDataEvaluator{

	//protected Log log = LogFactory.getLog(this.getClass());
	protected Log log = LogFactory.getLog(EnrolledInProgramEvaluator.class);
	
	
public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		StringResult result = new StringResult(patientData, context);
		
		EnrolledInProgram pd = (EnrolledInProgram)patientData;

		if(pd.getProgram().getProgramId() !=null)
		{
			SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");
			Integer progId=Context.getService(RowPerPatientDataService.class).getDao().getLastPatientProgramByStartDateAndEndDate(pd.getPatientId(), pd.getProgram().getProgramId(), null, null);
			PatientProgram pp=null;
			if(progId!=null){
			pp=Context.getProgramWorkflowService().getPatientProgram(progId.intValue());
			}
			if(pp == null)
			{
				result.setValue(" ");
			}
			else if(pd.getValueType().equalsIgnoreCase("EnrollmentDate"))
			{
				result.setValue(" "+sdf.format(pp.getDateEnrolled()));
			}
			else if(pd.getValueType().equalsIgnoreCase("ExitDate") && pp.getDateCompleted()!=null)
			{
				result.setValue(" "+sdf.format(pp.getDateCompleted()));
			}
			else if(pd.getValueType().equalsIgnoreCase("OutCome") && pp.getOutcome()!=null)
			{
				result.setValue(" "+pp.getOutcome().getName().getName());
			}/*else{
				result.setValue(" ");
			}*/
		}
		
		if(pd.getFilter() != null)
		{
			result.setValue((String)pd.getFilter().filter(result.getValue()));
		}
	
		return result;
    }
}
