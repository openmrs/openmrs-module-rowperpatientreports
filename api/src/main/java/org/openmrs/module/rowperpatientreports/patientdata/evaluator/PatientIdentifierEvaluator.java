package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.PatientIdentifier;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientIdentifierResult;

@Handler(supports={PatientIdentifier.class})
public class PatientIdentifierEvaluator implements RowPerPatientDataEvaluator{

	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		PatientIdentifierResult par = new PatientIdentifierResult(patientData, context);
		PatientIdentifier pd = (PatientIdentifier)patientData;
		
		if(pd.getIdentifierType() != null)
		{
			org.openmrs.PatientIdentifier id = pd.getPatient().getPatientIdentifier(pd.getIdentifierType());
			if(id != null)
			{
				par.setValue(id.getIdentifier());
			}
		}
		else{
			org.openmrs.PatientIdentifier id = pd.getPatient().getPatientIdentifier();
			if(id != null)
			{
				par.setValue(id.getIdentifier());
			}
		}

		return par;
    }

}
