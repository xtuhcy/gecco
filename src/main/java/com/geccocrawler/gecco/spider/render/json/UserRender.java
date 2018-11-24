package com.geccocrawler.gecco.spider.render.json;
/*
 *   Author: mario.zwk.
 *   Modifier:
 *   Date: 2018/11/24
 *
 */

import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.response.HttpResponse;
import com.geccocrawler.gecco.spider.SpiderBean;
import com.geccocrawler.gecco.spider.UserBean;
import com.geccocrawler.gecco.spider.render.AbstractRender;
import net.sf.cglib.beans.BeanMap;

public class UserRender extends AbstractRender {


    public UserRender() {
        super();
    }

    @Override
    public void fieldRender(HttpRequest request, HttpResponse response, BeanMap beanMap, SpiderBean bean) {
        if(! (bean instanceof UserBean)) {
            return ;
        }
        ((UserBean)bean).render(request, response, beanMap, bean);
    }

}