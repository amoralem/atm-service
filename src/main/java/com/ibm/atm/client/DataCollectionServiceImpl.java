package com.ibm.atm.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ibm.atm.exception.ServiceClientErrorExcepcion;
import com.ibm.atm.util.LogHandler;
import com.ibm.atm.util.MessageExceptionHandler;
/**
 * Capa de Servicio
 * @author amoralesm
 *
 */
@Service
public class DataCollectionServiceImpl implements IDataCollectionService {
	
	@Value("${banamex.localizador.jsonp.json5.uri}")
	String urlJson5;
	
	@Autowired
	private RestTemplate restTemplate;	
	
	/**
	 * Consumir el servicio con Ribbon + Feign
	 * ya no me dio tiempo
	 */
	@Override
	public String consumeService(String uid) throws ServiceClientErrorExcepcion {
		try{
			LogHandler.info(uid, getClass(), "[consumeService] Request: ");
			ResponseEntity<String> responseService=restTemplate.exchange(urlJson5, HttpMethod.GET, null, String.class);
			
			if(responseService.getStatusCode()!=HttpStatus.OK)
				throw new ServiceClientErrorExcepcion(MessageExceptionHandler.DETAIL_COMUNICATION_ERROR);
				
			return responseService.getBody();
		}catch (HttpClientErrorException e) {
			throw new ServiceClientErrorExcepcion(e.getMessage());
		}
		
	}

}
