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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.annotation.Handler;
import org.openmrs.api.APIException;
import org.openmrs.module.reporting.definition.service.BaseDefinitionService;
import org.openmrs.module.reporting.definition.service.DefinitionService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;
import org.openmrs.module.rowperpatientreports.patientdata.evaluator.RowPerPatientDataEvaluator;
import org.openmrs.module.rowperpatientreports.patientdata.result.PatientDataResult;
import org.openmrs.module.rowperpatientreports.service.db.RowPerPatientReportDAO;
import org.openmrs.util.HandlerUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Base Implementation of PatientDataService
 */
@Transactional
@Service
public class RowPerPatientDataServiceImpl extends BaseDefinitionService<RowPerPatientData> implements RowPerPatientDataService {

	protected static Log log = LogFactory.getLog(RowPerPatientDataServiceImpl.class);
	
	protected RowPerPatientReportDAO dao;
	
	/**
	 * @see DefinitionService#getDefinitionType()
	 */
	@Transactional(readOnly = true)
	public Class<RowPerPatientData> getDefinitionType() {
		return RowPerPatientData.class;
	}
	
	/**
	 * @see DefinitionService#getDefinitionTypes()
	 */
	@SuppressWarnings("unchecked")
	public List<Class<? extends RowPerPatientData>> getDefinitionTypes() {
		List<Class<? extends RowPerPatientData>> ret = new ArrayList<Class<? extends RowPerPatientData>>();
		for (RowPerPatientDataEvaluator e : HandlerUtil.getHandlersForType(RowPerPatientDataEvaluator.class, null)) {
			Handler handlerAnnotation = e.getClass().getAnnotation(Handler.class);
			if (handlerAnnotation != null) {
				Class<?>[] types = handlerAnnotation.supports();
				if (types != null) {
					for (Class<?> type : types) {
						ret.add((Class<? extends RowPerPatientData>) type);
					}
				}
			}
		}
		return ret;
	}
	
	/**
	 * @see DefinitionService#getDefinition(Class, Integer)
	 */
	@SuppressWarnings("unchecked")
	public <D extends RowPerPatientData> D getDefinition(Class<D> type, Integer id) throws APIException {
		return (D) getPersister(type);
	}
	

	
	/** 
	 * @see RowPerPatientDataService#evaluate(RowPerPatientData, EvaluationContext)
	 */
	public PatientDataResult evaluate(RowPerPatientData definition, EvaluationContext context) throws EvaluationException{
		RowPerPatientDataEvaluator evaluator = HandlerUtil.getPreferredHandler(RowPerPatientDataEvaluator.class, definition.getClass());
		return evaluator.evaluate(definition, context);
	}
	
	/** 
	 * @see RowPerPatientDataService#evaluate(Mapped, EvaluationContext)
	 */
	public PatientDataResult evaluate(Mapped<? extends RowPerPatientData> definition, EvaluationContext context) throws EvaluationException{
		return (PatientDataResult) super.evaluate(definition, context);
	}

	
    /**
     * @return the dao
     */
    public RowPerPatientReportDAO getDao() {
    	return dao;
    }

	
    /**
     * @param dao the dao to set
     */
    public void setDao(RowPerPatientReportDAO dao) {
    	this.dao = dao;
    }

	
}
