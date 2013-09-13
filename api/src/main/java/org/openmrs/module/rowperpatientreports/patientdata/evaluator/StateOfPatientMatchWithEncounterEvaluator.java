package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.ProgramWorkflow;
import org.openmrs.ProgramWorkflowState;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.StateOfPatient;
import org.openmrs.module.rowperpatientreports.patientdata.definition.StateOfPatientMatchWithEncounter;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports = { StateOfPatientMatchWithEncounter.class })
public class StateOfPatientMatchWithEncounterEvaluator implements RowPerPatientDataEvaluator {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
		
		DateValueResult result = new DateValueResult(patientsData, context);
		
		StateOfPatientMatchWithEncounter pd = (StateOfPatientMatchWithEncounter) patientsData;
		result.setDateFormat(pd.getDateFormat());
		
		
		ProgramWorkflow treatmentWf = pd.getPatienProgramWorkflow();
		
		List<PatientProgram> patientPrograms = Context.getProgramWorkflowService().getPatientPrograms(pd.getPatient(),
		    pd.getProgram(), null, null, null, null, false);
		
		List<Encounter> visits=Context.getEncounterService().getEncounters(pd.getPatient(), null, null, null, pd.getForms(), null, null, null, null, true);
		
		String resu = "";
		PatientState state=null;
		//PatientProgram ppp = null;
		ppLoop:for (PatientProgram pp : patientPrograms) {
			
			Set<PatientState> states=pp.getStates();
			for (PatientState patientState : states) {
				for (Encounter encounter : visits) {
					if(patientState.getState().getProgramWorkflow()!=null && patientState.getState().getProgramWorkflow().getProgramWorkflowId().equals(treatmentWf.getProgramWorkflowId()) && patientState.getStartDate().compareTo(encounter.getEncounterDatetime())==0 && patientState.getEndDate()==null && patientState.getVoided()==false){
						state=patientState;
						break ppLoop;
					}
				}
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
