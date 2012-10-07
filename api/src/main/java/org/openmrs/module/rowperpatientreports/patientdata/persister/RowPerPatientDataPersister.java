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

import org.openmrs.api.APIException;
import org.openmrs.module.rowperpatientreports.patientdata.definition.RowPerPatientData;

/**
 * This interface exposes the functionality required to access the Data Access
 * functionality for a particular set of PatientData implementations
 */
public interface RowPerPatientDataPersister {
	
	/**
	 * Gets the {@link RowPerPatientData} that matches the given id
	 * 
	 * @param id the id to match
	 * @return the {@link RowPerPatientData} with the given id among those managed by this persister
	 * 
	 * @should return null when does not exist
	 * @should return PatientData when exists
	 */
	public RowPerPatientData getPatientData(Integer id);
	
	/**
	 * Gets the {@link RowPerPatientData} that matches the given uuid
	 * 
	 * @param uuid	the uuid to match
	 * @return the {@link RowPerPatientData} with the given uuid among those managed by this persister
	 * 
	 * @should return null when does not exist
	 * @should return {@link RowPerPatientData} when exists
	 */
	public RowPerPatientData getPatientDataByUuid(String uuid);
	
	/**
	 * @param includeRetired - if true, include retired {@link RowPerPatientData} in the returned List
	 * @return All {@link RowPerPatientData} whose persistence is managed by this persister
	 * 
	 * @should get all {@link RowPerPatientData} including retired
	 * @should get all {@link RowPerPatientData} not including retired
	 */
	public List<RowPerPatientData> getAllPatientDatas(boolean includeRetired);
	
	/**
	 * @param includeRetired indicates whether to also include retired PatientDatas in the count
	 * @return the number of saved PatientDatas
	 */
	public int getNumberOfPatientDatas(boolean includeRetired);
	
	/**
	 * Returns a List of {@link RowPerPatientData} whose name contains the passed name.
	 * An empty list will be returned if there are none found. Search is case insensitive.
	 * 
	 * @param name The search string
	 * @param exactMatchOnly if true will only return exact matches
	 * 
	 * @throws APIException
	 * @return a List<PatientData> objects whose name contains the passed name
	 */
	public List<RowPerPatientData> getPatientDatas(String name, boolean exactMatchOnly);
	
	/**
	 * Saves the given {@link RowPerPatientData} to the system.
	 * 
	 * @param datasetDefinition	the {@link RowPerPatientData} to save
	 * @return the {@link RowPerPatientData} that was 
	 * 
	 * @should create new {@link RowPerPatientData}
	 * @should update existing {@link RowPerPatientData}
	 * @should set identifier after save
	 */
	public RowPerPatientData savePatientData(RowPerPatientData patientData);
	
	/**
	 * Deletes a {@link RowPerPatientData} from the system.
	 * 
	 * @param datasetDefinition	the {@link RowPerPatientData} to purge
	 * 
	 * @should remove the PatientData
	 */
	public void purgePatientData(RowPerPatientData patientData);
}
