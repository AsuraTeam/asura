/**
 * @FileName: Optionals.java
 * @Package: com.asura.framework.commons.util
 * @author liusq23
 * @created 2017/4/22 下午3:26
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.commons.util;


import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * <p>
 * Optionals 工具类，结合jdk8 Optional使用
 * 场景：定义po时，不定义属性为Optional<T>类型，如
 * public class UserPO{
 * Date birthDate;
 * }
 * 而vo时需要,
 * public class UserVO{
 * String birthDate;
 * }
 * 在po和vo转换过程中，birthDate可能为空，容易导致NullPointerException
 * 使用Optionals可解决，如下
 * Optionals.orElse(birthDate,"");
 * Optionals.orElseGet(birthDate,()->{""});
 * Optionals.orElseThrow(birthDate,NullPointerException:new);
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
public class Optionals {

    /**
     * 判断对象是否为null，否则返回默认值
     *
     * @param originValue
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T orElse(@Nullable T originValue, T defaultValue) {
        Optional<T> optional = Optional.ofNullable(originValue);
        return optional.orElse(defaultValue);
    }

    /**
     * 判断对象是否为null，否则根据lamba表达式返回
     *
     * @param originValue
     * @param supplier
     * @param <T>
     * @return
     */
    public static <T> T orElseGet(T originValue, Supplier<? extends T> supplier) {
        return orElse(originValue, supplier.get());
    }

    /**
     * 判断对象是否为null，否则抛出异常
     *
     * @param originValue
     * @param exception
     * @param <T>
     * @return
     */
    public static <T, X extends Throwable> T orElseThrow(T originValue, Supplier<? extends X> exception) throws X {
        Optional<T> optional = Optional.ofNullable(originValue);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw exception.get();
    }

}
