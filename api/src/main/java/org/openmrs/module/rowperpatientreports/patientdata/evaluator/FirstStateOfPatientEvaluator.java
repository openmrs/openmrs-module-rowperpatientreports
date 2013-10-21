package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.ProgramWorkflow;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.FirstStateOfPatient;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports = { FirstStateOfPatient.class })
public class FirstStateOfPatientEvaluator implements RowPerPatientDataEvaluator {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
		
		DateValueResult result = new DateValueResult(patientsData, context);
		
		FirstStateOfPatient pd = (FirstStateOfPatient) patientsData;
		result.setDateFormat(pd.getDateFormat());
		
		
		ProgramWorkflow treatmentWf = pd.getPatienProgramWorkflow();
		
		List<PatientProgram> programs = Context.getProgramWorkflowService().getPatientPrograms(pd.getPatient(),
		    pd.getPatientProgram(), null, null, null, null, false);
		
		String resu = "";
		
		PatientProgram ppp = null;
		for (PatientProgram pp : programs) {
			
			//We only want current enrolled programs
			/*if(pp.getActive(pd.getOnDate()) || pd.isIncludeCompleted())
			{*/
				
				if(ppp == null)
				{
					ppp = pp;
				}
				else if(pp.getDateEnrolled() != null)
				{
					if(ppp.getDateEnrolled() == null || pp.getDateEnrolled().after(ppp.getDateEnrolled()))
					{
						ppp = pp;
					}
				}
			//}
		}
		
		if(ppp != null)
		{
			//PatientState state = ppp.getCurrentState(treatmentWf);
			PatientState state =null;
			Set<PatientState> states=ppp.getStates();			
			
			
			for (PatientState patientState : states) {
								
				if(state==null && patientState.getState().getProgramWorkflow().getProgramWorkflowId()==treatmentWf.getProgramWorkflowId()){					
					state=patientState;
				}				
				
				else if(state!=null && patientState.getStartDate()!=null && patientState.getState().getProgramWorkflow().getProgramWorkflowId()==treatmentWf.getProgramWorkflowId() && patientState.getStartDate().compareTo(state.getStartDate())<0){
					state=patientState;
				}
			}
			
			
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
