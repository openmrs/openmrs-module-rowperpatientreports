package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.annotation.Handler;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.PatientProperty;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientPropertyResult;
import org.springframework.util.StringUtils;

@Handler(supports={PatientProperty.class})
public class PatientPropertyEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) {
	    
		PatientPropertyResult par = new PatientPropertyResult(patientData, context);
		PatientProperty pd = (PatientProperty)patientData;
		
		Method m;
        try {
	        m = Patient.class.getMethod("get" + StringUtils.capitalize(pd.getProperty()), new Class[] {});
	        par.setClass(m.getReturnType());
			
			Object o = m.invoke(pd.getPatient(), new Object[] {});
			par.setValue(o);
        }
        catch (Exception e) {
			log.error("Unable to get property " + pd.getProperty() + " on patient for dataset", e);
		}
        	
		
		return par;
    }

}
