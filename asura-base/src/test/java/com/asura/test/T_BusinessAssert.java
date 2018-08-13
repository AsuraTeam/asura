/**
 * @FileName: T_BusinessAssert.java
 * @Package: com.asura.test
 * @author liusq23
 * @created 2017/3/12 上午12:37
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.test;

import com.asura.framework.base.exception.ValidatorException;
import com.asura.framework.base.util.BusinessAssert;
import org.junit.Assert;
import org.junit.Test;

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
public class T_BusinessAssert {

    @Test
    public void t_requireNonNull_01() {
        String s = "";
        String str = BusinessAssert.requireNonNull(s, "字符串为空");
        Assert.assertEquals(str, s);
    }

    @Test(expected = ValidatorException.class)
    public void t_requireNonNull_02() {
        String s = null;
        String str = BusinessAssert.requireNonNull(s, "字符串为空");
        Assert.assertEquals(str, s);
    }

    @Test(expected = ValidatorException.class)
    public void t_requireNonEmpty_01() {
        String s = null;
        String str = BusinessAssert.requireNonEmpty(s, "字符串为空");
        Assert.assertEquals(str, s);
    }

    @Test(expected = ValidatorException.class)
    public void t_requireNonEmpty_02() {
        String s = "  ";
        String str = BusinessAssert.requireNonEmpty(s, "字符串为空");
        Assert.assertEquals(str, s);
    }

    @Test
    public void t_requireNonEmpty_03() {
        String s = "aaa";
        String str = BusinessAssert.requireNonEmpty(s, "字符串为空");
        Assert.assertEquals(str, s);
    }

    @Test(expected = ValidatorException.class)
    public void t_requireNonEmpty_04() {
        User s = null;
        User str = BusinessAssert.requireNonEmpty(s, "字符串为空");
        Assert.assertEquals(str, s);
    }

    @Test
    public void t_requireNonEmpty_05() {
        User s = new User();
        User str = BusinessAssert.requireNonEmpty(s, "对象为空");
        Assert.assertEquals(str, s);
    }
}
