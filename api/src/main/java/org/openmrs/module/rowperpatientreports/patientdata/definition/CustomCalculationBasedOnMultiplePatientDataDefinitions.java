package org.openmrs.module.rowperpatientreports.patientdata.definition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openmrs.module.reporting.evaluation.parameter.Mapped;



/**
 * This PatientData will take in multiple Patient Data Definitions, allow with a custom
 * calculator class. The evaluator will resolve all the Patient Data Definitions and pass them 
 * to the calculator, which will have custom code to calculate the result based on the values 
 * returned by the individual Patient Data Definitions.
 */
public class CustomCalculationBasedOnMultiplePatientDataDefinitions extends BasePatientData implements RowPerPatientData {
	
	private List<Mapped<RowPerPatientData>> patientDataToBeEvaluated = null;
	
	private CustomCalculation calculator;

    /**
     * Constructor 
     */
    public CustomCalculationBasedOnMultiplePatientDataDefinitions() {
	    super();
    }

	public List<Mapped<RowPerPatientData>> getPatientDataToBeEvaluated() {
		if(patientDataToBeEvaluated == null)
		{
			patientDataToBeEvaluated = new ArrayList<Mapped<RowPerPatientData>>();
		}
		return patientDataToBeEvaluated;
	}
	
	public void addPatientDataToBeEvaluated(RowPerPatientData patientData, Map<String, Object> mappings)
	{
		getPatientDataToBeEvaluated().add(new Mapped<RowPerPatientData>(patientData, mappings));
	}

	public void setPatientDataToBeEvaluated(
			List<Mapped<RowPerPatientData>> patientDataToBeEvaluated) {
		this.patientDataToBeEvaluated = patientDataToBeEvaluated;
	}

	public CustomCalculation getCalculator() {
		return calculator;
	}

	public void setCalculator(CustomCalculation calculator) {
		this.calculator = calculator;
	}  
}
