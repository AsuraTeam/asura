/**
 * @FileName: BusinessAssert.java
 * @Package: com.asura.framework.base.util
 * @author liusq23
 * @created 2017/3/11 下午11:28
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.base.util;

import com.asura.framework.base.exception.ValidatorException;
import com.asura.framework.commons.util.Check;

import java.util.Collection;

/**
 * <p>
 * 业务判断，做参数判断抛出业务异常
 * </p>
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
public class BusinessAssert {

    private BusinessAssert() {

    }

    /**
     * 判断对象是否为null,如果为null，返回默认code ValidatorException
     * 注意：如果是字符串，不会判断是否是空字符串
     *
     * @param t
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T requireNonNull(T t, String message) {
        return requireNonNull(t, ValidatorException.VALIDATOR_CODE, message);
    }

    /**
     * 判断对象是否为null，如果为null，返回指定code ValidatorException
     * 注意：如果是字符串，不会判断是否是空字符串
     *
     * @param t
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T requireNonNull(T t, int code, String message) {
        if (Check.isNull(t)) {
            throw new ValidatorException(code, message);
        }
        return t;
    }

    /**
     * 判断字符串是否为空字符串
     *
     * @param t
     * @param message
     * @return
     */
    public static String requireNonEmpty(String t, String message) {
        return requireNonEmpty(t, ValidatorException.VALIDATOR_CODE, message);
    }

    /**
     * 判断字符串是否为空字符串
     *
     * @param t
     * @param message
     * @return
     */
    public static String requireNonEmpty(String t, int code, String message) {
        requireNonNull(t, code, message);
        if (Check.isNullOrEmpty(t.trim())) {
            throw new ValidatorException(code, message);
        }
        return t;
    }

    /**
     * 判断字符串是否为空字符串
     *
     * @param t
     * @param message
     * @return
     */
    public static Collection requireNonEmpty(Collection t, String message) {
        return requireNonEmpty(t, ValidatorException.VALIDATOR_CODE, message);
    }

    /**
     * 判断字符串是否为空字符串
     *
     * @param t
     * @param message
     * @return
     */
    public static Collection requireNonEmpty(Collection t, int code, String message) {
        requireNonNull(t, code, message);
        if (Check.isNullOrEmpty(t)) {
            throw new ValidatorException(code, message);
        }
        return t;
    }

    /**
     * 判断对象是否为null，如果为null，返回指定code ValidatorException
     * 注意：这里会判断是否是空字符串以及空集合
     *
     * @param t
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T requireNonEmpty(T t, String message) {
        return requireNonEmpty(t, ValidatorException.VALIDATOR_CODE, message);
    }

    /**
     * 判断对象是否为null，如果为null，返回指定code ValidatorException
     * 注意：这里会判断是否是空字符串以及空集合
     *
     * @param t
     * @param message
     * @param <T>
     * @return
     */
    public static <T> T requireNonEmpty(T t, int code, String message) {
        if (t instanceof Collection) {
            Collection collection = (Collection) t;
            return (T) requireNonEmpty(collection, code, message);
        }
        if (t instanceof String) {
            String str = (String) t;
            return (T) requireNonEmpty(str, code, message);
        }
        return requireNonNull(t, code, message);
    }

}
