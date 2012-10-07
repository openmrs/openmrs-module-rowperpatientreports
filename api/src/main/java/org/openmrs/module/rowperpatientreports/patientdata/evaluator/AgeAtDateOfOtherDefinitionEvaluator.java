package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.AgeAtDateOfOtherDefinition;
import org.openmrs.module.rowperpatientreports.patientdata.definition.DateOfPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.AgeResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports={AgeAtDateOfOtherDefinition.class})
public class AgeAtDateOfOtherDefinitionEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException{
	    
		AgeResult par = new AgeResult(patientData, context);
		AgeAtDateOfOtherDefinition pd = (AgeAtDateOfOtherDefinition)patientData;
		
		Mapped<RowPerPatientData> mapped = pd.getDateOfPatientData();
		
		DateOfPatientData definition = (DateOfPatientData)mapped.getParameterizable();
		definition.setPatient(pd.getPatient());
		definition.setPatientId(pd.getPatientId());
		mapped.setParameterizable(definition);
		
		PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(mapped, context);
		
		Date dateOfObs = (Date)patientDataResult.getValue();
		Date dateOfBirth = Context.getService(RowPerPatientDataService.class).getDao().getBirthDate(pd.getPatientId());
		
		if(dateOfObs != null && dateOfBirth != null)
		{
			Calendar birthDate = Calendar.getInstance();
			birthDate.setTime(dateOfBirth);
			
			Calendar obsDate = Calendar.getInstance();
			obsDate.setTime(dateOfObs);
			
			int age = obsDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
			
			par.setValue(age);
		}
		
		return par;
    }
}
