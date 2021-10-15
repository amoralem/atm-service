package com.ibm.atm.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.atm.exception.DataExcepcion;
import com.ibm.atm.exception.ServiceClientErrorExcepcion;
import com.ibm.atm.mapping.Mapping;
import com.ibm.atm.model.ErrorDetalle;
import com.ibm.atm.model.Request;
import com.ibm.atm.model.Response;
import com.ibm.atm.service.IAtmService;
import com.ibm.atm.util.GUIDGenerator;
import com.ibm.atm.util.LogHandler;
import com.ibm.atm.util.MessageExceptionHandler;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Capa controller
 * @author amoralesm
 *
 */
@RestController
public class AtmController {
	
	@Autowired
	private IAtmService atmService;
	
	/**
	 * Metodo para obtner la ubicacion de atms y sucursales
	 * @param request
	 * @return
	 * @throws DataExcepcion
	 * @throws ServiceClientErrorExcepcion
	 */
	@PostMapping(Mapping.GETATMS)
	@ApiOperation(value = "Buscar cajeros automáticos citibanamex", notes = "Buscar cajeros automáticos citibanamex")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Solicitud Exitosa", response = Response.class),
			@ApiResponse(code = 400, message = MessageExceptionHandler.MESSAGE_ERROR_VALID, response = ErrorDetalle.class),
			@ApiResponse(code = 406, message = MessageExceptionHandler.MESSAGE_ERROR_BUSINESS, response = ErrorDetalle.class) })
    public ResponseEntity<Response> getATMs(@Valid @RequestBody Request request) throws DataExcepcion, ServiceClientErrorExcepcion {
    	String uid=GUIDGenerator.generateGUID();
    	LogHandler.info(uid, getClass(),request.toString());
    	return new ResponseEntity<>(
    			atmService.getAtms(uid, request),
    			HttpStatus.OK);
    }
}
