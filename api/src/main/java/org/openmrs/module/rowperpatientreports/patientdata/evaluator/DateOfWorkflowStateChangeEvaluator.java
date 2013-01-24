package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfWorkflowStateChange;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={DateOfWorkflowStateChange.class})
public class DateOfWorkflowStateChangeEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateResult result = new DateResult(patientData, context);
		
		DateOfWorkflowStateChange pd = (DateOfWorkflowStateChange)patientData;
		
		result.setFormat(pd.getDateFormat());

		Date workflowChange = Context.getService(RowPerPatientDataService.class).getDao().getDateOfWorkflowStateChange(pd.getPatientId(), pd.getConcept().getId(), pd.getStartDate(), pd.getEndDate());
		
		result.setValue(workflowChange);
	
		return result;
    }
}
