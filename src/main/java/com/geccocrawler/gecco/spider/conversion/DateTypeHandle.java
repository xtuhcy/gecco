package com.geccocrawler.gecco.spider.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DateTypeHandle implements TypeHandle<Date> {
	
	private static final Logger log = LogManager.getLogger(DateTypeHandle.class);

	@Override
	public Date getValue(Object src) {
		return getValue(src, "yyyy-MM-dd HH:mm:ss");
	}

	public Date getValue(Object src, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(src.toString());
		} catch(Exception ex) {
			log.error("Date conversion error : " + src + " format " + format, ex);
		}
		return null;
	}
}
