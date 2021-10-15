package com.ibm.atm.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.atm.model.Request;
import com.ibm.atm.util.GUIDGenerator;
import com.ibm.atm.util.LogHandler;

class AtmServiceTest {
	
	@Autowired
	private IAtmService atmService;
	
	String uid= GUIDGenerator.generateGUID();

	@Test
	void getAtmsNull() {
		try {
			Request request=new Request();
			assertNotNull(atmService.getAtms(uid, request));
		}catch (Exception e) {
			LogHandler.error(uid, getClass(), "Error ", e);
		}	
	}
	
	@Test
	void getAtmsEmpty() {
		try {
			Request request=new Request();
			request.setCodigoPostal("");
			request.setDelegacion("");
			request.setEstado("");
			assertNotNull(atmService.getAtms(uid, request));
		}catch (Exception e) {
			LogHandler.error(uid, getClass(), "Error ", e);
		}	
	}
	
	@Test
	void getAtms() {
		try {
			Request request=new Request();
			request.setCodigoPostal("75700");
			request.setDelegacion("Tehuacan");
			request.setEstado("Puebla");
			assertNotNull(atmService.getAtms(uid, request));
		}catch (Exception e) {
			LogHandler.error(uid, getClass(), "Error ", e);
		}	
	}
	
}
