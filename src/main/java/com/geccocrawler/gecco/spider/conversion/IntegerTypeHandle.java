package com.geccocrawler.gecco.spider.conversion;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class IntegerTypeHandle implements TypeHandle<Integer> {
	
	private static final Logger log = LogManager.getLogger(IntegerTypeHandle.class);

	@Override
	public Integer getValue(Object src) {
		try {
			return Integer.valueOf(src.toString());
		} catch(Exception ex) {
			log.error("Integer conversion error : " + src, ex);
		}
		return 0;
	}

}
