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
package org.openmrs.module.rowperpatientreports.patientdata.service;

import org.openmrs.module.reporting.definition.service.DefinitionService;
import org.openmrs.module.reporting.evaluation.Definition;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.service.db.RowPerPatientReportDAO;
import org.springframework.transaction.annotation.Transactional;
import org.openmrs.module.reporting.evaluation.EvaluationException;


/**
 * Contains methods pertaining to creating/updating/deleting/retiring/registering/evaluating PatientData to be used in row
 * per patient reports
 */
@Transactional
public interface RowPerPatientDataService extends DefinitionService<RowPerPatientData> {
	
	public RowPerPatientReportDAO getDao();
	
    /**
     * @param dao the dao to set
     */
    public void setDao(RowPerPatientReportDAO dao);
	
	/**
	 * @see DefinitionService#evaluate(Definition, EvaluationContext)
	 */
	@Transactional(readOnly = true)
	public PatientDataResult evaluate(RowPerPatientData patientData, EvaluationContext context) throws EvaluationException;
	
	/**
	 * @see DefinitionService#evaluate(Mapped<Definition>, EvaluationContext)
	 */
	@Transactional(readOnly = true)
	public PatientDataResult evaluate(Mapped<? extends RowPerPatientData> patientData, EvaluationContext context) throws EvaluationException;;
}
