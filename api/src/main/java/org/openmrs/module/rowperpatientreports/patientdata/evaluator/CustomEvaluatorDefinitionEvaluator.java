package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.CustomEvaluatorDefinition;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={CustomEvaluatorDefinition.class})
public class CustomEvaluatorDefinitionEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		CustomEvaluatorDefinition ced =  (CustomEvaluatorDefinition)patientData;
		
		return ced.getEvaluator().evaluate(ced.getPatient(), ced.getName(), context);
    }
}
