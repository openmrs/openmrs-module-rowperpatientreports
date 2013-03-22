package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.SystemIdentifier;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientIdentifierResult;

@Handler(supports={SystemIdentifier.class})
public class SystemIdentifierEvaluator implements RowPerPatientDataEvaluator{

	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		PatientIdentifierResult par = new PatientIdentifierResult(patientData, context);
		SystemIdentifier pd = (SystemIdentifier)patientData;
		
		par.setValue(pd.getPatientId().toString());

		return par;
    }

}
