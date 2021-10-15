package com.ibm.atm.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ibm.atm.util.GUIDGenerator;
import com.ibm.atm.util.LogHandler;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ResponseTest {
	
	String uid=GUIDGenerator.generateGUID();
	
	@Test
	void RequestModel() {
		try {
			Response obj=new Response();
			obj.setAtms(Arrays.asList("x"));
			obj.setSucursales(Arrays.asList("x"));
			obj.setTotalDeAtms(1);
			obj.setTotalDeSucursales(1);
			
			assertNotNull(obj);
			assertNotNull(obj.getAtms());
			assertNotNull(obj.getSucursales());
			assertNotNull(obj.getTotalDeAtms());
			assertNotNull(obj.getTotalDeSucursales());
		}catch (Exception e) {
			LogHandler.error(uid, getClass(), "Error ", e);
		}
	}	
}
