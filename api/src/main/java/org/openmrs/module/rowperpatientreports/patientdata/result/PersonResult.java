package org.openmrs.module.rowperpatientreports.patientdata.result;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;


public class PersonResult  extends BasePatientDataResult {

	protected Log log = LogFactory.getLog(this.getClass());
	
	List<Person> persons;
	
	public PersonResult(RowPerPatientData patientData, EvaluationContext ec) {
	    super(patientData, ec);
    }

	public Class<?> getColumnClass() {
	    return Person.class;
    }

	public List<Person> getValue() {
	   if(persons == null)
	   {
		   persons = new ArrayList<Person>();
	   }
	   return persons;
    }

	public boolean isMultiple() {
	    return true;
    }
	
	public void setValue(List<Person> person)
	{
		this.persons = person;
	}
	
	public void addPerson(Person person)
	{
		getValue().add(person);
	}

	
    public String getValueAsString() {
		StringBuilder result = new StringBuilder(" ");
	    
	    for(Person p: getValue())
	    {
    		try{
    			result.append(p.getGivenName());
    			result.append(" ");
    			result.append(p.getFamilyName());
    			result.append(" ");
    		}
    		catch(Exception e)
    		{
    			log.info("Error retrieving person info", e);
    		} 	
	    }
	    return result.toString().trim();

    }
	
}
