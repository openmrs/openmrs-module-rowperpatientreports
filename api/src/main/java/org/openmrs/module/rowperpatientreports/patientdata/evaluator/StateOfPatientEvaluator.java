package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.ProgramWorkflow;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.StateOfPatient;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports = { StateOfPatient.class })
public class StateOfPatientEvaluator implements RowPerPatientDataEvaluator {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
		
		DateValueResult result = new DateValueResult(patientsData, context);
		
		StateOfPatient pd = (StateOfPatient) patientsData;
		result.setDateFormat(pd.getDateFormat());
		
		
		ProgramWorkflow treatmentWf = pd.getPatienProgramWorkflow();
		
		List<PatientProgram> programs = Context.getProgramWorkflowService().getPatientPrograms(pd.getPatient(),
		    pd.getPatientProgram(), null, null, null, null, false);
		
		String resu = "";
		
		PatientProgram ppp = null;
		for (PatientProgram pp : programs) {
			
			//We only want current enrolled programs
			if(pp.getActive(pd.getOnDate()) || pd.isIncludeCompleted())
			{
				
				if(ppp == null)
				{
					ppp = pp;
				}
				else if(pp.getDateEnrolled() != null)
				{
					if(ppp.getDateEnrolled() == null || pp.getDateEnrolled().after(ppp.getDateEnrolled()));
				}
			}
		}
		
		if(ppp != null)
		{
			PatientState state = ppp.getCurrentState(treatmentWf);
			
			if (state != null) {
				
				String stateName = state.getState().getConcept().getName().getName();
				if(pd.getFilter() != null)
				{
					stateName = (String)pd.getFilter().filter(stateName);
				}
				
				result.setValue(stateName);
				result.setDateOfObservation(state.getStartDate());
			}
		}
		
		if(result.getValue() == null && pd.getFilter() != null)
		{
			if(pd.getFilter().filterWhenNull()!= null)
			{
				result.setValue(pd.getFilter().filterWhenNull().toString());
			}
			
		}
		
		return result;
	}
}
