/**
 * @FileName: Address.java
 * @Package: com.ziroom.zmc.commons.util.request
 * @author youshipeng
 * @created 2017/1/9 11:17
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.tokenizer.util;

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
public interface Address {
    String getDomain();

    String getMapping();

    RequestMethod getMethod();
}