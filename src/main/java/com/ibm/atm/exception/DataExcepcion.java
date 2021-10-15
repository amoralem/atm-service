package com.ibm.atm.exception;

public class DataExcepcion  extends Exception{
	
	private static final long serialVersionUID = 3652788285776668805L;
	
    public DataExcepcion(String mensaje){
        super(mensaje);        
    }
}
