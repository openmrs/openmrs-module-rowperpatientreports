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
package org.openmrs.module.rowperpatientreports.patientdata.persister;

import java.util.List;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.dataset.definition.persister.DataSetDefinitionPersister;
import org.openmrs.module.reporting.definition.service.SerializedDefinitionService;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;

/**
 * This class returns PatientDatas that have been Serialized to the database
 * This class is annotated as a Handler that supports all PatientData classes
 * Specifying no order on this indicates that this is the default means of Persisting 
 * a PatientData.  To override this behavior, any additional PatientDataPersister
 * should specify the order field on the Handler annotation.
 */
@Handler(supports={RowPerPatientData.class})
public class SerializedRowPerPatientDataPersister implements RowPerPatientDataPersister {
	
    //****************
    // Constructor
    //****************
	
	private SerializedRowPerPatientDataPersister() { }

    //****************
    // Instance methods
    //****************
	
	/**
	 * Utility method that returns the SerializedDefinitionService
	 */
	public SerializedDefinitionService getService() {
		return Context.getService(SerializedDefinitionService.class);
	}
    
	/**
     * @see RowPerPatientDataPersister#getPatientData(Integer)
     */
    public RowPerPatientData getPatientData(Integer id) {
    	return getService().getDefinition(RowPerPatientData.class, id);
    }
    
	/**
     * @see RowPerPatientDataPersister#getPatientDataByUuid(String)
     */
    public RowPerPatientData getPatientDataByUuid(String uuid) {
    	return getService().getDefinitionByUuid(RowPerPatientData.class, uuid);
    }

	/**
     * @see RowPerPatientDataPersister#getAllPatientDatas(boolean)
     */
    public List<RowPerPatientData> getAllPatientDatas(boolean includeRetired) {
    	return getService().getAllDefinitions(RowPerPatientData.class, includeRetired);
    }
    
	/**
	 * @see DataSetDefinitionPersister#getNumberOfDataSetDefinitions(boolean)
	 */
	public int getNumberOfPatientDatas(boolean includeRetired) {
    	return getService().getNumberOfDefinitions(RowPerPatientData.class, includeRetired);
	}

	/**
     * @see RowPerPatientDataPersister#getPatientDataByName(String, boolean)
     */
    public List<RowPerPatientData> getPatientDatas(String name, boolean exactMatchOnly) {
    	return getService().getDefinitions(RowPerPatientData.class, name, exactMatchOnly);
    }
    
	/**
     * @see RowPerPatientDataPersister#savePatientData(RowPerPatientData)
     */
    public RowPerPatientData savePatientData(RowPerPatientData patientData) {
    	return getService().saveDefinition(patientData);
    }

	/**
     * @see RowPerPatientDataPersister#purgePatientData(RowPerPatientData)
     */
    public void purgePatientData(RowPerPatientData patientData) {
    	getService().purgeDefinition(patientData);
    }
}
