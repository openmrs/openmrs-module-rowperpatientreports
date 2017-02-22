package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.Relationship;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.definition.PatientRelationship;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientAttributeResult;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

@Handler(supports={PatientRelationship.class})
public class PatientRelationshipEvaluator implements RowPerPatientDataEvaluator{

	protected Log log = LogFactory.getLog(this.getClass());
	
	public PatientDataResult evaluate(RowPerPatientData patientsData, EvaluationContext context) {
	    
		PatientAttributeResult result = new PatientAttributeResult(patientsData, context);
		PatientRelationship pd = (PatientRelationship)patientsData;
		
		List<Relationship> relOfPerson=Context.getPersonService().getRelationshipsByPerson(pd.getPatient());
		
		if(relOfPerson.size()>0)
		{	
			List<Person> relatedPerson = new ArrayList<Person>();
			for(Relationship rp:relOfPerson)
			{
				if(rp.getRelationshipType().getRelationshipTypeId() == pd.getRelationshipTypeId())
				{
					try{
						if(("A").equals(pd.getRetrievePersonAorB()))
						{
							if((rp.getPersonA().getId() != patientsData.getPatient().getPersonId()) && rp.getEndDate()==null)
							{
								relatedPerson.add(rp.getPersonA());
							}
						}
						if(("B").equals(pd.getRetrievePersonAorB()))
						{
							if((rp.getPersonB().getId() != patientsData.getPatient().getPersonId()) && rp.getEndDate()==null)
							{
								relatedPerson.add(rp.getPersonB());
							}	
						}
					}
					catch(Exception e)
					{
						log.info("Problems retrieving person", e);
					}
				}
			}
			
			StringBuilder name = new StringBuilder();
			for(Person p: relatedPerson)
			{
				if(name.length() > 0)
				{
					name.append(", ");
				}
				name.append(p.getGivenName());
				name.append(" ");
				name.append(p.getFamilyName());
			}
			if(pd.getResultFilter() != null)
			{
				result.setValue((String)pd.getResultFilter().filter(name.toString()));
			}
			else
			{
				result.setValue(name.toString());
			}
		} else{ // what if the patient has no any relationship recorded?
			if(pd.getResultFilter() != null)			
				result.setValue((String)pd.getResultFilter().filter(""));
		}
			
		
		return result;
    }
}
