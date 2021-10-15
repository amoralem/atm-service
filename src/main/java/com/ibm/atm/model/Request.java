package com.ibm.atm.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Request {	
	
	private String gps;
	private String codigoPostal;
	private String delegacion;
	private String estado;

}
