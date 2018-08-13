/**
 * @FileName: Utils.java
 * @Package: com.asura.framework.generator.utils
 * @author youshipeng
 * @created 2017/2/22 18:08
 * <p>
 * Copyright 2016 ziroom
 */
package com.asura.framework.generator.utils;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author youshipeng
 * @since 1.0
 * @version 1.0
 */
public class Utils {
    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }
}