/**
 * @FileName: OperatorPageRequest.java
 * @Package: com.asura.framework.base.dto
 * @author liusq23
 * @created 2016/12/21 下午4:13
 * <p>
 * Copyright 2015 ziroom
 */
package com.asura.framework.base.dto;

/**
 * <p>
 * 分页操作请求
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
public class OperatorPageRequest extends PageRequest {

    /**
     * 操作人id
     */
    private String operatorId;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

}
