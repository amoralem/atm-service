package com.ibm.atm.util;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class Parser {
	
	private Parser() {}
	
	private static final String JSONCALLBACK="jsonCallback(";
	private static final String JSONCALLBACK_CLOSE=");";
	private static final String EMPTY_SPACE="";
	private static final String JSONNODE_SERVICIOS="Servicios";
	
	public static Map<String,Object> objectToMap(String json) throws JsonProcessingException {
		
		final ObjectMapper objectMapper = new ObjectMapper();
		json=json.replace(JSONCALLBACK,EMPTY_SPACE).replace(JSONCALLBACK_CLOSE, EMPTY_SPACE);		
		JsonNode actualObj= objectMapper.readTree(json);		
		return objectMapper.convertValue(actualObj.get(JSONNODE_SERVICIOS), new TypeReference<Map<String,Object>>(){});
	}
	
}
