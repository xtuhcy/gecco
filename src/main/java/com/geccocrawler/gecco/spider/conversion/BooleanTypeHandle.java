package com.geccocrawler.gecco.spider.conversion;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class BooleanTypeHandle implements TypeHandle<Boolean> {

	private static final Logger log = LogManager.getLogger(BooleanTypeHandle.class);
	
	@Override
	public Boolean getValue(Object src) {
		try {
			return Boolean.valueOf(src.toString().toLowerCase());
		} catch(Exception ex) {
			log.error("boolean conversion error : " + src, ex);
		}
		return false;
	}

}
