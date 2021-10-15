package com.ibm.atm.util;

public final class ValidateUtil {
	
	private ValidateUtil() {}
	
	public static boolean validationOfEmptyAndNull(String atributo){
		return (atributo == null || atributo.isEmpty());
	}
}
