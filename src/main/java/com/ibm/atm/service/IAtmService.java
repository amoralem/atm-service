package com.ibm.atm.service;




import com.ibm.atm.exception.DataExcepcion;
import com.ibm.atm.exception.ServiceClientErrorExcepcion;
import com.ibm.atm.model.Request;
import com.ibm.atm.model.Response;

public interface IAtmService {
	
	Response getAtms(String uid, Request request) throws DataExcepcion, ServiceClientErrorExcepcion;

}
