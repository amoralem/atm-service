package com.ibm.atm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ibm.atm.mapping.Mapping;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations =
"classpath:application-test.properties")
@SpringBootTest
class AtmControllerTest {
	
	@Autowired
	private  WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
		
	@Test
	void getATMsNotReadableException() throws Exception {
		String request="";
		
		mockMvc.perform(post(Mapping.GETATMS).content(request)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	@Test
	void getATMsEmpty() throws Exception {
		String request="{}";
		
		mockMvc.perform(post(Mapping.GETATMS).content(request)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void getATMs() throws Exception {
		String request="{\r\n"
				+ "    \"codigoPostal\":\"75700\",\r\n"
				+ "    \"delegacion\":\"Tehuacan\",\r\n"
				+ "    \"estado\":\"Puebla\"   \r\n"
				+ "}\r\n";				
		
		mockMvc.perform(post(Mapping.GETATMS).content(request)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
}
