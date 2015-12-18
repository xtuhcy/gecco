package com.geccocrawler.gecco.spider.conversion;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class LongTypeHandle implements TypeHandle<Long> {
	
	private static final Logger log = LogManager.getLogger(LongTypeHandle.class);

	@Override
	public Long getValue(Object src) {
		try {
			return Long.valueOf(src.toString());
		} catch(Exception ex) {
			log.error("Long conversion error : " + src, ex);
		}
		return 0l;
	}

}
