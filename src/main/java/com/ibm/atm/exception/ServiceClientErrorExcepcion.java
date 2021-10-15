package com.ibm.atm.exception;

public class ServiceClientErrorExcepcion  extends Exception{
	
	private static final long serialVersionUID = 3652788285776668805L;
	
    public ServiceClientErrorExcepcion(String mensaje){
        super(mensaje);        
    }
}
