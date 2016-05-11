package com.geccocrawler.gecco.monitor;

import java.lang.management.ManagementFactory;
import java.util.Set;

import org.reflections.Reflections;
import org.weakref.jmx.MBeanExporter;

import com.geccocrawler.gecco.annotation.MBean;

public class GeccoJmx {
	
	private static MBeanExporter exporter = new MBeanExporter(ManagementFactory.getPlatformMBeanServer());
	
	public static void export(String classpath) {
		Reflections reflections = new Reflections("com.geccocrawler.gecco.monitor");
		Set<Class<?>> mbeanClasses = reflections.getTypesAnnotatedWith(MBean.class);
		for(Class<?> mbeanClass : mbeanClasses) {
			MBean mbean = (MBean)mbeanClass.getAnnotation(MBean.class); 
			String name = mbean.value();
	    	try {
				exporter.export(classpath+":name="+name, mbeanClass.newInstance());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public static void unexport() {
		exporter.unexportAllAndReportMissing();
	}

}
