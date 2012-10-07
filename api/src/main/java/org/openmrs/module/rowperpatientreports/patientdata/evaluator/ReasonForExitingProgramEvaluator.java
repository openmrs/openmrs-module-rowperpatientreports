package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.ReasonForExitingProgram;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.DateValueResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={ReasonForExitingProgram.class})
public class ReasonForExitingProgramEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		DateValueResult par = new DateValueResult(patientData, context);
		ReasonForExitingProgram pd = (ReasonForExitingProgram)patientData;
		
		List<PatientProgram> programs = Context.getProgramWorkflowService().getPatientPrograms(pd.getPatient(),
			    pd.getPatientProgram(), null, null, null, null, false);
		
		//now find the most recent program enrolment
		PatientProgram p = null;
		for(PatientProgram pp: programs)
		{
			if(p == null || pp.getDateEnrolled().after(p.getDateEnrolled()))
			{
				p = pp;
			}
		}
		
		if(p != null)
		{
			if(!p.getActive(pd.getOnDate()))
			{
				PatientState ps = p.getCurrentState(pd.getReasonWorkflow());
				
				if(ps != null)
				{
					par.setValue(ps.getState().getConcept().getName().getName());
				}
				par.setDateOfObservation(p.getDateCompleted());
			}
		}
		return par;
    }
}
