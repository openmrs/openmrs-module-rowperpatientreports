package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.MultiplePatientDataDefinitions;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.NullResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={MultiplePatientDataDefinitions.class})
public class MultiplePatientDataDefinitionsEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException{
		
		MultiplePatientDataDefinitions pd = (MultiplePatientDataDefinitions)patientData;
	
		for(Mapped<RowPerPatientData> mapped: pd.getPatientDataDefinitions())
		{
			RowPerPatientData def = mapped.getParameterizable();
			def.setPatient(pd.getPatient());
			def.setPatientId(pd.getPatientId());
			def.setName(pd.getName());
			mapped.setParameterizable(def);
			PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(mapped, context);
			
			if(patientDataResult.getValue() != null)
			{
				return patientDataResult;
			}
		}
		
		return new NullResult(patientData, context);
    }
}
