package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.CustomCalculationBasedOnMultiplePatientDataDefinitions;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.NullResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={CustomCalculationBasedOnMultiplePatientDataDefinitions.class})
public class CustomCalculationBasedOnMultiplePatientDataDefinitionsEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException{
	    
		CustomCalculationBasedOnMultiplePatientDataDefinitions pd = (CustomCalculationBasedOnMultiplePatientDataDefinitions)patientData;
		
		List<Mapped<RowPerPatientData>> definitions = pd.getPatientDataToBeEvaluated();
		List<PatientDataResult> results = new ArrayList<PatientDataResult>();
		
		for(Mapped<RowPerPatientData> def: definitions)
		{
			RowPerPatientData data = def.getParameterizable();
			data.setPatient(pd.getPatient());
			data.setPatientId(pd.getPatientId());
			def.setParameterizable(data);
			
			PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(def, context);
			results.add(patientDataResult);
		}
		
		if(pd.getCalculator() != null)
		{
			PatientDataResult result =  pd.getCalculator().calculateResult(results, context);
			result.setDefinition(patientData.getDescription());
			result.setName(patientData.getName());
			result.setEc(context);
			result.setPatientData(patientData);
			return result;
		}
		
		return new NullResult(pd, context);
    }
}
