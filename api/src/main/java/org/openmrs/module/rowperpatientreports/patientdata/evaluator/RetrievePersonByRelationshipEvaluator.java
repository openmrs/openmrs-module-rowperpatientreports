package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Relationship;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RetrievePersonByRelationship;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PersonResult;

@Handler(supports = { RetrievePersonByRelationship.class })
public class RetrievePersonByRelationshipEvaluator implements RowPerPatientDataEvaluator {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
		
		PersonResult result = new PersonResult(patientsData, context);
		RetrievePersonByRelationship pd = (RetrievePersonByRelationship) patientsData;
		
		List<Relationship> relOfPerson = Context.getPersonService().getRelationshipsByPerson(pd.getPatient());
		
		if (relOfPerson.size() > 0) {
			for (Relationship rp : relOfPerson) {
				if (rp.getRelationshipType().getRelationshipTypeId() == pd.getRelationshipTypeId()) {
					try {
						if (("A").equals(pd.getRetrievePersonAorB())) {
							result.addPerson(rp.getPersonA());
						}
						if (("B").equals(pd.getRetrievePersonAorB())) {
							result.addPerson(rp.getPersonB());
						}
					}
					catch (Exception e) {
						log.info("Problems retrieving person", e);
					}
				}
			}
		}
		return result;
	}
	
}
