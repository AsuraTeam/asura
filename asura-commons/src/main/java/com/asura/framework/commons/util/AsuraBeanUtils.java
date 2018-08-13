/**
 * @FileName: BeanUtils.java
 * @Package: com.asura.framework.commons.util
 * @author liusq23
 * @created 2017/9/6 下午3:34
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.commons.util;


import com.asura.framework.commons.exception.BeanUtilsCopyException;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

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
public class AsuraBeanUtils {

    /**
     * 执行属性拷贝
     *
     * @param k
     * @param clazz
     * @param <T>
     * @param <K>
     * @return
     * @throws BeanUtilsCopyException
     */
    public static <T, K> T copyProperties(K k, Class<T> clazz) throws BeanUtilsCopyException {
        if (Check.isNull(k)) {
            return null;
        }
        try {
            T t = clazz.newInstance();
            BeanUtils.copyProperties(k, t);
            return t;
        } catch (Exception e) {
            throw new BeanUtilsCopyException(e);
        }
    }

    /**
     * 执行属性拷贝
     *
     * @param ks
     * @param clazz
     * @param <T>
     * @param <K>
     * @return
     * @throws BeanUtilsCopyException
     */
    public static <T, K> List<T> copyProperties(List<K> ks, Class<T> clazz) throws BeanUtilsCopyException {
        if (Check.isNullOrEmpty(ks)) {
            return new ArrayList<>();
        }
        try {
            if (Check.isNullOrEmpty(ks)) {
                return new ArrayList<>();
            }
            List<T> ts = new ArrayList<>(ks.size());
            for (K k : ks) {
                T t = clazz.newInstance();
                BeanUtils.copyProperties(k, t);
                ts.add(t);
            }
            return ts;
        } catch (Exception e) {
            throw new BeanUtilsCopyException(e);
        }
    }

    /**
     * 执行属性拷贝
     *
     * @param k
     * @param t
     * @param <T>
     * @param <K>
     * @return
     * @throws BeanUtilsCopyException
     */
    public static <T, K> void copyProperties(K k, T t) {
        if (!Check.isNull(k)) {
            BeanUtils.copyProperties(k, t);
        }
    }

}
