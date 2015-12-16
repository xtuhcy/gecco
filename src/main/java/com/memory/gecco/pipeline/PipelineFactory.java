package com.memory.gecco.pipeline;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.memory.gecco.annotation.PipelineName;

public class PipelineFactory {
	
	private Map<String, Pipeline> pipelines;
	
	public PipelineFactory(Reflections reflections) {
		this.pipelines = new HashMap<String, Pipeline>();
		Set<Class<?>> pipelineClasses = reflections.getTypesAnnotatedWith(PipelineName.class);
		for(Class<?> pipelineClass : pipelineClasses) {
			PipelineName spiderFilter = (PipelineName)pipelineClass.getAnnotation(PipelineName.class);
			try {
				pipelines.put(spiderFilter.value(), (Pipeline)pipelineClass.newInstance());
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public Pipeline getPipeline(String name) {
		return pipelines.get(name);
	}

}
