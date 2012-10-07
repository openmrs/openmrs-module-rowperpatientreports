package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import org.openmrs.PersonAttribute;
import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.PatientAttribute;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientAttributeResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={PatientAttribute.class})
public class PatientAttributeEvaluator implements RowPerPatientDataEvaluator{

	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		PatientAttributeResult par = new PatientAttributeResult(patientData, context);
		PatientAttribute pd = (PatientAttribute)patientData;
		
		PersonAttribute att = pd.getPatient().getAttribute(pd.getAttribute());
		if(att != null)
		{
			par.setValue(att.getHydratedObject().toString());
		}
		
		return par;
    }

}
