package com.ibm.atm.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Response {
	
	private Integer totalDeAtms;
	private Integer totalDeSucursales;
	private List<Object> atms;
	private List<Object> sucursales;
}
