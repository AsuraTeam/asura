/**
 * @FileName: BeanUtilsCopyException.java
 * @Package: com.asura.framework.commons.exception
 * @author liusq23
 * @created 2017/9/6 下午6:33
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.commons.exception;

/**
 * <p></p>
 * <p>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @version 1.0
 * @since 1.0
 */
public class BeanUtilsCopyException extends RuntimeException {

    private static final long serialVersionUID = -3010456487227762210L;

    /**
     * 构造器
     */
    public BeanUtilsCopyException() {
        super();
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     * @param cause   异常原因
     */
    public BeanUtilsCopyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 构造器
     *
     * @param message 异常详细信息
     */
    public BeanUtilsCopyException(String message) {
        super(message);
    }

    /**
     * 构造器
     *
     * @param cause 异常原因
     */
    public BeanUtilsCopyException(Throwable cause) {
        super(cause);
    }


}
