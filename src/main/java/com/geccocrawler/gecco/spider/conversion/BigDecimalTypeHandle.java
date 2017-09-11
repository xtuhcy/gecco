package com.geccocrawler.gecco.spider.conversion;

import java.math.BigDecimal;

public class BigDecimalTypeHandle implements TypeHandle<BigDecimal>{

    @Override
    public BigDecimal getValue(Object src) throws Exception{
        if (null == src){
            return null;
        }
        return new BigDecimal(src.toString());   
    }
    
}
