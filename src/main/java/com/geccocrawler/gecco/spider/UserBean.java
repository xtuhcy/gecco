package com.geccocrawler.gecco.spider;
/*
 *   Author: mario.zwk.
 *   Modifier:
 *   Date: 2018/11/24
 *
 */

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import net.sf.cglib.beans.BeanMap;

public interface UserBean extends SpiderBean {
    void render(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean);
}
