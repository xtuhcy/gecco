package com.memory.gecco.spider.conversion;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DoubleTypeHandle implements TypeHandle<Double> {

	private static final Logger log = LogManager.getLogger(DoubleTypeHandle.class);
	
	@Override
	public Double getValue(Object src) {
		try {
			return Double.valueOf(src.toString());
		} catch(Exception ex) {
			log.error("Double conversion error : " + src, ex);
		}
		return 0d;
	}

}
