package com.ibm.atm.service;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
	private static final int ADDRESS_FIELD_INDEX=4;
	private static final int ATMSUCURSAL_FIELD_INDEX=19;
	private static final String ATM="ATM";
	private static final String SUCURSAL="Sucursal";
	
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
		
	private Response getBranchOfficesAndAtms(String uid, List<Object> dataCollection, Request request) {
		LogHandler.info(uid, getClass(), "[getBranchOfficesAndAtms] size dataCollection: "+dataCollection.size());
		
		//Obtiene los ATMS
		List<Object> atms=dataCollection.stream()
										.filter(filterObject(request, ATM))
										.collect(Collectors.toList());
		//Obtiene las sucursales		
		List<Object> branchOffices=dataCollection.stream()
												.filter(filterObject(request, SUCURSAL))
												.collect(Collectors.toList());
		
		LogHandler.info(uid, getClass(), "[getBranchOfficesAndAtms] matches found ATMS: "+atms.size());
		LogHandler.info(uid, getClass(), "[getBranchOfficesAndAtms] matches found BranchOffices: "+branchOffices.size());
		
		return new Response(atms.size(),branchOffices.size(),atms,branchOffices);
	}	
	
	private static Predicate<Object> filterObject(Request request, String typeOfContent) {
		Predicate<Object> codigoPostal = value->MapDataCollection.getValueOfField(value,ADDRESS_FIELD_INDEX).contains(request.getCodigoPostal());
		Predicate<Object> delegacion = value->MapDataCollection.getValueOfField(value,ADDRESS_FIELD_INDEX).contains(request.getDelegacion());
		Predicate<Object> estado = value->MapDataCollection.getValueOfField(value,ADDRESS_FIELD_INDEX).contains(request.getEstado());
		Predicate<Object> typeOfContentObject = value->MapDataCollection.getValueOfField(value,ATMSUCURSAL_FIELD_INDEX).contains(typeOfContent);
		
		return (codigoPostal.or(delegacion).or(estado)).and(typeOfContentObject);
	}
	
	
}
