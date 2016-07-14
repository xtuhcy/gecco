package com.geccocrawler.gecco.demo.osc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.geccocrawler.gecco.demo.osc.mapper.OscnewsMapper;
import com.geccocrawler.gecco.demo.osc.model.Oscnews;
import com.geccocrawler.gecco.demo.osc.service.OscnewsService;
@Service
public class OscnewsServiceImpl implements OscnewsService {

	@Autowired
	private OscnewsMapper oscnewsMapper;
	
	@Override
	public int save(Oscnews oscnews) {
		
		return oscnewsMapper.insert(oscnews);
	}

}
