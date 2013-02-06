package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.PatientHash;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.StringResult;

@Handler(supports={PatientHash.class})
public class PatientHashEvaluator implements RowPerPatientDataEvaluator{

	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		StringResult par = new StringResult(patientData, context);
		PatientHash pd = (PatientHash)patientData;
		
		par.setValue(Integer.toString(pd.getPatient().getUuid().hashCode()));
		
		return par;
    }

}
