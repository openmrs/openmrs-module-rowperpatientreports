package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.PatientProgram;
import org.openmrs.PatientState;
import org.openmrs.ProgramWorkflow;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.FullHistoryOfProgramWorkflowStates;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.StringResult;

@Handler(supports={FullHistoryOfProgramWorkflowStates.class})
public class FullHistoryOfProgramWorkflowStatesEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		StringResult result = new StringResult(patientData, context);
		
		FullHistoryOfProgramWorkflowStates pd = (FullHistoryOfProgramWorkflowStates)patientData;
		
		StringBuilder res = new StringBuilder();
		
		List<PatientProgram> patientPrograms = Context.getProgramWorkflowService().getPatientPrograms(pd.getPatient(), null, null, null, null, null, false);
		
		for(PatientProgram prog: patientPrograms)
		{
			boolean addProg = true;
			for(ProgramWorkflow pw: pd.getWorkflows())
			{
				List<PatientState> psl = prog.statesInWorkflow(pw, false);
				if(psl != null && psl.size() > 0)
				{
					for(PatientState ps: psl)
					{
						if(res.length() > 0)
						{
							res.append(", ");
						}
						if(addProg)
						{
							res.append("Program: ");
							res.append(prog.getProgram().getName());
							res.append(" Date Enrolled: ");
							if(prog.getDateEnrolled() != null)
							{
								res.append(new SimpleDateFormat(pd.getDateFormat()).format(prog.getDateEnrolled()));
							}
							res.append(" Date Completed: ");
							if(prog.getDateCompleted() != null)
							{
								res.append(new SimpleDateFormat(pd.getDateFormat()).format(prog.getDateCompleted()));
							}
							res.append(" ");
							addProg = false;
						}
						res.append("State: ");
						res.append(ps.getState().getConcept().getName().getName());
						res.append(" Start Date: ");
						if(ps.getStartDate() != null)
						{
							res.append(new SimpleDateFormat(pd.getDateFormat()).format(ps.getStartDate()));
						}
						res.append(" End Date: ");
						if(ps.getEndDate() != null)
						{
							res.append(new SimpleDateFormat(pd.getDateFormat()).format(ps.getEndDate()));
						}
					}
				}
			}
		}
		
		result.setValue(res.toString());
		return result;
    }
}
