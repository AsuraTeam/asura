/**
 * @FileName: HotUpdateAddress.java
 * @Package: top.swimmer.elasticsearch.HanLP.update
 * @author youshipeng
 * @created 2017/2/12 12:22
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer.update;


import com.asura.framework.tokenizer.util.Address;
import com.asura.framework.tokenizer.util.RequestMethod;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author youshipeng
 * @version 1.0
 * @since 1.0
 */
public class HotUpdateAddress implements Address {
    private String domain;
    private String mapping;
    private RequestMethod method;

    public HotUpdateAddress(String domain, String mapping, RequestMethod method) {
        this.domain = domain;
        this.mapping = mapping;
        this.method = method;
    }

    @Override
    public String getDomain() {
        return domain;
    }

    @Override
    public String getMapping() {
        return mapping;
    }

    @Override
    public RequestMethod getMethod() {
        return method;
    }
}