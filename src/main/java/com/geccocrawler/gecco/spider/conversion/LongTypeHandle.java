package com.geccocrawler.gecco.spider.conversion;


public class LongTypeHandle implements TypeHandle<Long> {
	
	@Override
	public Long getValue(Object src) throws Exception {
		return Long.valueOf(src.toString());
	}

}
