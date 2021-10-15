package com.ibm.atm.client;

import com.ibm.atm.exception.ServiceClientErrorExcepcion;

public interface IDataCollectionService {
	
	String consumeService(String uid) throws ServiceClientErrorExcepcion;

}
