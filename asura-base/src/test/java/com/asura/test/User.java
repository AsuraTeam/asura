/**
 * @FileName: User.java
 * @Package: com.asura.test
 * @author liusq23
 * @created 2016/11/28 上午9:49
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.test;

import com.asura.framework.base.entity.BaseEntity;

import java.sql.Timestamp;
import java.util.Date;

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
public class User extends BaseEntity {
    private String name;
    private Date createDate;
    private Timestamp timestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
