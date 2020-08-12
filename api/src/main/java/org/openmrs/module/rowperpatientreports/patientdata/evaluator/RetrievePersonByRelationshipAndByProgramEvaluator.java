package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.Person;
import org.openmrs.Program;
import org.openmrs.Relationship;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.InProgramCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.service.CohortDefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RetrievePersonByRelationshipAndByProgram;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PersonResult;

@Handler(supports = { RetrievePersonByRelationshipAndByProgram.class })
public class RetrievePersonByRelationshipAndByProgramEvaluator implements RowPerPatientDataEvaluator {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
		
		PersonResult result = new PersonResult(patientsData, context);
		RetrievePersonByRelationshipAndByProgram pd = (RetrievePersonByRelationshipAndByProgram) patientsData;
		
		List<Relationship> relOfPerson = Context.getPersonService().getRelationshipsByPerson(pd.getPatient());
		
		Person person=null;
		if (relOfPerson.size() > 0) {
			for (Relationship rp : relOfPerson) {
				if (rp.getRelationshipType().getRelationshipTypeId() == pd.getRelationshipTypeId()) {
					try {
						if (("A").equals(pd.getRetrievePersonAorB())) {
							person=rp.getPersonA();
							//result.addPerson(rp.getPersonA());
						}
						if (("B").equals(pd.getRetrievePersonAorB())) {
							person=rp.getPersonB();
							//result.addPerson(rp.getPersonB());
						}
					}
					catch (Exception e) {
						log.info("Problems retrieving person", e);
					}
				}
			}
		}
		if(person !=null && pd.getProgram()!=null){
			Program program = Context.getProgramWorkflowService().getProgram(pd.getProgram().getProgramId());
			InProgramCohortDefinition cd = new InProgramCohortDefinition();
			cd.setPrograms(Arrays.asList(program));
			try {
				Cohort c = Context.getService(CohortDefinitionService.class).evaluate(cd, new EvaluationContext());
				Set<Integer> patientsIds = c.getMemberIds();
				int idMotherICC;
				int idPerson;
				for (Integer integer : patientsIds) {
					idMotherICC = integer;
					idPerson = person.getPersonId();
					if (idMotherICC == idPerson) {
						result.addPerson(person);
					}
				}
			}
			catch (EvaluationException e) {
				throw new RuntimeException(e);
			}
			
		}
		/*else if(person !=null){
			result.addPerson(person);
		}*/
		return result;
	}
	
}
