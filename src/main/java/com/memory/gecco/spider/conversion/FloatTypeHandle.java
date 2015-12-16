package com.memory.gecco.spider.conversion;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FloatTypeHandle implements TypeHandle<Float> {
	
	private static final Logger log = LogManager.getLogger(FloatTypeHandle.class);

	@Override
	public Float getValue(Object src) {
		try {
			return Float.valueOf(src.toString());
		} catch(Exception ex) {
			log.error("float conversion error : " + src, ex);
		}
		return 0f;
	}

}
