/**
 * @FileName: T_SOAResParseUtil.java
 * @Package: com.asura.test
 * @author wurt2
 * @created 2017/2/22 21:01
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.test;

import com.asura.framework.base.entity.DataTransferObject;
import com.asura.framework.base.exception.SOAParseException;
import com.asura.framework.base.util.DataTransferObjectJsonParser;
import com.asura.framework.base.util.SOAResParseUtil;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author wurt2
 * @since 1.0
 * @version 1.0
 */
public class T_SOAResParseUtil {

    @Test
    public void t_test() throws SOAParseException {
        User user = new User();
        user.setName("吴瑞涛");
        user.setCreateDate(new Date());
        user.setTimestamp(new Timestamp(user.getCreateDate().getTime()));
        DataTransferObject dto = new DataTransferObject();
        dto.putValue("data", user);
        String result = dto.toJsonString();

        System.out.println(result);
        User parserUser = SOAResParseUtil.getValueFromDataByKey(result, "data", User.class);
        Assert.assertEquals(user, parserUser);
    }

    @Test
    public void t_test_01() throws SOAParseException {
        DataTransferObject dataTransferObject = new DataTransferObject();
        User user = DataTransferObjectJsonParser.getValueFromDataByKey(dataTransferObject.toJsonString(), "data", User.class);
        Assert.assertNull(user);
    }
}
