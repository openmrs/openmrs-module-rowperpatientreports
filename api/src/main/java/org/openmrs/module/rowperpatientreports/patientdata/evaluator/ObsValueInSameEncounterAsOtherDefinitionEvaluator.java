package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.ObsValueInSameEncounterAsOtherDefinition;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.ObservationResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.StringResult;
import org.openmrs.module.rowperpatientreports.patientdata.service.RowPerPatientDataService;

@Handler(supports = { ObsValueInSameEncounterAsOtherDefinition.class })
public class ObsValueInSameEncounterAsOtherDefinitionEvaluator implements RowPerPatientDataEvaluator {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException {
		
		StringResult par = new StringResult(patientData, context);
		ObsValueInSameEncounterAsOtherDefinition pd = (ObsValueInSameEncounterAsOtherDefinition) patientData;
		
		Mapped<RowPerPatientData> mapped = pd.getPatientData();
		
		RowPerPatientData definition = (RowPerPatientData) mapped.getParameterizable();
		definition.setPatientId(pd.getPatientId());
		definition.setPatient(pd.getPatient());
		mapped.setParameterizable(definition);
		
		PatientDataResult patientDataResult = Context.getService(RowPerPatientDataService.class).evaluate(mapped, context);
		
		ObservationResult obsResult = (ObservationResult) patientDataResult;
		
		if (obsResult != null && obsResult.getObs() != null) {
			Encounter enc = obsResult.getObs().getEncounter();
			
			List<Obs> allObs = Context.getObsService().getObservations(
			    Collections.singletonList(obsResult.getObs().getPerson()), Collections.singletonList(enc),
			    Collections.singletonList(pd.getConcept()), null, null, null, null, null, null, null, null, false);
			
			StringBuilder result = new StringBuilder();
			
			for (Obs o : allObs) {
				if (result.length() == 0) {
					result.append(",");
				}
				if (o.getValueAsString(Context.getLocale()) == null
				        || o.getValueAsString(Context.getLocale()).trim().length() == 0) {
					result.append(pd.getOutputWhenObsPresentButNoValue());
				} else {
					result.append(o.getValueAsString(Context.getLocale()));
				}
			}
			
			par.setValue(result.toString());
		}
		return par;
	}
}
