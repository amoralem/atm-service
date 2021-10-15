package com.ibm.atm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class MapDataCollection {
	
	private MapDataCollection() {}
	
	/**
	 * Cabe mencionar que este metodo no es una solucion optima, pero debido al poco tiempo
	 * que puedo invertir de esfuerzo lo termine implementando de esta forma
	 * @param data
	 * @param request
	 * @return
	 */
	public static List<Object> getDataList(Map<String,Object> data) {
	
		List<Object> dataList= new ArrayList<>();
		
		for(Map.Entry<String,Object> dataLevel1 : data.entrySet()) {
			for(Map.Entry<String,Object> dataLevel2 : getDataLevelN(dataLevel1).entrySet()) {
				for(Map.Entry<String,Object> dataLevel3 : getDataLevelN(dataLevel2).entrySet()) {
					dataList.add(dataLevel3.getValue());
				}				
			}
		}

		return dataList;
	}
	
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getDataLevelN(Map.Entry<String,Object> data) {		
		return (Map<String, Object>)data.getValue();
	}
	
}
