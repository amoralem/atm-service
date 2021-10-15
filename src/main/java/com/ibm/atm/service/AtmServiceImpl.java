package com.ibm.atm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.atm.client.IDataCollectionService;
import com.ibm.atm.exception.DataExcepcion;
import com.ibm.atm.exception.ServiceClientErrorExcepcion;
import com.ibm.atm.model.Request;
import com.ibm.atm.model.Response;
import com.ibm.atm.util.LogHandler;
import com.ibm.atm.util.MapDataCollection;
import com.ibm.atm.util.Parser;
import com.ibm.atm.util.ValidateUtil;
/**
 * Capa de negocio
 * @author amoralesm
 *
 */
@Service
public class AtmServiceImpl implements IAtmService {
	
	private static final String DEFAULT_VALUE="-1";
	private static final int INDEX_ADDRESS_FIELD=4;
	private static final int INDEX_ATMSUCURSAL=19;
	private static final String ATM="ATM";
	
	@Autowired
	private IDataCollectionService dataCollectionService;

	@Override
	public Response getAtms(String uid, Request request) throws DataExcepcion, ServiceClientErrorExcepcion {
		
		//En los casos de que los atributos seas vacios o nulos se les asigna un valor por defaul
		//esto debido a que no son obligatorios
		if(ValidateUtil.validationOfEmptyAndNull(request.getCodigoPostal()))
			request.setCodigoPostal(DEFAULT_VALUE);
		
		if(ValidateUtil.validationOfEmptyAndNull(request.getDelegacion()))
			request.setDelegacion(DEFAULT_VALUE);
		
		if(ValidateUtil.validationOfEmptyAndNull(request.getEstado()))
			request.setEstado(DEFAULT_VALUE);
		
		return processDataCollection(uid, request, dataCollectionService.consumeService(uid));
	}
	
	private Response processDataCollection(String uid, Request request, String json) throws DataExcepcion {
		
		try {
			
			return getBranchOfficesAndAtms(uid,MapDataCollection.getDataList(Parser.objectToMap(json)),request);
			
		} catch (JsonProcessingException e) {
			throw new DataExcepcion(e.getMessage());
			
		}		
	}
	
	
	@SuppressWarnings("unchecked")
	private Response getBranchOfficesAndAtms(String uid, List<Object> dataCollection, Request request) {
		LogHandler.info(uid, getClass(), "[getBranchOfficesAndAtms] size dataCollection: "+dataCollection.size());
		List<Object> atms= new ArrayList<>();
		List<Object> branchOffices= new ArrayList<>();
		for(Object obj:dataCollection) {
			List<Object> data=(List<Object>) obj;
			if(data.get(INDEX_ADDRESS_FIELD).toString().contains(request.getCodigoPostal()) ||
					data.get(INDEX_ADDRESS_FIELD).toString().contains(request.getDelegacion()) ||
					data.get(INDEX_ADDRESS_FIELD).toString().contains(request.getEstado())) {
				
				if(data.get(INDEX_ATMSUCURSAL).toString().contains(ATM))
					atms.add(data); 
				else 
					branchOffices.add(data);
			}
		}
		LogHandler.info(uid, getClass(), "[getBranchOfficesAndAtms] matches found ATMS: "+atms.size());
		LogHandler.info(uid, getClass(), "[getBranchOfficesAndAtms] matches found BranchOffices: "+branchOffices.size());
		return new Response(atms.size(),branchOffices.size(),atms,branchOffices);
	}
	
	
}
