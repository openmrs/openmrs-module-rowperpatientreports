package org.openmrs.module.rowperpatientreports.patientdata.definition;




/**
 * This PatientData will take in multiple Patient Data Definitions, allow with a custom
 * calculator class. The evaluator will resolve all the Patient Data Definitions and pass them 
 * to the calculator, which will have custom code to calculate the result based on the values 
 * returned by the individual Patient Data Definitions.
 */
public class CustomEvaluatorDefinition extends BasePatientData implements RowPerPatientData {
	
	private CustomEvaluator evaluator;

    /**
     * Constructor 
     */
    public CustomEvaluatorDefinition() {
	    super();
    }

	
    public CustomEvaluator getEvaluator() {
    	return evaluator;
    }

	
    public void setEvaluator(CustomEvaluator evaluator) {
    	this.evaluator = evaluator;
    }
}