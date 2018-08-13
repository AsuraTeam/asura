/**
 * Copyright(c) 2018 asura
 */
package com.asura.framework.commons.util;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p></p>
 *
 *
 * @author liusq23
 * @since 1.0
 * @version 1.0
 * @Date 2018/3/27 下午4:57
 */
public class T_BeanUtils {

    @Test
    public void t_01(){
        User a = new User();
        a.setNa("name");
        Address aad = new Address();
        aad.setSa("address");
        a.setAd(aad);
        User or = AsuraBeanUtils.copyProperties(a,User.class);
        Assert.assertThat(or.getAd().getSa(), CoreMatchers.containsString("address"));
    }

}
