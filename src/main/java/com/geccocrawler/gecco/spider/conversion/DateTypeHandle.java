package com.geccocrawler.gecco.spider.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTypeHandle implements TypeHandle<Date> {
	
	@Override
	public Date getValue(Object src) throws Exception {
		return getValue(src, "yyyy-MM-dd HH:mm:ss");
	}

	public Date getValue(Object src, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(src.toString());
	}
}
