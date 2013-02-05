package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.MostRecentProgramWorkflowState;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.StringResult;

@Handler(supports={MostRecentProgramWorkflowState.class})
public class MostRecentProgramWorkflowStateEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		StringResult result = new StringResult(patientData, context);
		
		MostRecentProgramWorkflowState pd = (MostRecentProgramWorkflowState)patientData;
		
		List<PatientProgram> patientPrograms = Context.getProgramWorkflowService().getPatientPrograms(pd.getPatient(), null, null, null, null, null, false);
		
		PatientState mostRecent = null;
		
		for(PatientProgram prog: patientPrograms)
		{
			Set<PatientState> states = prog.getStates();
			
			for(PatientState state: states)
			{
				if(!state.isVoided() && state.getStartDate() != null)
				{
					if(pd.getWorkflows().contains(state.getState().getProgramWorkflow()))
					{
						if(mostRecent == null || mostRecent.getStartDate().before(state.getStartDate()))
						{
							mostRecent = state;
						}
					}
				}
			}
		}
		
		if(mostRecent != null)
		{
			if(pd.getFilter() != null)
			{
				result.setValue(pd.getFilter().filter(mostRecent).toString());
			}
			else
			{
				result.setValue(mostRecent.toString());
			}
		}
		
		return result;
    }
}
