/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.rowperpatientreports.patientdata.evaluator;

import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;

/**
 * This interfaces provides the functionality to evaluate an PatientData and return a result.
 */
public interface RowPerPatientDataEvaluator {
	
	/**
	 * Evaluates an PatientData based on the passed EvaluationContext
	 * @param patientData PatientData to evaluate
	 * @param context context to use during evaluation
	 * @return an IndicatorResult representing the PatientData evaluation result
	 * @throws EvaluationException 
	 */
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException;
		
}

