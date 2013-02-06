package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfMultipleWorkflowStateChange;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={DateOfMultipleWorkflowStateChange.class})
public class DateOfMultipleWorkflowStateChangeEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateResult result = new DateResult(patientData, context);
		
		DateOfMultipleWorkflowStateChange pd = (DateOfMultipleWorkflowStateChange)patientData;
		
		List<Integer> conceptIds = new ArrayList<Integer>();
		for(Concept stateConcept: pd.getConcepts())
		{
			conceptIds.add(stateConcept.getConceptId());
		}

		Date workflowChange = Context.getService(RowPerPatientDataService.class).getDao().getDateOfWorkflowStateChange(pd.getPatientId(), conceptIds, pd.getStartDate(), pd.getEndDate());
		
		result.setValue(workflowChange);
		
		return result;
    }
}
