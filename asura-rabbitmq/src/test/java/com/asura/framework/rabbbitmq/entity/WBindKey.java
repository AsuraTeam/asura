/**
 * @FileName: WBindKey.java
 * @Package: com.asura.framework.rabbbitmq
 * @author sence
 * @created 3/19/2016 5:28 PM
 * <p/>
 * Copyright 2015 ziroom
 */
package com.asura.framework.rabbbitmq.entity;

import com.asura.framework.rabbitmq.entity.BindingKey;
import com.asura.framework.rabbitmq.exception.AsuraRabbitMqException;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class WBindKey extends BindingKey {

    private String biz;

    public WBindKey() {

    }

    public WBindKey(String system, String module, String biz, String function) {
        super(system, module, function);
        this.biz = biz;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }


    /**
     * 获取到routingKey
     */
    public String getKey() throws AsuraRabbitMqException {
        if (this.getSystem() == null || "".equals(this.getSystem())) {
            this.setSystem("*");
        }
        if (this.getModule() == null || "".equals(this.getModule())) {
            this.setModule("*");
        }
        if (this.getFunction() == null || "".equals(this.getFunction())) {
            this.setFunction("*");
        }
        return getSystem() + "." + getModule() + "." + getBiz() + "." + getFunction();
    }
}
