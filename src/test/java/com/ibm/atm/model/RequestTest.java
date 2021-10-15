package com.ibm.atm.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ibm.atm.util.GUIDGenerator;
import com.ibm.atm.util.LogHandler;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RequestTest {
	
	String uid=GUIDGenerator.generateGUID();
	
	@Test
	void RequestModel() {
		try {
			Request obj=new Request();
			obj.setCodigoPostal(uid);
			obj.setDelegacion(uid);
			obj.setEstado(uid);
			obj.setGps(uid);
			assertNotNull(obj);
			assertNotNull(obj.getCodigoPostal());
			assertNotNull(obj.getDelegacion());
			assertNotNull(obj.getEstado());
			assertNotNull(obj.getGps());
		}catch (Exception e) {
			LogHandler.error(uid, getClass(), "Error ", e);
		}
	}

}
